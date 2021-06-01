package controller;

import dao.Words;
import exceptions.InvalidDataException;
import exceptions.InvalidRangeException;
import exceptions.NoWordException;
import model.Word;
import model.WordDuoDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("word")
public class WordsController {
    private Words wordsDao = new Words();

    @GetMapping("/{wordToGet}")
    public Word getWordByName(@PathVariable String wordToGet) {
        try {
            return wordsDao.getWordByName(wordToGet);
        } catch (NoWordException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getById/{id}")
    public Word getWordById(@PathVariable Integer id) {
        try {
            return wordsDao.getWordById(id);
        } catch (NoWordException | InvalidRangeException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/dexonline/{wordToGet}")
    public ResponseEntity<Word> getWordByNameDexOnline(@PathVariable String wordToGet) {
        try {
            return new ResponseEntity<>(wordsDao.getWordByNameDexOnline(wordToGet), HttpStatus.OK);
        } catch (NoWordException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rowiktionary/{wordToGet}")
    public ResponseEntity<Word> getWordByNameRoWiktionary(@PathVariable String wordToGet) {
        try {
            return new ResponseEntity<>(wordsDao.getWordByNameRoWiktionary(wordToGet), HttpStatus.NOT_FOUND);
        } catch (NoWordException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/dexonline/getById/{id}")
    public ResponseEntity<Word> getWordByIdDexOnline(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(wordsDao.getWordByIdDexOnline(id), HttpStatus.OK);
        } catch (NoWordException | InvalidRangeException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/rowiktionary/getById/{id}")
    public ResponseEntity<Word> getWordByIdRoWiktionary(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(wordsDao.getWordByIdRoWiktionary(id), HttpStatus.OK);
        } catch (NoWordException | InvalidRangeException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getRange/dexonline")
    public ResponseEntity<List<Word>> getWordsRangeDexOnline (@RequestParam int skip,@RequestParam int count) {
        try {
            return new ResponseEntity<>(wordsDao.getWordTargetDexOnline(skip,count), HttpStatus.OK);
        } catch (InvalidRangeException | NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getRange/rowiktionary")
    public ResponseEntity<List<Word>> getWordsRangeRoWiktionary (@RequestParam int skip,@RequestParam int count) {
        try {
            return new ResponseEntity<>(wordsDao.getWordTargetRoWiktionary(skip,count), HttpStatus.OK);
        } catch (InvalidRangeException | NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getRange")
    public ResponseEntity<List<WordDuoDefinition>> getWordsRange (@RequestParam int skip,@RequestParam int count) {
        try {
            return new ResponseEntity<>(wordsDao.getWordRange(skip,count), HttpStatus.OK);
        } catch (InvalidRangeException | NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/exists")
    public ResponseEntity<String> existsWord(@RequestParam String cuvant) {
        try {
            return new ResponseEntity<>(wordsDao.existsWord(cuvant) ? "true" : "false" ,HttpStatus.OK);
        } catch (InvalidDataException | NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Bad request! Please see the documentation before proceeding to do more calls!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/startsWith")
    public ResponseEntity<List<Word>> getWordsStartWith (@RequestParam String prefix) {
        try {
            return new ResponseEntity<>(wordsDao.getWordStartWith(prefix), HttpStatus.OK);
        } catch (NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/endsWith")
    public ResponseEntity<List<Word>> getWordsEndsWith (@RequestParam String suffix) {
        try {
            return new ResponseEntity<>(wordsDao.getWordEndWith(suffix), HttpStatus.OK);
        } catch (NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/contains")
    public ResponseEntity<List<Word>> getWordsContains (@RequestParam String text) {
        try {
            return new ResponseEntity<>(wordsDao.getWordContains(text), HttpStatus.OK);
        } catch (NoWordException  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}