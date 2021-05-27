package model;

public class Word {
    private int id;
    private String word;
    private String definition;

    public Word() {}

    public Word(int id, String word, String definition) { //only used for data management in application. NOT FOR INSERT
        this.id = id;
        this.word = word;
        this.definition = definition;
    }

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}