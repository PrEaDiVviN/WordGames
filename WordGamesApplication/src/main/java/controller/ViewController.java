package controller;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import view.WordViewGenerator;
import view.WordViewWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/query")
    public String getWordById(@RequestParam int skip, @RequestParam int count) {
        List<WordViewWrapper> listWords =  WordViewGenerator.initialize(skip, count);
        WordViewGenerator.generateView(listWords, skip / 10);
        try {
            String content = Files.asCharSource(new File("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\WordGamesApplication\\src\\main\\java\\view\\Representation.html"), Charsets.UTF_8).read();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
