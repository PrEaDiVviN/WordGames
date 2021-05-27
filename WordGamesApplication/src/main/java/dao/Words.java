package dao;

import connection.ConnectionSingleton;
import exceptions.InvalidDataException;
import exceptions.InvalidRangeException;
import exceptions.NoWordException;
import model.Word;
import model.WordDuoDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Words {
    private PreparedStatement insertStmt;
    private PreparedStatement getWordByName;
    private PreparedStatement modifyDefinition;
    private PreparedStatement modifyDefinition2;
    private PreparedStatement modifyWordAndDefinition;
    private PreparedStatement getWordTarget;
    private PreparedStatement getWordById;
    private PreparedStatement checkWord;
    private Connection connection;

    public Words() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertString = "INSERT INTO cuvinte(`cuvant`, `definitieDEXONLINE`) VALUES ( ? , ? )";
        String selectString = "SELECT * FROM cuvinte WHERE cuvant = ?";
        String updateString = "UPDATE cuvinte SET definitieDEXONLINE=? WHERE cuvant=?";
        String updateString2 = "UPDATE cuvinte SET definitieROWIKTIONARY=? WHERE cuvant=?";
        String updateWordAndDef = "UPDATE cuvinte SET definitie=?, cuvant=? WHERE cuvant=?";
        String getTarget = "SELECT * from cuvinte where id between ? AND ?";
        String getWordByIdString = "SELECT * FROM cuvinte WHERE id = ?";
        String existsWord = "SELECT COUNT(*) AS numar FROM cuvinte WHERE cuvant = ?";
        try {
            insertStmt = connection.prepareStatement(insertString);
            getWordByName = connection.prepareStatement(selectString);
            modifyDefinition = connection.prepareStatement(updateString);
            modifyDefinition2 = connection.prepareStatement(updateString2);
            modifyWordAndDefinition = connection.prepareStatement(updateWordAndDef);
            getWordTarget = connection.prepareStatement(getTarget);
            getWordById = connection.prepareStatement(getWordByIdString);
            checkWord = connection.prepareStatement(existsWord);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Word getWordByName(String wordToFind) throws NoWordException{
        Word word = new Word();
        try {
            getWordByName.setString(1,wordToFind);

            ResultSet result = getWordByName.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieROWIKTIONARY").replace("\n"," "));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("There is not such word in the database! Please, lowercase the word if you send the word using uppercase letters!");
    }

    public Word getWordByNameDexOnline(String wordToFind) throws NoWordException{
        Word word = new Word();
        try {
            getWordByName.setString(1,wordToFind);

            ResultSet result = getWordByName.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieDEXONLINE").replace("\n"," "));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("There is not such word in the database! Please, lowercase the word if you send the word using uppercase letters!");
    }

    public Word getWordByNameRoWiktionary(String wordToFind) throws NoWordException{
        Word word = new Word();
        try {
            getWordByName.setString(1,wordToFind);

            ResultSet result = getWordByName.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieROWIKTIONARY").replace("\n"," "));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("There is not such word in the database! Please, lowercase the word if you send the word using uppercase letters!");
    }

    public Word getWordById(Integer id) throws NoWordException, InvalidRangeException {
        if(id < 1 || id > 85158)
            throw new InvalidRangeException("The range of the id should be between [1,85158]");
        Word word = new Word();
        try {
            getWordById.setInt(1,id);

            ResultSet result = getWordById.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieDEXONLINE"));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("No word with that id was found in the database or the id type is not a valid number");
    }

    public Word getWordByIdDexOnline(Integer id) throws NoWordException, InvalidRangeException {
        if(id < 1 || id > 85158)
            throw new InvalidRangeException("The range of the id should be between [1,85158]");
        Word word = new Word();
        try {
            getWordById.setInt(1,id);

            ResultSet result = getWordById.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieDEXONLINE"));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("No word with that id was found in the database or the id type is not a valid number");
    }

    public Word getWordByIdRoWiktionary(Integer id) throws NoWordException, InvalidRangeException {
        if(id < 1 || id > 85158)
            throw new InvalidRangeException("The range of the id should be between [1,85158]");
        Word word = new Word();
        try {
            getWordById.setInt(1,id);

            ResultSet result = getWordById.executeQuery();
            if(result.next()){
                word.setId(result.getInt("id"));
                word.setWord(result.getString("cuvant"));
                word.setDefinition(result.getString("definitieROWIKTIONARY"));
                return word;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("No word with that id was found in the database or the id type is not a valid number");
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
    public void modifyDefinitionROWIKTIONARYByName(String word, String newDefinition) {
        try {
            modifyDefinition2.setString(1,newDefinition);
            modifyDefinition2.setString(2,word);
            modifyDefinition2.executeUpdate();
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


    public List<Word> getWordTargetDexOnline(int skip, int count) throws InvalidRangeException, NoWordException {
        if(skip < 0 || skip > 85158 || count < 0 || count > 85158 || (skip + count) > 85128)
            throw new InvalidRangeException("The range specified between skip and count param is invalid. Keep the values between [1,85158]!");
        try {
            getWordTarget.setInt(1,skip);
            getWordTarget.setInt(2,skip+count);
            ResultSet result = getWordTarget.executeQuery();

            List<Word> array = new ArrayList<>();
            while(result.next()){
                Word word = new Word(result.getInt("id"), result.getString("cuvant"), result.getString("definitieDEXONLINE"));
                array.add(word);
            }
            return array;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("The current search is invalid. Please check the documentation for help!");
    }

    public List<Word> getWordTargetRoWiktionary(int skip, int count) throws InvalidRangeException, NoWordException {
        if(skip < 0 || skip > 85158 || count < 0 || count > 85158 || (skip + count) > 85128)
            throw new InvalidRangeException("The range specified between skip and count param is invalid. Keep the values between [1,85158]!");
        try {
            getWordTarget.setInt(1,skip);
            getWordTarget.setInt(2,skip+count);
            ResultSet result = getWordTarget.executeQuery();

            List<Word> array = new ArrayList<>();
            while(result.next()){
                Word word = new Word(result.getInt("id"), result.getString("cuvant"), result.getString("definitieROWIKTIONARY"));
                array.add(word);
            }
            return array;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("The current search is invalid. Please check the documentation for help!");
    }

    public List<WordDuoDefinition> getWordRange(int skip, int count) throws InvalidRangeException, NoWordException {
        if(skip < 0 || skip > 85158 || count < 0 || count > 85158 || (skip + count) > 85128)
            throw new InvalidRangeException("The range specified between skip and count param is invalid. Keep the values between [1,85158]!");
        try {
            getWordTarget.setInt(1,skip);
            getWordTarget.setInt(2,skip+count);
            ResultSet result = getWordTarget.executeQuery();

            List<WordDuoDefinition> array = new ArrayList<>();
            while(result.next()){
                WordDuoDefinition word = new WordDuoDefinition(result.getInt("id"), result.getString("cuvant"),result.getString("definitieDEXONLINE"),result.getString("definitieROWIKTIONARY"));
                array.add(word);
            }
            return array;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoWordException("The current search is invalid. Please check the documentation for help!");
    }

    public boolean existsWord(String word) throws InvalidDataException, NoWordException {
        if(word.contains("SELECT") || word.contains("DROP") || word.contains("UPDATE") || word.contains("DELETE"))
            throw new InvalidDataException("SQL injection is not permitted! Continue this and you may be sued!");
        try {
            checkWord.setString(1,word);
            ResultSet result = checkWord.executeQuery();
            result.next();
            if(result.getInt("numar") == 0)
                return false;
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoWordException("The word specified does not exists in the database!");
    }
}