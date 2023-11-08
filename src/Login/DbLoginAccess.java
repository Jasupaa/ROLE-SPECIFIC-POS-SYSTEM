/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jasper
 */
class DbLoginAccess {
    public String checkAccessCode(int enteredCode) {
     String role = null;

    // Database connection details
    String url = "jdbc:mysql://localhost:3306/RSPOSS_TEST";
    String username = "root";
    String password = "";

    try {
        // Establish a database connection
        
        Connection connection = DriverManager.getConnection(url, username, password);

        // Check for 'cashier' role
        String sql = "SELECT cashier_code FROM Login_Access WHERE cashier_code = ?";
        role = executeQuery(connection, sql, enteredCode, "cashier");

        if (role == null) {
            // Check for 'kitchen' role
            sql = "SELECT kitchen_code FROM Login_Access WHERE kitchen_code = ?";
            role = executeQuery(connection, sql, enteredCode, "kitchen");
        }

        if (role == null) {
            // Check for 'admin' role
            sql = "SELECT admin_code FROM Login_Access WHERE admin_code = ?";
            role = executeQuery(connection, sql, enteredCode, "admin");
        }

        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
        
    }

    return role;
    
    
    }
    
    private String executeQuery(Connection connection, String sql, int code, String role) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, code);
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if (resultSet.next()) {
        return role;
    }
    
    resultSet.close();
    preparedStatement.close();
    return null;
}
}
