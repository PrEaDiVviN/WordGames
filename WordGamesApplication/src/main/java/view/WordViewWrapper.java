package view;

import model.Synonym;
import model.WordDuoDefinition;

import java.util.List;

public class WordViewWrapper {
    private WordDuoDefinition wordDuoDefinition;
    private List<Synonym> synonym;

    public WordViewWrapper(WordDuoDefinition wordDuoDefinition, List<Synonym> synonym) {
        this.wordDuoDefinition = wordDuoDefinition;
        this.synonym = synonym;
    }

    public WordDuoDefinition getWordDuoDefinition() {
        return wordDuoDefinition;
    }

    public void setWordDuoDefinition(WordDuoDefinition wordDuoDefinition) {
        this.wordDuoDefinition = wordDuoDefinition;
    }

    public List<Synonym> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<Synonym> synonym) {
        this.synonym = synonym;
    }
}
