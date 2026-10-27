package nl.deltares.csvviewer.service;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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

    public static String loadCsvAsJson(String csvUrl, String username, String password, String filterableColumns) {
        return loadCsvAsJson(csvUrl, username, password, filterableColumns, true);
    }

    public static String loadCsvAsJson(String csvUrl, String username,  String password, String filterableColumns, boolean strictValidation) {
        try {
            HashMap<String, String> headers = new HashMap<>();
            if (!username.isEmpty()) {
                headers.put("Authorization", "Basic " + HttpClientUtils.getBasicAuthorization(username, password));
            }
            HttpURLConnection connection = HttpClientUtils.getConnection(csvUrl, "GET", headers);
            HttpClientUtils.checkResponse(connection);

            try (InputStream inputStream = connection.getInputStream();
                 BufferedInputStream bufferedInput = new BufferedInputStream(inputStream)) {
                bufferedInput.mark(8192);
                byte[] sample = readSample(bufferedInput, 8192);
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

                try (CSVParser parser = CSVParser.parse(bufferedInput, charset, csvFormat)) {
                    List<String> csvHeaders = parser.getHeaderNames();
                    List<String> filterable = parseFilterableColumns(filterableColumns, csvHeaders);
                    JSONArray rows = JSONFactoryUtil.createJSONArray();

                    int rowNumber = 0;
                    for (CSVRecord record : parser) {
                        rowNumber++;
                        Map<String, String> values = new LinkedHashMap<>();
                        List<String> invalidColumns = new ArrayList<>();
                        String invalidReason = "";

                        for (String header : csvHeaders) {
                            String value = record.isSet(header) ? record.get(header) : "";
                            value = value == null ? "" : value.trim();
                            values.put(header, value);

                            if (value.isEmpty() && strictValidation) {
                                invalidColumns.add(header);
                            }

                            if (isUrl(value)) {
                                values.put(header + "__link", value);
                            }
                        }

                        if (!invalidColumns.isEmpty()) {
                            invalidReason = "Empty values in columns: " + String.join(", ", invalidColumns);
                        }

                        JSONObject row = JSONFactoryUtil.createJSONObject();
                        row.put("rowNumber", rowNumber);
                        row.put("values", values);
                        row.put("valid", invalidReason.isEmpty());
                        row.put("invalidReason", invalidReason);
                        row.put("filterableColumns", filterable);
                        rows.put(row);
                    }

                    JSONObject response = JSONFactoryUtil.createJSONObject();
                    response.put("columns", csvHeaders);
                    response.put("rows", rows);
                    response.put("totalRows", rows.length());
                    response.put("validRows", countValidRows(rows));
                    response.put("invalidRows", rows.length() - countValidRows(rows));
                    response.put("filterableColumns", filterable);
                    return response.toJSONString();
                }
            }
        } catch (Exception e) {
            JSONObject error = JSONFactoryUtil.createJSONObject();
            error.put("error", e.getMessage());
            return error.toJSONString();
        }
    }

    private static byte[] readSample(InputStream inputStream, int maxBytes) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int totalRead = 0;
        int read;

        while (totalRead < maxBytes && (read = inputStream.read(buffer, 0, Math.min(buffer.length, maxBytes - totalRead))) != -1) {
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

    private static boolean isUrl(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String trimmed = value.trim().toLowerCase();
        return trimmed.startsWith("http://") || trimmed.startsWith("https://");
    }

    private static List<String> parseFilterableColumns(String columns, List<String> availableHeaders) {
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

    private static int countValidRows(JSONArray rows) {
        int valid = 0;
        for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            if (row != null && row.getBoolean("valid")) {
                valid++;
            }
        }
        return valid;
    }
}
