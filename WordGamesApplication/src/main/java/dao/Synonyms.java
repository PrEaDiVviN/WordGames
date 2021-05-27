package dao;

import connection.ConnectionSingleton;
import exceptions.NoSynonymException;
import model.Synonym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Synonyms {
    private PreparedStatement insertStmt;
    private PreparedStatement getSynonymById;
    private PreparedStatement getSynonymsByIdWord;
    private PreparedStatement getSynonymByIdWordAndType;
    private PreparedStatement modifySynonymTypeById;
    private PreparedStatement modifySynonymTextById;
    private PreparedStatement modifySynonymTextAndTypeById;
    private PreparedStatement modifySynonymTextByIdWordAndType;
    private PreparedStatement modifySynonymTypeByIdWordAndType;
    private PreparedStatement modifySynonymTextAndTypeByIdWordAndType;
    private Connection connection;

    public Synonyms() { //uses Singleton Connection to the database
        connection = ConnectionSingleton.getConnection();
        String insertLine = "INSERT INTO sinonime(`id_cuvant`, `tip_sinonime`, text_sinonime) VALUES ( ?, ?, ? )";
        String getLineById = "SELECT * FROM sinonime WHERE id_sinonim = ?";
        String getLinesByIdWord = "SELECT * FROM sinonime WHERE id_cuvant = ?";
        String getLineByIdWordAndType = "SELECT * FROM sinonime WHERE id_cuvant = ? AND tip_sinonime = ?";
        String modifyLineTypeById = "UPDATE sinonime SET tip_sinonime = ? WHERE id_sinonim = ?";
        String modifyLineTextById = "UPDATE sinonime SET text_sinonime = ? WHERE id_sinonim = ?";
        String modifyLineTextAndTypeById = "UPDATE sinonime SET text_sinonime = ?, tip_sinonime = ? WHERE id_sinonim = ?";
        String modifyLineTypeByIdWordAndType = "UPDATE sinonime SET tip_sinonime = ? WHERE id_cuvant = ? AND tip_sinonime = ?";
        String modifyLineTextByIdWordAndType = "UPDATE sinonime SET text_sinonime = ? WHERE id_cuvant = ? AND tip_sinonime = ?";
        String modifyLineTextAndTypeByIdWordAndType = "UPDATE sinonime SET text_sinonime = ?, tip_sinonime = ? WHERE id_cuvant = ? AND tip_sinonime = ?";
        try {
            insertStmt = connection.prepareStatement(insertLine);
            getSynonymById = connection.prepareStatement(getLineById);
            getSynonymsByIdWord = connection.prepareStatement(getLinesByIdWord);
            getSynonymByIdWordAndType = connection.prepareStatement(getLineByIdWordAndType);
            modifySynonymTypeById = connection.prepareStatement(modifyLineTypeById);
            modifySynonymTextById = connection.prepareStatement(modifyLineTextById);
            modifySynonymTextAndTypeById = connection.prepareStatement(modifyLineTextAndTypeById);
            modifySynonymTypeByIdWordAndType = connection.prepareStatement(modifyLineTypeByIdWordAndType);
            modifySynonymTextByIdWordAndType = connection.prepareStatement(modifyLineTextByIdWordAndType);
            modifySynonymTextAndTypeByIdWordAndType = connection.prepareStatement(modifyLineTextAndTypeByIdWordAndType);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void insertSynonym(Synonym synonym) {
        try {
            insertStmt.setInt(1,synonym.getIdCuvant());
            insertStmt.setString(2,synonym.getTipSinonime());
            insertStmt.setString(3,synonym.getTextSinonime());
            insertStmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Synonym getSynonymById(int id) throws  NoSynonymException {
        try{
            getSynonymById.setInt(1,id);
            ResultSet result = getSynonymById.executeQuery();
            result.next();
            Synonym synonym = new Synonym(result.getInt("id_sinonim"), result.getInt("id_cuvant"), result.getString("tip_sinonime"), result.getString("text_sinonime"));
            return synonym;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoSynonymException("Could not find synonym with the specified id!");
    }

    public List<Synonym> getSynonymsByWordId(int wordId) throws NoSynonymException {
        try {
            getSynonymsByIdWord.setInt(1,wordId);
            ResultSet result = getSynonymsByIdWord.executeQuery();

            List<Synonym> array = new ArrayList();
            while(result.next()){
                Synonym synonym = new Synonym(result.getInt("id_sinonim"), result.getInt("id_cuvant"), result.getString("tip_sinonime"), result.getString("text_sinonime"));
                array.add(synonym);
            }
            return array;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        throw new NoSynonymException("There is no data regarding synonyms about the specified word in the database!");
    }

    public Synonym getSynonymByIdWordAndType(int idWord, String type) throws NoSynonymException{
        try{
            getSynonymByIdWordAndType.setInt(1,idWord);
            getSynonymByIdWordAndType.setString(2,type);
            ResultSet result = getSynonymByIdWordAndType.executeQuery();
            result.next();
            Synonym synonym = new Synonym(result.getInt("id_sinonim"), result.getInt("id_cuvant"), result.getString("tip_sinonime"), result.getString("text_sinonime"));
            return synonym;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoSynonymException("Could not find synonym with the specified id_word and word_type!");
    }

    public void modifySynonymTypeById(int id, String newType) throws  NoSynonymException{
        try {
            modifySynonymTypeById.setString(1,newType);
            modifySynonymTypeById.setInt(2,id);
            modifySynonymTypeById.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that id in the database!");
    }

    public void modifySynonymTextById(int id, String newText) throws  NoSynonymException{
        try {
            modifySynonymTextById.setString(1,newText);
            modifySynonymTextById.setInt(2,id);
            modifySynonymTextById.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that id in the database!");
    }

    public void modifySynonymTextAndTypeById(int id, String newText, String newType) throws  NoSynonymException{
        try {
            modifySynonymTextAndTypeById.setString(1,newText);
            modifySynonymTextAndTypeById.setString(2,newType);
            modifySynonymTextAndTypeById.setInt(3,id);
            modifySynonymTextAndTypeById.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that id in the database!");
    }

    public void modifySynonymTypeByWordIdAndType(int wordId, String oldType, String newType) throws  NoSynonymException{
        try {
            modifySynonymTypeByIdWordAndType.setString(1,newType);
            modifySynonymTypeByIdWordAndType.setInt(2, wordId);
            modifySynonymTypeByIdWordAndType.setString(3,oldType);
            modifySynonymTypeByIdWordAndType.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that references to that id_word and that type!");
    }

    public void modifySynonymTextByWordIdAndType(int wordId, String oldType, String newText) throws  NoSynonymException{
        try {
            modifySynonymTypeByIdWordAndType.setString(1,newText);
            modifySynonymTypeByIdWordAndType.setInt(2, wordId);
            modifySynonymTypeByIdWordAndType.setString(3,oldType);
            modifySynonymTypeByIdWordAndType.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that references to that id_word and that type!");
    }

    public void modifySynonymTextAndTypeByWordIdAndType(int wordId, String oldType, String newText, String newType) throws  NoSynonymException{
        try {
            modifySynonymTextAndTypeByIdWordAndType.setString(1,newText);
            modifySynonymTextAndTypeByIdWordAndType.setString(2,newType);
            modifySynonymTextAndTypeByIdWordAndType.setInt(3, wordId);
            modifySynonymTextAndTypeByIdWordAndType.setString(4,oldType);
            modifySynonymTextAndTypeByIdWordAndType.executeUpdate();
            return ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSynonymException("There is no synonym with that references to that id_word and that type!");
    }

    public static void main(String[] args) {
        //Synonyms synonyms = new Synonyms();
        /*
        //TEST -> INSERT
        synonyms.insertSynonym(new Synonym(1,"prepozitie","Litera mare"));
        synonyms.insertSynonym(new Synonym(2,"conjunctie","Litera mica"));
        synonyms.insertSynonym(new Synonym(1,"carne","macra"));
         */
        /*
        //TEST -> GET ID
        try {
            Synonym synonym = synonyms.getSynonymById(1);
            System.out.println(synonym.getIdSinonim() + " " + synonym.getIdCuvant() + " " + synonym.getTipSinonime() + " " + synonym.getTextSinonime());
        } catch (NoSynonymException e) {
            e.printStackTrace();
        }
         */
        /*
        // GET LIST -> ID WORD
        try {
            List<Synonym> lista = synonyms.getSynonymsByWordId(1);
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i).getIdSinonim() + " " + lista.get(i).getIdCuvant() + " " + lista.get(i).getTipSinonime() + " " + lista.get(i).getTextSinonime());
            }

        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
       */
        /*
        //TEST -> GET ID WORD AND TYPE
        try {
            Synonym synonym = synonyms.getSynonymByIdWordAndType(1,"prepozitie");
            System.out.println(synonym.getIdSinonim() + " " + synonym.getIdCuvant() + " " + synonym.getTipSinonime() + " " + synonym.getTextSinonime());
        } catch (NoSynonymException e) {
            e.printStackTrace();
        }
        */
        /*
        //TEST -> MODIFY TYPE BY ID
        try {
            synonyms.modifySynonymTypeById(1,"conjunctie");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
         */
        /*
        //TEST -> MODIFY TEXT BY ID
        try {
            synonyms.modifySynonymTextById(1,"Text nou");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
        */
        /*
        //TEST -> MODIFY TEXT AND TYPE BY ID
        try {
            synonyms.modifySynonymTextAndTypeById(1,"====","-----");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
        */
        /*
        //TEST -> MODIFY TYPE BY ID WORD AND TYPE
        try {
            synonyms.modifySynonymTypeByWordIdAndType(1,"-----","====");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
         */
        /*
        //TEST -> MODIFY TEXT BY ID WORD AND TYPE
        try {
            synonyms.modifySynonymTextByWordIdAndType(1,"====","++++");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
         */
        /*
        //TEST -> MODIFY TEXT AND TYPE BY ID WORD AND TYPE
        try {
            synonyms.modifySynonymTextAndTypeByWordIdAndType(1,"++++","????","****");
        }
        catch (NoSynonymException e) {
            e.printStackTrace();
        }
         */
    }
}