---
applyTo: '{modules/**,themes/**}'
---

This instructions file provides guidance for implementing coding instructions.

## Required Inputs

- Read agents file '.github/agents/agent.md' to get an understanding of current workspace.
- Read the content of folder 'modules/csv-viewer/src/main/resources/AI_overview_input' as example input 

### Task to execute

## Goal
Create a Liferay portlet named csv-viewer that reads the content of a pre-configured CSV file
and displays the content this in a table form. Furthermore it should be possible to filter 
this table for a subset of the columns.

Use the contents of the folder 'modules/csv-viewer/src/main/resources/AI_overview_input' as example input 

## Context
Users should be able to view the content of a CSV file and also filter the records by specific columns.
The columns that are to be filtered should be configurable.

## Requirements
- CSV file location is configurable.
- CSV file should be downloaded from the configured location.
- Parse data and show rows in a table
- Support basic validation
- Handle large files gracefully
- Use existing Liferay module conventions
- All columns should be sortable
- any URL links in the CSV fields should be represented as clickable links
- It should not be possible to download the CSV file or contents.
- Add a free text search functionality
- Columns to be filtered should be configurable
## Acceptance Criteria
- Portlet builds successfully
- Invalid rows are flagged
- Errors are shown clearly
- Module name is `csv-viewer`

## Notes
- Use Liferay module skeleton
- Validate file encoding and delimiter
- Ensure proper permissions and error handling