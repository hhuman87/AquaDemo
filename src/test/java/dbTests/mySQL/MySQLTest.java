package dbTests.mySQL;

import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLTest {

    private static Connection connection;

    // Set up the connection before tests
    public static void setUp(String schema) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+schema,
                "root", "H3rm@n");
    }

    // Clean up the connection after tests
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Test 1: Fetch user details by ID
    @Test
    public void testGetUserDetails() throws SQLException {
        setUp("users");
        String query = "SELECT * FROM auth WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        }
        tearDown();
    }

    // Test 2: Insert a new user
    @Test
    public void testInsertUser() throws SQLException {
        setUp("users");
        String query1 = "INSERT INTO auth (Name, Username, Password) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setString(1, "Grant Williams");
        preparedStatement1.setString(2, "grant.williams");
        preparedStatement1.setString(3, "bok9");

        int rowsInserted = preparedStatement1.executeUpdate();
        System.out.println("Rows inserted: " + rowsInserted);

        String query2 = "SELECT * FROM auth WHERE Username = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setString(1, "grant.williams"); // assuming username is grant.williams

        ResultSet resultSet = preparedStatement2.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            assertEquals("grant.williams",username);
        }
        tearDown();
    }

    // Test 3: Update user password by username
    @Test
    public void testUpdateUserEmail() throws SQLException {
        setUp("users");
        String query1 = "UPDATE auth SET Password = ? WHERE Username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query1);
        preparedStatement.setString(1, "bok21");
        preparedStatement.setString(2, "grant.williams"); // assuming username is grant.williams

        int rowsUpdated = preparedStatement.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);

        String query2 = "SELECT * FROM auth WHERE Username = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setString(1, "grant.williams"); // assuming username is grant.williams

        ResultSet resultSet = preparedStatement2.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            assertEquals("bok21",password);
        }
        tearDown();
    }

    // Test 4: Delete user by Username
    @Test
    public void testDeleteUserById() throws SQLException {
        setUp("users");
        String query1 = "DELETE FROM auth WHERE Username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query1);
        preparedStatement.setString(1, "grant.williams"); // assuming username is grant.williams

        int rowsDeleted = preparedStatement.executeUpdate();
        System.out.println("Rows deleted: " + rowsDeleted);

        String query2 = "SELECT COUNT(*) FROM auth";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query2);

        if (resultSet.next()) {
            int users = resultSet.getInt(1);
            System.out.println("Total users: " + users);

            assertEquals(2,users);
        }
        tearDown();
    }

    // Test 5: Count the number of users
    @Test
    public void testCountUsers() throws SQLException {
        setUp("users");
        String query = "SELECT COUNT(*) FROM auth";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            int users = resultSet.getInt(1);
            System.out.println("Total users: " + users);

            assertEquals(2,users);
        }
        tearDown();
    }

    // Test 6: Fetch users by username
    @Test
    public void testGetUsersByEmail() throws SQLException {
        setUp("users");
        String query = "SELECT * FROM auth WHERE Username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "john.smith"); // assuming username is john.smith

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            assertEquals("john.smith",username);
        }
        tearDown();
    }

    // Test 7: Fetch user details with their orders (JOIN)
    @Test
    public void testGetUserWithOrders() throws SQLException {
        setUp("sakila");
        String query = "SELECT r.rental_id, s.store_id FROM rental r JOIN store s ON r.rental_id = s.store_id WHERE r.rental_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int rentalID = resultSet.getInt("rental_id");
            int storeID = resultSet.getInt("store_id");

            System.out.println("Rental ID: " + rentalID);
            System.out.println("Store ID: " + storeID);

            assertEquals(rentalID,storeID);
        }
        tearDown();
    }

    // Test 8: Transfer funds (demonstrates transaction handling)
    @Test
    public void testTransferFunds() throws SQLException {
        setUp("sakila");
        try {
            connection.setAutoCommit(false);  // Start transaction

            String updateUserBalanceQuery = "UPDATE payment SET amount = payment.amount - ? WHERE rental_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateUserBalanceQuery);
            preparedStatement1.setDouble(1, 1.99);  // amount to deduct
            preparedStatement1.setInt(2, 1); // user ID 1
            preparedStatement1.executeUpdate();

            String updateAccountBalanceQuery = "UPDATE rental SET accounts = rental.accounts + ? WHERE rental_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(updateAccountBalanceQuery);
            preparedStatement2.setDouble(1, 1.99);  // amount to add
            preparedStatement2.setInt(2, 1); // account ID 1
            preparedStatement2.executeUpdate();

            connection.commit();  // Commit transaction
            System.out.println("Funds transferred successfully.");

            String query = "SELECT accounts FROM rental WHERE rental_id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, 1); // assuming user ID is 1

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                Object accounts = resultSet.getDouble("accounts");
                System.out.println("Account: " + accounts);

                double expectedAcc = 1.99;
                assertEquals(expectedAcc,accounts);
            }

            // Set table to default
            String updateUserBalanceQuery2 = "UPDATE payment SET amount = payment.amount + ? WHERE rental_id = ?";
            PreparedStatement preparedStatement3 = connection.prepareStatement(updateUserBalanceQuery2);
            preparedStatement3.setDouble(1, 1.99);  // amount to deduct
            preparedStatement3.setInt(2, 1); // user ID 1
            preparedStatement3.executeUpdate();

            String updateAccountBalanceQuery2 = "UPDATE rental SET accounts = rental.accounts - ? WHERE rental_id = ?";
            PreparedStatement preparedStatement4 = connection.prepareStatement(updateAccountBalanceQuery2);
            preparedStatement4.setDouble(1, 1.99);  // amount to add
            preparedStatement4.setInt(2, 1); // account ID 1
            preparedStatement4.executeUpdate();

            connection.commit();  // Commit transaction
            System.out.println("Funds transferred successfully.");

            String query2 = "SELECT accounts FROM rental WHERE rental_id = ?";
            PreparedStatement prepStatement2 = connection.prepareStatement(query2);
            prepStatement2.setInt(1, 1); // assuming user ID is 1

            ResultSet resultSet2 = prepStatement2.executeQuery();
            while (resultSet2.next()) {
                Object accounts = resultSet2.getDouble("accounts");
                System.out.println("Account: " + accounts);

                double expectedAcc = 0.00;
                assertEquals(expectedAcc,accounts);
            }
        } catch (SQLException e) {
            connection.rollback();  // Rollback if an error occurs
            System.out.println("Error occurred. Transaction rolled back.");
            throw e;
        }
        tearDown();
    }

    // Test 9: Fetch users with pagination (LIMIT)
    @Test
    public void testGetUsersWithPagination() throws SQLException {
        setUp("users");
        String query = "SELECT * FROM auth LIMIT ?, ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 0);  // offset = 0
        preparedStatement.setInt(2, 1);  // limit = 1

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            assertEquals(1,id);
            assertEquals("John Smith",name);
            assertEquals("john.smith",username);
            assertEquals("bok2",password);

            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        }
        tearDown();
    }

    // Test 10: Call a stored procedure to get user details by ID
    @Test
    public void testCallGetUserDetailsProcedure() throws SQLException {
        setUp("users");
        String query = "{CALL get_user_details(?)}";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = callableStatement.executeQuery();
        while (resultSet.next()) {

            // Check the first row of the result set
            assertEquals(1, resultSet.getInt("Id"), "Expected id is 1");
            assertEquals("John Smith", resultSet.getString("Name"), "Expected name is John Smith");
            assertEquals("john.smith", resultSet.getString("Username"), "Expected username is john.smith");
            assertEquals("bok2", resultSet.getString("Password"), "Expected password is bok2");

            // Move to the next row and assert values for second row
            assertTrue(resultSet.next(), "Result set should have more rows");
            assertEquals(2, resultSet.getInt("Id"), "Expected id is 2");
            assertEquals("Bakkies Botha", resultSet.getString("Name"), "Expected name is Bakkies Botha");
            assertEquals("bakkies.botha", resultSet.getString("Username"), "Expected username is bakkies.botha");
            assertEquals("bok4", resultSet.getString("Password"), "Expected password is bok4");

            // Assert there are no more rows
            assertFalse(resultSet.next(), "Result set should have no more rows");
        }
        System.out.println("Call was successful");
        tearDown();
    }
}
