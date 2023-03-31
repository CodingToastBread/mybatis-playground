This package contains tests for the following scenario:

1. An external user parses data in the form of an Excel file into JSON and sends it as an HTTP request body.
2. The system creates a table based on the header information in the Excel file.
3. The system inserts rows from the body of the Excel file into the previously created table.
4. The system adds a PRIMARY KEY constraint to the final table.
