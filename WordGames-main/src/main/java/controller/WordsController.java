package controller;

import dao.Words;
import model.Word;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
public class WordsController {
/*
    @GetMapping("/word/{word}")
    public Word getWord(@PathVariable String wordToGet) {
        Word word = new Word();
        word.setId(1);
        word.setWord("aaa");
        return word;
    }*/
    @GetMapping("/word/{wordToGet}")
    public Word sayHi(@PathVariable String wordToGet) {
        Words words = new Words();
        Word word = words.getWordByName(wordToGet);
        return word;
    }

}
