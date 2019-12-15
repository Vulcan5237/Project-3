import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLiteDataAdapter implements IDataAdapter {

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public int saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();
            ProductModel p = loadProduct(product.mProductID); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Products WHERE ProductID = " + product.mProductID);
            }

            String sql = "INSERT INTO Products(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);

            stmt.executeUpdate(sql);


        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_SAVE_FAILED;
        }

        return PRODUCT_SAVE_OK;
    }

    public CustomerModel loadCustomer(int id) {
        CustomerModel customer = null;

        try {
            String sql = "SELECT * FROM Customers WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer = new CustomerModel();
                customer.mCustomerID = id;
                customer.mName = rs.getString("Name");
                customer.mAddress = rs.getString("Address");
                customer.mPhone = rs.getString("PhoneNumber");
                customer.mUserID = rs.getInt("UserID");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
    public int saveCustomer (CustomerModel customer) {
        try {
            Statement stmt = conn.createStatement();
            CustomerModel c = loadCustomer(customer.mCustomerID); // check if this product exists
            if (c != null) {
                stmt.executeUpdate("DELETE FROM Customers WHERE CustomerID = " + customer.mCustomerID);
            }

            String sql = "INSERT INTO Customers(CustomerID, Name, Address, PhoneNumber, UserID) VALUES " + customer;
            System.out.println(sql);

            stmt.executeUpdate(sql);


        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_SAVE_FAILED;
        }

        return CUSTOMER_SAVE_OK;
    }

    public PurchaseModel loadPurchase (int id) {
        PurchaseModel purchase = null;

        try {
            String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                purchase = new PurchaseModel();
                purchase.mPurchaseID = id;
                purchase.mCustomerID = Integer.parseInt(rs.getString("CustomerID"));
                purchase.mProductID = Integer.parseInt(rs.getString("ProductID"));
                purchase.mPrice = Double.parseDouble(rs.getString("Price"));
                purchase.mQuantity = Double.parseDouble(rs.getString("Quantity"));
                purchase.mCost = Double.parseDouble(rs.getString("Cost"));
                purchase.mTax = Double.parseDouble(rs.getString("Tax"));
                purchase.mTotal = Double.parseDouble(rs.getString("TotalCost"));
                purchase.mDate = rs.getString("PurchaseDate");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }
    public int savePurchase(PurchaseModel purchase) {
        try {
            Statement stmt = conn.createStatement();
            PurchaseModel p = loadPurchase(purchase.mPurchaseID);
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Purchases WHERE PurchaseID = " + purchase.mPurchaseID);
            }

            String sql = ("INSERT INTO Purchases(PurchaseID, CustomerID, ProductID, Price, Quantity," +
                    " Cost, Tax, TotalCost, PurchaseDate) VALUES " + purchase);
            System.out.println(sql);

            stmt.executeUpdate(sql);


        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_SAVE_FAILED;
        }

        return PURCHASE_SAVE_OK;

    }

    public UserModel loadUser(String uName) {
        UserModel user = null;
        try {
            String sql = ("SELECT Username, Password, Name, UserType, UserID FROM Users WHERE Username = \"" + uName + "\"");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = rs.getString("Username");
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Name");
                user.mUserType = rs.getInt("UserType");
                user.mUserID = rs.getInt("UserID");
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    public int saveUser(UserModel user){
        try {
            Statement stmt = conn.createStatement();
            UserModel u = loadUser(user.mUsername); // check if this user exists
            if (u != null) {
                stmt.executeUpdate("DELETE FROM Users WHERE Username = \"" + user.mUsername + "\"");
            }

            String sql = ("INSERT INTO Users(Username, Password, Name, UserType, UserID) VALUES " + user);
            System.out.println(sql);

            stmt.executeUpdate(sql);


        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return USER_SAVE_FAILED;
        }

        return USER_SAVE_OK;
    }

    public CustomerModel loadCustomerByUser(int uID) {
        CustomerModel customer = null;

        try {
            String sql = "SELECT * FROM Customers WHERE UserID = " + uID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer = new CustomerModel();
                customer.mCustomerID = uID;
                customer.mName = rs.getString("Name");
                customer.mAddress = rs.getString("Address");
                customer.mPhone = rs.getString("PhoneNumber");
                customer.mUserID = rs.getInt("UserID");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public ArrayList<PurchaseModel> loadPurchaseHistory(){
       // PurchaseListModel retVal = new PurchaseListModel();
        ArrayList<PurchaseModel> list = new ArrayList<PurchaseModel>();
        try {
            String sql = "SELECT * FROM Purchases";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mPurchaseID = Integer.parseInt(rs.getString("PurchaseID"));
                purchase.mCustomerID = Integer.parseInt(rs.getString("CustomerID"));
                purchase.mProductID = Integer.parseInt(rs.getString("ProductID"));
                purchase.mPrice = Double.parseDouble(rs.getString("Price"));
                purchase.mQuantity = Double.parseDouble(rs.getString("Quantity"));
                purchase.mCost = Double.parseDouble(rs.getString("Cost"));
                purchase.mTax = Double.parseDouble(rs.getString("Tax"));
                purchase.mTotal = Double.parseDouble(rs.getString("TotalCost"));
                purchase.mDate = rs.getString("PurchaseDate");
                list.add(purchase);
            }

        }
        catch (Exception e) {
            System.out.println("Unable to make query");
        }
        return list;
    }

    public ArrayList<ProductModel> searchProductByPrice(double price){
        ArrayList<ProductModel> list = new ArrayList<ProductModel>();
        try {
            String sql = "SELECT * FROM Products WHERE Price <= " + price;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
                list.add(product);
            }

        }
        catch (Exception e) {
            System.out.println("Unable to make query");
        }
        return list;

    }

    public ArrayList<ProductModel> searchProductByName(String name) {
        ArrayList<ProductModel> list = new ArrayList<ProductModel>();
        try {
            String sql = "SELECT * FROM Products WHERE Name = \"" + name + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
                list.add(product);
            }

        }
        catch (Exception e) {
            System.out.println("Unable to make query");
        }
        return list;
    }

}
