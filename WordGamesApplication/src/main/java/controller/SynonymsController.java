package controller;

import dao.Synonyms;
import dao.Words;
import exceptions.NoSynonymException;
import exceptions.NoWordException;
import io.swagger.models.auth.In;
import model.Synonym;
import model.Word;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("synonym")

public class SynonymsController {
    private Synonyms synonymsDao = new Synonyms();
    private Words wordsDao = new Words();

    @GetMapping("/query")
    public ResponseEntity<Synonym> getSynonymQuery(@RequestParam String cuvant, @RequestParam String tip){
        try {
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20"," "));
            Synonym synonym = synonymsDao.getSynonymByIdWordAndType(word.getId(), tip.replaceAll("%20", " "));
            return new ResponseEntity<>(synonym, HttpStatus.OK);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry/query")
    public ResponseEntity<String> getSynonymQueryEntry(@RequestParam String cuvant, @RequestParam String tip, @RequestParam int index , @RequestParam boolean recursiv){
        try {
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20"," "));
            Synonym synonym = synonymsDao.getSynonymByIdWordAndType(word.getId(), tip.replaceAll("%20", " "));
            String[] separatedCsv = synonym.getTextSinonime().split(", ");
            if(index >= 0 && index < separatedCsv.length)
                return new ResponseEntity<>(separatedCsv[index],HttpStatus.OK);
            if(recursiv)
                return new ResponseEntity<>(separatedCsv[index%separatedCsv.length],HttpStatus.OK);
            return new ResponseEntity<>("Bad Request! Pay attention to the fact that if you do not ret recursiv parameter ON, then, an index bigger then" +
                    " the number of entries will result in 403(BAD REQUEST)!",HttpStatus.BAD_REQUEST);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry/all/{cuvant}/{index}/{recursiv}")
    public ResponseEntity<String> getSynonymAllEntry(@PathVariable String cuvant, @PathVariable int index, @PathVariable boolean recursiv){
        try {
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20", " "));
            List<Synonym> synonymList = synonymsDao.getSynonymsByWordId(word.getId());
            List<String> synonyms = new ArrayList<>();
            for (int i = 0; i < synonymList.size(); i++) {
                String[] separatedCsv = synonymList.get(i).getTextSinonime().split(", ");
                synonyms.addAll(Arrays.asList(separatedCsv));
            }
            if(recursiv)
                index = index % synonyms.size();
            if(!(index < 0 || index >= synonyms.size()))
                return new ResponseEntity<>(synonyms.get(index),HttpStatus.OK);
            return new ResponseEntity<>("Bad Request! Pay attention to the fact that if you do not ret recursiv parameter ON, then, an index bigger then" +
                    " the number of entries will result in 403(BAD REQUEST)!",HttpStatus.BAD_REQUEST);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all/{cuvant}")
    public ResponseEntity<List<Synonym>> getSynonymQuery(@PathVariable String cuvant){
        try {
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20", " "));
            List<Synonym> synonymList = synonymsDao.getSynonymsByWordId(word.getId());
            return new ResponseEntity<>(synonymList, HttpStatus.OK);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/query/array")
    public ResponseEntity<List<String>> getSynonymQueryAsArray(@RequestParam String cuvant, @RequestParam String tip){
        try {
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20"," "));
            Synonym synonym = synonymsDao.getSynonymByIdWordAndType(word.getId(), tip.replaceAll("%20", " "));
            String[] separatedCsv = synonym.getTextSinonime().split(", ");
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(separatedCsv));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all/array")
    public ResponseEntity<Map<String,List<String>>> getSynonymQueryAsArray(@RequestParam String cuvant){
        try {
            Map<String, List<String >> result = new HashMap<>();
            Word word = wordsDao.getWordByName(cuvant.replaceAll("%20", " "));
            List<Synonym> synonymList = synonymsDao.getSynonymsByWordId(word.getId());
            for (int i = 0; i < synonymList.size(); i++) {
                String[] separatedCsv = synonymList.get(i).getTextSinonime().split(", ");
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(separatedCsv));
                result.put(synonymList.get(i).getTipSinonime().replaceAll("[ ]+[ ]+", ""), list);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getWordWithSynonym")
    public Integer getWordWithSynonym() {
        Random random = new Random();
        return synonymsDao.getWordWithSynonym(random.nextInt((65000 - 1) + 1) + 1);
    }

}