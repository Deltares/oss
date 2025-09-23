package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;
import nl.deltares.portal.utils.AccountUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.*;
import java.nio.file.Files;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class ImportAccountsRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(ImportAccountsRequest.class);
    private final String _accountFilePath;
    private final AccountUtils _accountUtils;
    private final long _companyId;

    public ImportAccountsRequest(String id, long currentUserId, long companyId, String accountDataFile,
                                 AccountUtils accountUtils) throws IOException {
        super(id, currentUserId);
        _accountFilePath = accountDataFile;
        _accountUtils = accountUtils;
        _companyId = companyId;
    }

    @Override
    public STATUS call() {

        final File accountsFile = new File(_accountFilePath);
        if (!accountsFile.exists()) {
            errorMessage = "File containing account data does not exist: " + _accountFilePath;
            status = terminated;
            fireStateChanged();
            return status;
        }

        if (status == available || status == nodata) {
            return status;
        }
        init();
        status = running;

        try {
            totalCount = (int) Files.size(accountsFile.toPath());
        } catch (IOException e) {
            errorMessage = "Error getting file size: " + e.getMessage();
            status = terminated;
            fireStateChanged();
            return status;
        }

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Download results to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                String message = String.format("Start importing account data from file: " + _accountFilePath);
                logger.info(message);
                writer.println(message);

                String line;
                try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                    int lineCounter = 0;
                    while ((line = reader.readLine()) != null) {
                        lineCounter++;
                        incrementProcessCount(line.length());
                        final String[] split = line.split(";");
                        if (split.length == 0) continue;
                        AccountInfo accountInfo;
                        try {
                            accountInfo = parseLine(line);
                        } catch (Exception e){
                            logger.error(String.format("Error parsing line %d: %s", lineCounter, e.getMessage()));
                            continue;
                        }
                        try {
                            _accountUtils.createBusinessAccountEntry(accountInfo, _companyId);
                            logger.info(String.format("Imported account '%s' with address '%s'",
                                    accountInfo.getCompanyName(), accountInfo.getAddressInfo().getAddressName()));
                        } catch (Exception e){
                            logger.error(String.format("Error importing account for line %d: %s", lineCounter, e.getMessage()));
                        }
                    }
                }

                logger.info("Finished importing account data.");
                writer.println("Finished importing account data.");
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn(String.format("Error importing account data: %s", errorMessage), e);
                status = terminated;
            }
            if (status != terminated) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
                status = available;
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;

    }

    //todo
    private AccountInfo parseLine(String line) {
        AccountInfo accountInfo = new AccountInfo();

        AddressInfo addressInfo = new AddressInfo();
        accountInfo.setAddressInfo(addressInfo);

        return accountInfo;
    }


    @Override
    public String getStatusMessage() {
        //dummy something to show in progress bar.
        if (status == running){
            int processedCount = super.getProcessedCount();
            processedCount++;
            if (processedCount == totalCount) super.setProcessCount(0);
            else setProcessCount(processedCount);
        }
        return super.getStatusMessage();
    }
}
