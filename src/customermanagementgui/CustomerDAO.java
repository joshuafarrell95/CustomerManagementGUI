package customermanagementgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Customer 
 * @author Joshua Farrell
 * @version 1.00 21 Nov 2022
 */
public class CustomerDAO {
    public static void createaCustomerDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/"; // no database yet
        String user = "root";
        String password = "";
        Connection con = null;
        Statement stmt = null;
        String query;
        ResultSet result = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

            System.out.println("\n##############################");
            System.out.println(" Database is being created...");
            System.out.println("##############################\n");
            query = "DROP DATABASE IF EXISTS smtbiz;";
            stmt.executeUpdate(query);
            query = "CREATE DATABASE smtbiz;";
            stmt.executeUpdate(query);
            query = "USE smtbiz;";
            stmt.executeUpdate(query);
            query = """
                    CREATE TABLE customer (
                    	ID INTEGER NOT NULL AUTO_INCREMENT,
                    	Name VARCHAR(32),
                    	Email VARCHAR(52),
                        Mobile VARCHAR(32),
                        PRIMARY KEY(ID)
                    );
                    """;
            stmt.executeUpdate(query);
            query = """
                    INSERT INTO customer
                    	(Name, Email, Mobile)
                    VALUES
                        ("Joshua Farrell", "Joshua.Farrell@audit.wa.gov.au", "0412345678"),
                        ("Arthur Dent", "Arthur.Dent@audit.wa.gov.au", "0424242424"),
                        ("Ford Prefect", "Ford.Prefect@audit.wa.gov.au", "0403758000"),
                        ("Zaphod Beeblebrox", "Zaphod.Beeblebrox@audit.wa.gov.au", "0423232323"),
                        ("Douglas Adams", "Douglas.Adams@audit.wa.gov.au", "0419798082");
                    """;
            stmt.executeUpdate(query);
            query = "SELECT * FROM customer;";
            result = stmt.executeQuery(query); // execute the SQL query
        } catch (SQLException ex) {
            System.out.println("SQLException on database creation: " + ex.getMessage());
        } finally {
            try {
                if (result != null) {
                    result.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException caught: " + ex.getMessage());
            }
        }
    }
    
    public static void insertCustomer(String name, String email, String mobile) {
        String insertSQL = String.format("INSERT INTO customer (Name, Email, Mobile) VALUES ('%s', '%s', '%s');",
                name, email, mobile);
        int count = DBUtil.executeUpdate(insertSQL);

        if (count == 0) {
            System.out.println("!!! Failed to add a new Customer!!!");
        } else {
            System.out.println("\n>>> A New Customer Successfully Added!!!");
        }
    }

    public static void deleteCustomerByID(int id) throws NullPointerException {
        String deleteSQL = "DELETE FROM customer WHERE ID='" + id + "';";
        int count = DBUtil.executeUpdate(deleteSQL);

        if (count == 0) {
            System.out.println("!!! Customers NOT found!!!");
        } else {
            System.out.println("\n>>> Customers Successfully Deleted!!!");
        }
    }
    
    public static void updateCustomer(int id, String name, String email, String mobile) {
        String updateSQL = String.format("UPDATE customer SET Name = '%s', Email = '%s', Mobile = '%s' WHERE ID='" + id + "';",
                name, email, mobile);
        int count = DBUtil.executeUpdate(updateSQL);

        if (count == 0) {
            System.out.println("!!! Failed to update the Customer!!!");
        } else {
            System.out.println("\n>>> A Customer Successfully Updated!!!");
        }
    }

    public static Customer searchCustomerByID(int id) throws NullPointerException {
        String query = "SELECT * FROM customer WHERE ID=" + id + ";";
        Customer c = null;

        try {
            ResultSet rs = DBUtil.executeQuery(query);

            if (rs.next()) {
                c = new Customer();
                c.setId(rs.getInt("ID"));
                c.setName(rs.getString("Name"));
                c.setEmail(rs.getString("Email"));
                c.setMobile(rs.getString("Mobile"));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException on executeQuery: " + ex.getMessage());
        }

        return c;
    }
    
    public static ArrayList<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer;";
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            ResultSet rs = DBUtil.executeQuery(query);

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("ID"));
                c.setName(rs.getString("Name"));
                c.setEmail(rs.getString("Email"));
                c.setMobile(rs.getString("Mobile"));
                customers.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException on executeQuery: " + ex.getMessage());
        }

        return customers;
    }
}
