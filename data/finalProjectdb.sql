/*
Grant Robertson
COMP 3700
Assignment 3
SQL Code (Physical Database design
*/

--Table Declarations
CREATE TABLE Products (
ProductID int not null PRIMARY KEY,
Name varchar(100),
Price float,
Quantity int
);


CREATE TABLE Customers (
CustomerID int not null PRIMARY KEY,
Name varchar(100),
Address varchar(100),
PhoneNumber varchar(100)
);

CREATE TABLE Purchases (
PurchaseID int not null PRIMARY KEY,
CustomerID int,
ProductID int,
Price float,
Quantity INT,
Cost float,
Tax float,
TotalCost float,
PurchaseDate varchar(100),
FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

--Data Insertion

--Products
INSERT INTO Products(productId, name, price, taxRate)
VALUES (1, 'Apples', 2.99, 0.09);

INSERT INTO Products(productId, name, price, taxRate)
VALUES (2, 'Pears', 1.59, 0.09);

INSERT INTO Products(productId, name, price, taxRate)
VALUES (3, 'Bananas', 3.00, 0.05);

INSERT INTO Products(productId, name, price, taxRate)
VALUES (4, 'Oranges', 0.75, 0.08);

INSERT INTO Products(productId, name, price, taxRate)
VALUES (5, 'Grapes', 4.26, 0.04);


--Customers
INSERT INTO Customers(CustomerId, name, address, phoneNumber)
VALUES (1, 'John Smith', 'Auburn AL', 3341234567);

INSERT INTO Customers(CustomerId, name, address, phoneNumber)
VALUES (2, 'Jack Black', 'Atlanta GA', 6787704040);

INSERT INTO Customers(CustomerId, name, address, phoneNumber)
VALUES (3, 'Grant Robertson', 'Opelika AL', 6574839302);

INSERT INTO Customers(CustomerId, name, address, phoneNumber)
VALUES (4, 'Scott Pilgrim', 'Toronto CA', 3657821922);

INSERT INTO Customers(CustomerId, name, address, phoneNumber)
VALUES (5, 'Zachary Cristofferson', 'Denver CO', 7729452031);


--Purchases

/* "John Buys a pear" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (1, 1, 2, 'September 10th', ((SELECT price FROM Products WHERE productId = 2)*1), 
		((SELECT taxRate FROM Products WHERE productId = 2)*(SELECT price FROM Products WHERE productId = 2)*1), 1, 
		(((SELECT price FROM Products WHERE productId = 2)*1)+((SELECT taxRate FROM Products WHERE productId = 2)*(SELECT price FROM Products WHERE productId = 2)*1)));

/* "Jack Buys Grapes" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (2, 2, 5, 'September 2nd', ((SELECT price FROM Products WHERE productId = 5)*2), 
		((SELECT taxRate FROM Products WHERE productId = 5)*(SELECT price FROM Products WHERE productId = 5)*2), 2, 
		(((SELECT price FROM Products WHERE productId = 5)*2)+((SELECT taxRate FROM Products WHERE productId = 5)*(SELECT price FROM Products WHERE productId = 5)*2)));

/* "Grant Buys Bananas" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (3, 3, 3, 'September 9th', ((SELECT price FROM Products WHERE productId = 3)*10), 
		((SELECT taxRate FROM Products WHERE productId = 3)*(SELECT price FROM Products WHERE productId = 3)*10), 10, 
		(((SELECT price FROM Products WHERE productId = 3)*10)+((SELECT taxRate FROM Products WHERE productId = 3)*(SELECT price FROM Products WHERE productId = 3)*10)));


/* "Zach buys Grapes" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (4, 5, 5, 'September 12th', ((SELECT price FROM Products WHERE productId = 5)*100), 
		((SELECT taxRate FROM Products WHERE productId = 5)*(SELECT price FROM Products WHERE productId = 5)*100), 100, 
		(((SELECT price FROM Products WHERE productId = 5)*100)+((SELECT taxRate FROM Products WHERE productId = 5)*(SELECT price FROM Products WHERE productId = 5)*100)));

/* "Scott Buys a pear" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (5, 4, 2, 'September 13th', ((SELECT price FROM Products WHERE productId = 2)*643), 
		((SELECT taxRate FROM Products WHERE productId = 2)*(SELECT price FROM Products WHERE productId = 2)*643), 643, 
		(((SELECT price FROM Products WHERE productId = 2)*643)+((SELECT taxRate FROM Products WHERE productId = 2)*(SELECT price FROM Products WHERE productId = 2)*643)));


/* "Jack buys an apple" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (6, 2, 1, 'September 13th', ((SELECT price FROM Products WHERE productId = 1)*1), 
		((SELECT taxRate FROM Products WHERE productId = 1)*(SELECT price FROM Products WHERE productId = 1)*1), 1, 
		(((SELECT price FROM Products WHERE productId = 1)*1)+((SELECT taxRate FROM Products WHERE productId = 1)*(SELECT price FROM Products WHERE productId = 1)*1)));


/* "John Buys an orange" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (7, 1, 4, 'September 13th', ((SELECT price FROM Products WHERE productId = 4)*1), 
		((SELECT taxRate FROM Products WHERE productId = 4)*(SELECT price FROM Products WHERE productId = 4)*1), 1, 
		(((SELECT price FROM Products WHERE productId = 4)*1)+((SELECT taxRate FROM Products WHERE productId = 4)*(SELECT price FROM Products WHERE productId = 4)*1)));


/* "Grant Buys oranges" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (8, 3, 4, 'September 13th', ((SELECT price FROM Products WHERE productId = 4)*2), 
		((SELECT taxRate FROM Products WHERE productId = 4)*(SELECT price FROM Products WHERE productId = 4)*2), 2, 
		(((SELECT price FROM Products WHERE productId = 4)*2)+((SELECT taxRate FROM Products WHERE productId = 4)*(SELECT price FROM Products WHERE productId = 4)*2)));



/* Zach buys apples" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (9, 5, 1, 'September 13th', ((SELECT price FROM Products WHERE productId = 1)*10), 
		((SELECT taxRate FROM Products WHERE productId = 1)*(SELECT price FROM Products WHERE productId = 1)*10), 10, 
		(((SELECT price FROM Products WHERE productId = 1)*10)+((SELECT taxRate FROM Products WHERE productId = 1)*(SELECT price FROM Products WHERE productId = 1)*10)));



/* "Jack Buys bannanas" */
INSERT INTO Purchases(PurchaseId, CustomerNum, productNum, purchaseDate, price, tax, quantity, totalCost)
VALUES (10, 2, 3, 'September 13th', ((SELECT price FROM Products WHERE productId = 3)*2), 
		((SELECT taxRate FROM Products WHERE productId = 3)*(SELECT price FROM Products WHERE productId = 3)*2), 2, 
		(((SELECT price FROM Products WHERE productId = 3)*2)+((SELECT taxRate FROM Products WHERE productId = 3)*(SELECT price FROM Products WHERE productId = 3)*2)));


