package dao;

import connection.ConnectionPool;
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
    private PreparedStatement updateScoreStmt;
    private PreparedStatement getScoreStmt;
    private Connection connection;
    private boolean connectionPool = false;

    public Users() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertString = "INSERT INTO user(`id`, `username`,`password`,`score`) VALUES ( ? , ? , ? , 0)";
        String selectString = "SELECT * FROM user WHERE id = ?";
        String loginString = "SELECT password FROM user WHERE username=?";
        String updateScoreString = "UPDATE user SET score=? WHERE username=?";
        String getScoreString = "SELECT score FROM user WHERE username=?";
        try {
            insertStmt = connection.prepareStatement(insertString);
            selectStmt = connection.prepareStatement(selectString);
            loginStmt = connection.prepareStatement(loginString);
            updateScoreStmt = connection.prepareStatement(updateScoreString);
            getScoreStmt = connection.prepareStatement(getScoreString);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setConnectionHikaryCp() {
        this.connectionPool = true;
    }


    public void checkEndConnection() {
        if(this.connectionPool) {
            try {
                this.connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void reInitializeObject() {
        String insertString = "INSERT INTO user(`id`, `username`,`password`,`score`) VALUES ( ? , ? , ? , 0)";
        String selectString = "SELECT * FROM user WHERE id = ?";
        String loginString = "SELECT password FROM user WHERE username=?";
        String updateScoreString = "UPDATE user SET score=? WHERE username=?";
        String getScoreString = "SELECT score FROM user WHERE username=?";
        try {
            insertStmt = connection.prepareStatement(insertString);
            selectStmt = connection.prepareStatement(selectString);
            loginStmt = connection.prepareStatement(loginString);
            updateScoreStmt = connection.prepareStatement(updateScoreString);
            getScoreStmt = connection.prepareStatement(getScoreString);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void checkPool() {
        if(this.connectionPool) {
            try {
                Connection con = ConnectionPool.getConnection();
                setConnection(con);
                reInitializeObject();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public Integer getScore(String username) {
        checkPool();
        try {
            getScoreStmt.setString(1,username);

            ResultSet result = getScoreStmt.executeQuery();
            if(result.next()){
                Integer score = result.getInt("score");
                return score;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        checkEndConnection();
        return null;
    }

    public void updateScore(String username) {
        checkPool();
        try {
            Integer score = getScore(username);
            updateScoreStmt.setInt(1,score + 1);
            updateScoreStmt.setString(2,username);
            updateScoreStmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        checkEndConnection();
    }

    public User getUserById(Integer id) {
        checkPool();
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
        checkEndConnection();
        return null;
    }

    public boolean login(String username, String password) {
        checkPool();
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
        checkEndConnection();
        return false;
    }

    public boolean register(String username, String password, String confirmPass) {
        checkPool();
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
        checkEndConnection();
        return false;
    }
}