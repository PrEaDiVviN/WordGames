package model;

public class WordDuoDefinition {
    private int id;
    private String word;
    private String definitionDEXONLINE;
    private String definitionROWIKTIONARY;

    public WordDuoDefinition(int id, String word, String definitionDEXONLINE, String definitionROWIKTIONARY) {
        this.id = id;
        this.word = word;
        this.definitionDEXONLINE = definitionDEXONLINE;
        this.definitionROWIKTIONARY = definitionROWIKTIONARY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinitionDEXONLINE() {
        return definitionDEXONLINE;
    }

    public void setDefinitionDEXONLINE(String definitionDEXONLINE) {
        this.definitionDEXONLINE = definitionDEXONLINE;
    }

    public String getDefinitionROWIKTIONARY() {
        return definitionROWIKTIONARY;
    }

    public void setDefinitionROWIKTIONARY(String definitionROWIKTIONARY) {
        this.definitionROWIKTIONARY = definitionROWIKTIONARY;
    }
}
