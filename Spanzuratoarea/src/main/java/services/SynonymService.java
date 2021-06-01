package services;

import rest.RestClient;
import model.Word;

import java.util.Random;

public class SynonymService {
    private static RestClient restClient = new RestClient();

    public String getSynonym(String word, Integer index) {
        return restClient.callGetSynonymApi(word,index);
    }

    public Integer getWordWithSynonym() {
        return restClient.callGetWordWithSynonym();
    }
}