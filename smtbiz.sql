DROP DATABASE IF EXISTS smtbiz;
CREATE DATABASE smtbiz;
USE smtbiz;

CREATE TABLE customer (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	Name VARCHAR(32),
	Email VARCHAR(32),
	Mobile VARCHAR(32),
	PRIMARY KEY(ID)
);

INSERT INTO customer
	(Name, Email, Mobile)
VALUES
	("Joshua Farrell", "Joshua.Farrell@citems.com.au", "0412345678"),
	("Joe Bloggs", "Joe.Bloggs@citems.com.au", "0498765432"),
	("Tom Harry", "Tom.Harry@citems.com.au", "0411223344"),
	("Jane Smith", "Jane.Smith@citems.com.au", "0499887766"),
	("John Citizen", "John.Citizen@citems.com.au", "0412349876");

SELECT * FROM customer;