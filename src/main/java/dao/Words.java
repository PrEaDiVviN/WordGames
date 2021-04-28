package dao;

import connection.ConnectionSingleton;
import model.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Words {
    private PreparedStatement insertStmt;
    private PreparedStatement getWordByName; //todo
    private Connection connection;

    public Words() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertString = new String("INSERT INTO cuvinte(`cuvant`, `definitie`) VALUES ( ? , ? )");
        try {
            insertStmt = connection.prepareStatement(insertString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //todo for getWordByName
    }

    public void insertWord(Word word) {
        try {
            insertStmt.setString(1,word.getWord());
            insertStmt.setString(2,word.getDefinition());
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
