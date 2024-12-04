package dbTests.mySQL;

import org.junit.jupiter.api.*;
import java.sql.*;

public class MySQLTest {

    private static Connection connection;

    // Set up the connection before tests
    @BeforeAll
    public static void setUp() throws SQLException {
        // Establish the connection to the database (adjust URL, user, and password accordingly)
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "password");
    }

    // Clean up the connection after tests
    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Test 1: Fetch user details by ID
    @Test
    public void testGetUserDetails() throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
            System.out.println("Email: " + resultSet.getString("email"));
        }
    }

    // Test 2: Insert a new user
    @Test
    public void testInsertUser() throws SQLException {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "newuser");
        preparedStatement.setString(2, "password123");
        preparedStatement.setString(3, "newuser@example.com");

        int rowsInserted = preparedStatement.executeUpdate();
        System.out.println("Rows inserted: " + rowsInserted);
    }

    // Test 3: Update user email by ID
    @Test
    public void testUpdateUserEmail() throws SQLException {
        String query = "UPDATE users SET email = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "updatedemail@example.com");
        preparedStatement.setInt(2, 1); // assuming user ID is 1

        int rowsUpdated = preparedStatement.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);
    }

    // Test 4: Delete user by ID
    @Test
    public void testDeleteUserById() throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1); // assuming user ID is 1

        int rowsDeleted = preparedStatement.executeUpdate();
        System.out.println("Rows deleted: " + rowsDeleted);
    }

    // Test 5: Count the number of users
    @Test
    public void testCountUsers() throws SQLException {
        String query = "SELECT COUNT(*) FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Total users: " + resultSet.getInt(1));
        }
    }

    // Test 6: Fetch users by email
    @Test
    public void testGetUsersByEmail() throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "user@example.com");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
        }
    }

    // Test 7: Fetch user details with their orders (JOIN)
    @Test
    public void testGetUserWithOrders() throws SQLException {
        String query = "SELECT u.username, o.order_id FROM users u JOIN orders o ON u.id = o.user_id WHERE u.id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Username: " + resultSet.getString("username"));
            System.out.println("Order ID: " + resultSet.getInt("order_id"));
        }
    }

    // Test 8: Transfer funds (demonstrates transaction handling)
    @Test
    public void testTransferFunds() throws SQLException {
        try {
            connection.setAutoCommit(false);  // Start transaction

            String updateUserBalanceQuery = "UPDATE users SET balance = balance - ? WHERE id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateUserBalanceQuery);
            preparedStatement1.setDouble(1, 50.0);  // amount to deduct
            preparedStatement1.setInt(2, 1); // user ID 1
            preparedStatement1.executeUpdate();

            String updateAccountBalanceQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(updateAccountBalanceQuery);
            preparedStatement2.setDouble(1, 50.0);  // amount to add
            preparedStatement2.setInt(2, 1); // account ID 1
            preparedStatement2.executeUpdate();

            connection.commit();  // Commit transaction
            System.out.println("Funds transferred successfully.");
        } catch (SQLException e) {
            connection.rollback();  // Rollback if an error occurs
            System.out.println("Error occurred. Transaction rolled back.");
            throw e;
        }
    }

    // Test 9: Fetch users with pagination (LIMIT)
    @Test
    public void testGetUsersWithPagination() throws SQLException {
        String query = "SELECT * FROM users LIMIT ?, ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 0);  // offset = 0
        preparedStatement.setInt(2, 5);  // limit = 5

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
        }
    }

    // Test 10: Call a stored procedure to get user details by ID
    @Test
    public void testCallGetUserDetailsProcedure() throws SQLException {
        String query = "{CALL get_user_details(?)}";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, 1); // assuming user ID is 1

        ResultSet resultSet = callableStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
            System.out.println("Email: " + resultSet.getString("email"));
        }
    }
}