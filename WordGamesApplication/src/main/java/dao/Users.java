package dao;

import connection.ConnectionSingleton;
import model.User;
import model.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Users {
    private PreparedStatement insertStmt;
    private PreparedStatement selectStmt; //user by id
    private PreparedStatement loginStmt;
    private Connection connection;

    public Users() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertString = "INSERT INTO user(`id`, `username`,`password`) VALUES ( ? , ? , ? )";
        String selectString = "SELECT * FROM user WHERE id = ?";
        String loginString = "SELECT password FROM user WHERE username=?";
        try {
            insertStmt = connection.prepareStatement(insertString);
            selectStmt = connection.prepareStatement(selectString);
            loginStmt = connection.prepareStatement(loginString);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public User getUserById(Integer id) {
        User user = new User();
        try {
            selectStmt.setInt(1,id);
            ResultSet result = selectStmt.executeQuery();
            if(result.next()){
                user.setId(result.getInt("id"));
                user.setUserName(result.getString("username"));
                return user;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public boolean login(String username, String password) {
        try {
            loginStmt.setString(1,username);
            ResultSet result = loginStmt.executeQuery();
            if(result.next()){
                String passwordFromDb = result.getString("password");
                if(passwordFromDb.equals(password))
                    return true;
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public boolean register(String username, String password, String confirmPass) {
        try {
            if(password.equals(confirmPass)) {
                insertStmt.setInt(1,(new Random().nextInt()));
                insertStmt.setString(2, username);
                insertStmt.setString(3, password);
                insertStmt.executeUpdate();
                return true;
            }
            else return false;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}