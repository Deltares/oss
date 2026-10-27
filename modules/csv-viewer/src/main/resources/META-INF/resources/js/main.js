(function() {
    function getPortletRoot() {
        return document.querySelector('.csv-viewer-container');
    }

    function isUrl(value) {
        if (!value) {
            return false;
        }

        var v = value.trim().toLowerCase();
        return v.indexOf('http://') === 0 || v.indexOf('https://') === 0;
    }

    function renderSummary(data) {
        var root = getPortletRoot();
        var summary = document.getElementById(root.dataset.portletNamespace + 'summary');
        if (!summary) {
            return;
        }

        var totalRows = (data && data.totalRows) || 0;
        var invalidRows = (data && data.invalidRows) || 0;
        summary.textContent = 'Showing ' + totalRows + ' rows; ' + invalidRows + ' invalid row(s).';
    }

    function renderRows(rows, columns) {
        var root = getPortletRoot();
        var table = document.getElementById(root.dataset.portletNamespace + 'csv-table');
        var thead = table.querySelector('thead');
        var tbody = table.querySelector('tbody');

        thead.innerHTML = '';
        tbody.innerHTML = '';

        var headerRow = document.createElement('tr');

        columns.forEach(function(column) {
            var th = document.createElement('th');
            th.textContent = column;
            th.style.cursor = 'pointer';
            th.addEventListener('click', function() {
                sortRowsByColumn(column);
            });
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        rows.forEach(function(row) {
            var tr = document.createElement('tr');
            if (row && row.valid === false) {
                tr.style.backgroundColor = '#fff7f7';
                tr.title = row.invalidReason || 'Invalid row';
            }

            columns.forEach(function(column) {
                var td = document.createElement('td');
                var value = (row && row.values && row.values[column]) || '';

                if (isUrl(value)) {
                    var link = document.createElement('a');
                    link.href = value;
                    link.target = '_blank';
                    link.rel = 'noopener noreferrer';
                    link.textContent = value;
                    td.appendChild(link);
                } else {
                    td.textContent = value;
                }

                tr.appendChild(td);
            });

            tbody.appendChild(tr);
        });
    }

    function createFilterControls(columns, filterableColumns) {
        var root = getPortletRoot();
        var panel = document.getElementById(root.dataset.portletNamespace + 'filter-panel');
        panel.innerHTML = '';

        var enabled = (filterableColumns || '').split(',').map(function(item) {
            return item.trim();
        }).filter(Boolean);

        columns.forEach(function(column) {
            if (enabled.indexOf(column) === -1) {
                return;
            }

            var label = document.createElement('label');
            label.textContent = column + ':';

            var input = document.createElement('input');
            input.type = 'text';
            input.className = 'form-control';
            input.dataset.column = column;
            input.addEventListener('input', function() {
                applyFilters();
            });

            label.appendChild(input);
            panel.appendChild(label);
        });
    }

    function applyFilters() {
        var root = getPortletRoot();
        var searchInput = document.getElementById(root.dataset.portletNamespace + 'search');
        var searchText = (searchInput ? searchInput.value : '').toLowerCase();

        var filters = {};
        Array.from(document.querySelectorAll('[data-column]')).forEach(function(input) {
            filters[input.dataset.column] = (input.value || '').toLowerCase();
        });

        var rows = window.__csvRows || [];
        var columns = window.__csvColumns || [];

        var filtered = rows.filter(function(row) {
            var match = true;

            Object.keys(filters).forEach(function(column) {
                var value = (row.values[column] || '').toLowerCase();
                if (filters[column] && value.indexOf(filters[column]) === -1) {
                    match = false;
                }
            });

            if (searchText) {
                var haystack = JSON.stringify(row.values).toLowerCase();
                if (haystack.indexOf(searchText) === -1) {
                    match = false;
                }
            }

            return match;
        });

        renderRows(filtered, columns);
        renderSummary({ totalRows: filtered.length, invalidRows: rows.filter(function(item) { return item.valid === false; }).length });
    }

    function sortRowsByColumn(column) {
        var rows = window.__csvRows || [];
        rows.sort(function(a, b) {
            var av = (a.values[column] || '').toLowerCase();
            var bv = (b.values[column] || '').toLowerCase();

            if (av < bv) return -1;
            if (av > bv) return 1;
            return 0;
        });

        renderRows(rows, window.__csvColumns || []);
    }

    function showError(message) {
        var root = getPortletRoot();
        var errorBox = document.getElementById(root.dataset.portletNamespace + 'errors');
        if (errorBox) {
            errorBox.textContent = message;
            errorBox.classList.remove('hidden');
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        var root = getPortletRoot();
        if (!root) {
            return;
        }

        var searchInput = document.getElementById(root.dataset.portletNamespace + 'search');
        if (searchInput) {
            searchInput.addEventListener('input', function() {
                applyFilters();
            });
        }

        var data = root.dataset.json;
        if (!data) {
            showError('No CSV content found.');
            return;
        }

        var rows = data.rows || [];
        var columns = data.columns || [];
        window.__csvRows = rows;
        window.__csvColumns = columns;

        createFilterControls(columns, root.dataset.filterableColumns);
        renderRows(rows, columns);
        renderSummary(data);
    });
})();
