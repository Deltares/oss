package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import nl.deltares.emails.LicenseFilesEmail;
import nl.deltares.portal.utils.LicenseManagerUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class SendLicenseFilesRequest extends AbstractDataRequest {

    private final LicenseManagerUtils licenseManagerUtils;
    private final Long customerId;
    private final LicenseFilesEmail confirmationEmail;

    public SendLicenseFilesRequest(String id, Long customerId, User user, LicenseFilesEmail confirmationEmail, LicenseManagerUtils licenseManagerUtils) throws IOException {
        super(id, user.getUserId());

        this.customerId = customerId;
        this.licenseManagerUtils = licenseManagerUtils;
        this.confirmationEmail = confirmationEmail;
    }

    @Override
    public STATUS call() {

        if (getStatus() == available) return status;
        status = running;
        statusMessage = "starting license request";
        init();
        try {

            JSONObject response = licenseManagerUtils.generateCustomerLicenseFiles(customerId);
            String requestId = response.getString("requestViewRequestId");

            JSONObject progress = licenseManagerUtils.getProgress(requestId);
            while (progress != null && progress.get("requestProgress").equals("Generating")) {
                try {
                    Thread.sleep(1000); // sleep for 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // recommended practice // handle interruption
                    status = dispose;
                    errorMessage = "Progress thread was interrupted";
                    break;
                }
                progress = licenseManagerUtils.getProgress(requestId);
            }

            if (progress != null) {
                if (progress.getString("requestProgress").equals("Done")) {
                    licenseManagerUtils.download(requestId, dataFile);
                    confirmationEmail.sendLicenseFilesEmail(dataFile);
                    status = dispose; //cleanup once completed
                } else {
                    errorMessage = progress.getString("message");
                    status = dispose;
                }
            } else {
                status = dispose;
            }


        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = dispose;
        }
        fireStateChanged();

        return status;
    }
}
