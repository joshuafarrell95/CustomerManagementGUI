# CustomerManagementGUI

## Instructions
Before running this program, start your MySQL database engine. You must also have JavaFX-SDK and JavaFX-JMODS installed on your system to create a custom Java Runtime Environment.

**IMPORTANT:** The batch files must be run in this order: Compile.bat > CreateJAR.bat > CreateJRE.bat > Launch.bat
If CreateJRE.bat is stuck, please press the ENTER key to continue.

Your environment variables must be set up for your user profile (not your system).
*PATH_TO_FX* = your JavaFX-SDK lib directory (e.g, C:\Users\[your username]\Documents\javafx-sdk-18.0.2\lib)
*PATH_TO_FX_JMOD* = your JavaFX-JMODS root directory (e.g., C:\Users\[your username]\Documents\javafx-jmods-18.0.2)

Once CreateJRE.bat is run, this program is able to run independently on any Windows system as long as you copy the directories "app" (containing the program) and "jre" (containing the custom Java Runtime Environment that runs the program), and the batch file Launch.bat (which is a launcher that runs the program using the custom JRE).

## Project Description
### Summary
This application was created as a part of my Certificate IV in Information Technology (Programming) at South Metropolitan TAFE in 2022. The program is written using a Model-View-Controller pattern which ensures that functionality of the program is separated between different elements.

This program uses Customer class objects as the model to store an indefinite amount of customers where memory permits with five attributes; Name, Mobile Number and Email (placeholder customer names, emails and mobile numbers were used). This project implements JAVAFX as the view which is used to provide a graphical user interface for the program, and JDBC as the controller which is used to manipulate a MySQL database. 

### SQL Technical Details
The program has the option to create a new *smtbiz* database.
```SQL
DROP DATABASE IF EXISTS smtbiz;
CREATE DATABASE smtbiz;
USE smtbiz;
```

A new *customer* table is created to store the customers' information. Each customer has a read-only *ID* which is used as a primary key for the *customer* table. As the *smtbiz* database only contains the *customer* table, the latter is not required to be normalized.
```SQL
CREATE TABLE customer (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	Name VARCHAR(32),
	Email VARCHAR(32),
	Mobile VARCHAR(32),
	PRIMARY KEY(ID)
);
```

Placeholder information is inserted into the *customer* table to demonstrate the program.
```SQL
INSERT INTO customer
	(Name, Email, Mobile)
VALUES
	("Joshua Farrell", "Joshua.Farrell@example.com", "0412345678"),
	("Joe Bloggs", "Joe.Bloggs@example.com", "0498765432"),
	("Tom Harry", "Tom.Harry@example.com", "0411223344"),
	("Jane Smith", "Jane.Smith@example.com", "0499887766"),
	("John Citizen", "John.Citizen@example.com", "0412349876");
```
