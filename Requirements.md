# Employees Task: Pair of employees who have worked together

### Create an application that identifies the pair of employees who have worked together on common projects for the longest period of time.

_Input data:_

A **CSV file** with data in the following format:```EmpID, ProjectID, DateFrom, DateTo```

_Sample data:_
   ```
      143, 12, 2013-11-01, 2014-01-05
      218, 10, 2012-05-16, NULL
      143, 10, 2009-01-01, 2011-04-27
      ...
   ```

_Sample output:_
   ```
   143, 218, 8
   ```

### Specific requirements
1. DateTo can be NULL (equal to today)
2. The input data must be loaded to the program from a CSV file
3. The task solution needs to be uploaded in [github.com](https://www.github.com), repository name must be in format: _{FirstName}-{LastName}-employees_ 

### Bonus points
1. Create an UI: The user picks up a file from the file system and after selecting it all common projects of the pair are displayed in datagrid with the following commands:
   Empoyee ID #1, Empoyee ID #2, Project ID, Days worked
2. More than one date format to be supported, extra points will be given if all date format are supported.