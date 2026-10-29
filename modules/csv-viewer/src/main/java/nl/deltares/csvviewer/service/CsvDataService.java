package nl.deltares.csvviewer.service;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import nl.deltares.csvviewer.comparator.CsvJSONObjectComparator;
import nl.deltares.portal.utils.HttpClientUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvDataService {

    public static JSONObject loadCsvAsJson(String csvUrl, String username, String password,
                                           int searchRowNumber) throws IOException {

        HttpURLConnection connection = getConnection(csvUrl, username, password);
        JSONObject row = JSONFactoryUtil.createJSONObject();
        try (InputStream inputStream = connection.getInputStream();
             BufferedInputStream bufferedInput = new BufferedInputStream(inputStream)) {

            try (CSVParser parser = getParser(bufferedInput)) {

                int rowNumber = 0;
                for (CSVRecord record : parser) {
                    rowNumber++;
                    if (rowNumber != searchRowNumber) {
                        continue;
                    }

                    Map<String, String> values = readValues(parser.getHeaderNames(), record);

                    row.put("rowNumber", rowNumber);
                    row.put("values", values);
                    break;
                }

            }

        }
        return row;
    }


    public static JSONObject loadCsvAsJson(String csvUrl, String username, String password, String displayColumns,
                                           int startRow, int delta, String filterColumn, String filterValue,
                                           String orderByColumn, String orderByType) throws IOException {

        HttpURLConnection connection = getConnection(csvUrl, username, password);

        try (InputStream inputStream = connection.getInputStream();
             BufferedInputStream bufferedInput = new BufferedInputStream(inputStream)) {

            try (CSVParser parser = getParser(bufferedInput)) {
                List<String> csvHeaders = parser.getHeaderNames();
                List<String> displayable = parseDisplayColumns(displayColumns, csvHeaders);
                List<JSONObject> rows = new ArrayList<>();

                int rowNumber = 0;
                for (CSVRecord record : parser) {
                    rowNumber++;
                    if (!checkFilter(record, filterColumn, filterValue)) {
                        continue;
                    }

                    Map<String, String> values = readValues(displayable, record);

                    JSONObject row = JSONFactoryUtil.createJSONObject();
                    row.put("rowNumber", rowNumber);
                    row.put("values", values);
                    rows.add(row);
                }

                sortRows(rows, orderByColumn, orderByType);
                int endRow = Math.min(startRow + delta, rows.size());
                JSONObject response = JSONFactoryUtil.createJSONObject();
                response.put("columns", displayable);
                response.put("rows", rows.subList(startRow, endRow));
                response.put("totalRows", rows.size());

                return response;
            }
        }

    }

    private static void sortRows(List<JSONObject> rows, String orderByCol, String orderByType) {

        final CsvJSONObjectComparator comparator = new CsvJSONObjectComparator(orderByType.equals("asc"), orderByCol);
        rows.sort(comparator);
    }

    private static Map<String, String> readValues(List<String> parser, CSVRecord record) {
        Map<String, String> values = new LinkedHashMap<>();

        for (String header : parser) {
            String value = record.isSet(header) ? record.get(header) : "";
            value = value == null ? "" : value.trim();
            values.put(header, value);
        }
        return values;
    }

    private static CSVParser getParser(BufferedInputStream bufferedInput) throws IOException {
        bufferedInput.mark(8192);
        byte[] sample = readSample(bufferedInput);
        Charset charset = detectCharset(sample);
        char delimiter = detectDelimiter(sample, charset);
        bufferedInput.reset();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreSurroundingSpaces(true)
                .setTrim(true)
                .setDelimiter(delimiter)
                .build();

        return CSVParser.parse(bufferedInput, charset, csvFormat);
    }

    private static HttpURLConnection getConnection(String csvUrl, String username, String password) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        if (!username.isEmpty()) {
            headers.put("Authorization", "Basic " + HttpClientUtils.getBasicAuthorization(username, password));
        }
        HttpURLConnection connection = HttpClientUtils.getConnection(csvUrl, "GET", headers);
        HttpClientUtils.checkResponse(connection);
        return connection;
    }

    private static boolean checkFilter(CSVRecord record, String filterColumn, String filterValue) {
        if (filterColumn == null || filterColumn.trim().isEmpty()) {
            return true;
        }
        if (filterValue == null || filterValue.trim().isEmpty()) {
            return true;
        }
        String value = record.isSet(filterColumn) ? record.get(filterColumn) : "";
        value = value == null ? "" : value.trim();
        return value.toLowerCase().contains(filterValue.toLowerCase().trim());
    }

    private static byte[] readSample(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int totalRead = 0;
        int read;

        while (totalRead < 8192 && (read = inputStream.read(buffer, 0, Math.min(buffer.length, 8192 - totalRead))) != -1) {
            outputStream.write(buffer, 0, read);
            totalRead += read;
        }

        return outputStream.toByteArray();
    }

    private static Charset detectCharset(byte[] sample) {
        if (sample.length >= 3
                && sample[0] == (byte) 0xEF
                && sample[1] == (byte) 0xBB
                && sample[2] == (byte) 0xBF) {
            return StandardCharsets.UTF_8;
        }

        if (sample.length >= 2 && sample[0] == (byte) 0xFF && sample[1] == (byte) 0xFE) {
            return StandardCharsets.UTF_16LE;
        }

        if (sample.length >= 2 && sample[0] == (byte) 0xFE && sample[1] == (byte) 0xFF) {
            return StandardCharsets.UTF_16BE;
        }

        return StandardCharsets.UTF_8;
    }

    private static char detectDelimiter(byte[] sample, Charset charset) {
        String content = new String(sample, charset);
        int semicolonCount = countOccurrences(content, ';');
        int commaCount = countOccurrences(content, ',');
        int tabCount = countOccurrences(content, '\t');

        if (semicolonCount > commaCount && semicolonCount > tabCount) {
            return ';';
        }
        if (tabCount > commaCount && tabCount > semicolonCount) {
            return '\t';
        }
        return ',';
    }

    public static boolean isUrl(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String trimmed = value.trim().toLowerCase();
        return trimmed.startsWith("http://") || trimmed.startsWith("https://");
    }

    private static List<String> parseDisplayColumns(String columns, List<String> availableHeaders) {
        List<String> result = new ArrayList<>();
        if (columns == null || columns.trim().isEmpty()) {
            return result;
        }

        for (String column : columns.split(",")) {
            String trimmed = column.trim();
            if (!trimmed.isEmpty() && availableHeaders.contains(trimmed)) {
                result.add(trimmed);
            }
        }
        return result;
    }

    private static int countOccurrences(String value, char ch) {
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

}
