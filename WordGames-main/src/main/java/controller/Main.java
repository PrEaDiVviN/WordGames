package controller;

import dao.Words;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
       Words words = new Words();
//        System.out.println(words.getWordTarget(4,5));
//       words.modifyDefinitionByName("pix","Pix");
//        words.modifyNameAndDefinition("pix","pixx","pix");
//
//        Word word = words.getWordByName("pix");
//        System.out.println(word);
//        System.setProperty("server.port", "8081");
//        SpringApplication.run(Main.class, args);
    }
}
