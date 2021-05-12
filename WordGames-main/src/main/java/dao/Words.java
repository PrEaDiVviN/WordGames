package dao;

import connection.ConnectionSingleton;
import model.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Words {
    private PreparedStatement insertStmt;
    private PreparedStatement getWordByName;
    private PreparedStatement modifyDefinition;
    private PreparedStatement modifyWordAndDefinition;
    private PreparedStatement getWordTarget;
    private Connection connection;

    public Words() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertString = "INSERT INTO cuvinte(`cuvant`, `definitie`) VALUES ( ? , ? )";
        String selectString = "SELECT * FROM cuvinte WHERE cuvant = ?";
        String updateString = "UPDATE cuvinte SET definitie=? WHERE cuvant=?";
        String updateWordAndDef = "UPDATE cuvinte SET definitie=?, cuvant=? WHERE cuvant=?";
        String getTarget = "SELECT * from cuvinte where id between ? AND ?";
        try {
            insertStmt = connection.prepareStatement(insertString);
            getWordByName = connection.prepareStatement(selectString);
            modifyDefinition = connection.prepareStatement(updateString);
            modifyWordAndDefinition = connection.prepareStatement(updateWordAndDef);
            getWordTarget = connection.prepareStatement(getTarget);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Word getWordByName(String wordToFind) {
        Word word = new Word();
        try {
            getWordByName.setString(1,wordToFind);

            ResultSet result = getWordByName.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitie"));

                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public void insertWord(Word word) {
        try {
            insertStmt.setString(1,word.getWord());
            insertStmt.setString(2,word.getDefinition());
            insertStmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void modifyDefinitionByName(String word, String newDefinition) {
        try {
            modifyDefinition.setString(1,newDefinition);
            modifyDefinition.setString(2,word);
            modifyDefinition.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void modifyNameAndDefinition(String word, String newName, String newDefinition) {
        try {
            modifyWordAndDefinition.setString(1,newDefinition);
            modifyWordAndDefinition.setString(2,newName);
            modifyWordAndDefinition.setString(3,word);
            modifyWordAndDefinition.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public List<Word> getWordTarget(int skip, int count) {
        try {
            getWordTarget.setInt(1,skip);
            getWordTarget.setInt(2,skip+count);
            ResultSet result = getWordTarget.executeQuery();

            List array = new ArrayList();
            while(result.next()){
                array.add(result.getString("id"));
                array.add(result.getString("cuvant"));
                array.add(result.getString("definitie"));
            }
            return array;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
