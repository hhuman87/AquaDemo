package dbTests.mySQL;

import java.sql.*;

public class My_JDBC {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/users",
                    "root",
                    "H3rm@n"
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("Select * from auth");

            while(resultSet1.next()) {
                System.out.print(resultSet1.getInt("Id") + " | ");
                System.out.print(resultSet1.getString("Name") + " | ");
                System.out.print(resultSet1.getString("UserName") + " | ");
                System.out.println(resultSet1.getString("Password") + " | ");
            }

            String query = "SELECT * FROM auth WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 2); // x = userId
            ResultSet resultSet2 = preparedStatement.executeQuery();

            if (resultSet2.next()) {
                int id = resultSet2.getInt("Id");
                String name = resultSet2.getString("Name");
                String username = resultSet2.getString("Username");
                String password = resultSet2.getString("Password");

                System.out.println("Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
