package services;

import rest.RestClient;
import model.Word;

import java.util.Random;

public class WordService {
    private static RestClient restClient = new RestClient();

    public Word getWordById() {
        Integer id = generateRandomWordId();
        return restClient.callGetWordById(id);
    }

    public Integer generateRandomWordId() {
        Random random = new Random();
        return random.nextInt((85000 - 1) + 1) + 1;
    }
}