package nl.deltares.portal.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CsvParser {

    private final BufferedReader _reader;
    private char _separator = ',';
    private String[] _headers;
    private String[] _sortedValues;
    private int[] _headerColumns;

    public CsvParser(Reader reader, char separator) {
        this._reader = new BufferedReader(reader);
        this._separator = separator;
    }

    public String[] getHeaders() {
        return _headers;
    }

    public void setHeaders(String[] headers) {
        this._headers = Arrays.stream(headers).map(String::toLowerCase).toArray(String[]::new);
    }

    public String[] readLine() throws IOException {
        String line = _reader.readLine();
        if (line == null) return null;
        String[] parsedLines = cleanValues(line.split(_separator + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
        while (isHeader(parsedLines) || isInvalidLine(parsedLines)) {
            line = _reader.readLine();
            if (line == null) {
                return null;
            }
            parsedLines = cleanValues(line.split(_separator + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
        }
        return sortColumns(parsedLines);
    }

    public int getColumnIndex(String header) {
        return Arrays.asList(_headers).indexOf(header);
    }

    private String[] cleanValues(String[] items) {
        if (items == null) return null;

        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].replace("\"", "").replace("\r", "").trim();
        }
        return items;
    }

    private String[] sortColumns(String[] parsedLine) {
        if (_headerColumns == null) return parsedLine;
        for (var i = 0; i < _headerColumns.length; i++) {
            var headerColumn = _headerColumns[i];
            if (headerColumn == -1 || headerColumn >= parsedLine.length) continue;
            _sortedValues[i] = parsedLine[headerColumn];
        }

        return _sortedValues;
    }

    private boolean isInvalidLine(String[] parsedLine) {
        if (_headers == null) return false;
        return parsedLine.length < _headers.length;
    }

    private boolean isHeader(String[] values) {
        if (_headers == null) return false;
        if (_headerColumns != null) return false;
        if (values == null) return true;
        if (values.length < _headers.length) return false;
        var valuesLowerCase = Arrays.stream(values).map(String::toLowerCase)
                .map(s -> s.replace("\uFEFF", ""))
                .map(String::trim).collect(Collectors.toList());
        _headerColumns = new int[_headers.length];
        _sortedValues = new String[_headers.length];
        for (var i = 0; i < _headers.length; i++) {
            var headerColumn = valuesLowerCase.indexOf(_headers[i]);
            if (headerColumn == -1) continue;
            _headerColumns[i] = headerColumn;
        }

        return true;
    }
}
