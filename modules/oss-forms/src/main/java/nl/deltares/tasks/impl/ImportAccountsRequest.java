package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;
import nl.deltares.portal.utils.AccountUtils;
import nl.deltares.portal.utils.CsvParser;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.*;
import java.nio.file.Files;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class ImportAccountsRequest extends AbstractDataRequest {

    public final String MaconomyId = "maconomy_id";
    public final String CompanyName = "company_name";
    public final String Domain = "domain";
    public final String VAT = "vat";
    public final String Phone = "phone";
    public final String Website = "website";
    public final String Address = "address";
    public final String PostalCode = "postal_code";
    public final String City = "city";
    public final String State = "state";
    public final String CountryCode = "country_code";

    private final String[] CsvHeaderNames = {MaconomyId, CompanyName, Domain, VAT, Phone, Website, Address, PostalCode, City, CountryCode};

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
            status = TERMINATED;
            fireStateChanged();
            return status;
        }

        if (status == AVAILABLE || status == NODATA) {
            return status;
        }
        init();
        status = RUNNING;

        try {
            totalCount = (int) Files.size(accountsFile.toPath());
        } catch (IOException e) {
            errorMessage = "Error getting file size: " + e.getMessage();
            status = TERMINATED;
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

                String[] lineItems;
                try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {

                    CsvParser csvParser = new CsvParser(reader, ';');
                    csvParser.setHeaders(CsvHeaderNames);
                    int lineCounter = 0;

                    while (true) {

                        lineItems = csvParser.readLine();
                        if (lineItems == null){
                            break;
                        }
                        lineCounter++;
                        incrementProcessCount(getSummedStringLength(lineItems));
                        if (lineItems.length == 0) continue;
                        AccountInfo accountInfo;
                        try {
                            accountInfo = toAccountInfo(lineItems, csvParser);
                        } catch (Exception e){
                            String msg = String.format("Error parsing line %d: %s", lineCounter, e.getMessage());
                            logger.error(msg);
                            writer.println(msg);
                            continue;
                        }
                        try {
                            _accountUtils.createOrUpdateBusinessAccountEntry(accountInfo, _companyId, currentUserId);
                            String msg = String.format("Imported account '%s' with address '%s'",
                                    accountInfo.getCompanyName(), accountInfo.getAddressInfo().getAddressName());
                            logger.info(msg);
                            writer.println(msg);
                        } catch (Exception e){
                            String msg = String.format("Error importing account %s: %s", accountInfo.getCompanyName(), e.getClass().getSimpleName());
                            logger.error(msg);
                            writer.println(msg);
                        }
                    }
                }

                logger.info("Finished importing account data.");
                writer.println("Finished importing account data.");
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn(String.format("Error importing account data: %s", errorMessage), e);
                status = TERMINATED;
            }
            if (status != TERMINATED) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
                status = AVAILABLE;
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
            status = TERMINATED;
        }
        fireStateChanged();

        return status;

    }

    int getSummedStringLength(String[] arr) {
        int sum = 0;
        for (String s : arr) {
            if (s != null) {
                sum += s.length();
            }
        }
        return sum;
    }
    //todo
    private AccountInfo toAccountInfo(String[] line, CsvParser csvParser) {
        AccountInfo accountInfo = new AccountInfo();
        int columnIndex = csvParser.getColumnIndex(MaconomyId);
        if (columnIndex != -1) accountInfo.setCompanyIdentifier(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(CompanyName);
        if (columnIndex != -1) accountInfo.setCompanyName(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(Domain);
        if (columnIndex != -1) accountInfo.setEmailDomains(parseDomains(line[columnIndex]));
        columnIndex = csvParser.getColumnIndex(Website);
        if (columnIndex != -1) accountInfo.setWebsite(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(VAT);
        if (columnIndex != -1) accountInfo.setVat(line[columnIndex]);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setDefaultBillingAddress(true);
        addressInfo.setAddressIdentifier("address_" + accountInfo.getCompanyIdentifier());
        addressInfo.setAddressName("Billing address");
        columnIndex = csvParser.getColumnIndex(Address);
        if (columnIndex != -1) addressInfo.setStreet(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(PostalCode);
        if (columnIndex != -1) addressInfo.setPostal(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(City);
        if (columnIndex != -1) addressInfo.setCity(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(CountryCode);
        if (columnIndex != -1) addressInfo.setCountryA2Code(line[columnIndex]);
        columnIndex = csvParser.getColumnIndex(Phone);
        if (columnIndex != -1) addressInfo.setPhone(line[columnIndex]);
        addressInfo.setPhone(line[columnIndex]);

        accountInfo.setAddressInfo(addressInfo);

        return accountInfo;
    }

    private static String[] parseDomains(String line) {
        if (line == null) return new String[0];
        return line.split(";");
    }


    @Override
    public String getStatusMessage() {
        //dummy something to show in progress bar.
        if (status == RUNNING){
            int processedCount = super.getProcessedCount();
            processedCount++;
            if (processedCount == totalCount) super.setProcessCount(0);
            else setProcessCount(processedCount);
        }
        return super.getStatusMessage();
    }
}
