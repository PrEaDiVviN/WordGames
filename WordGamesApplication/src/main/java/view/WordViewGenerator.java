package view;

import dao.Synonyms;
import dao.Words;
import exceptions.InvalidRangeException;
import exceptions.NoSynonymException;
import exceptions.NoWordException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import model.Synonym;
import model.WordDuoDefinition;

import java.io.*;
import java.util.*;

public class WordViewGenerator {
    public static void generateView(List<WordViewWrapper> inputList, int entry) {
        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading( new File("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\WordGamesApplication\\src\\main\\java\\view"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.ENGLISH);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("entry", entry);
        input.put("word", inputList);

        Template template = null;
        Writer fileWriter = null;

        try {
            template = cfg.getTemplate("templateWord.ftl");
            fileWriter = new FileWriter("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\WordGamesApplication\\src\\main\\java\\view\\Representation.html");

            template.process(input, fileWriter);
        }
        catch (freemarker.template.TemplateException | IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                fileWriter.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<WordViewWrapper> initialize(int skip, int count) {
        Words wordDao = new Words();
        Synonyms synonyms = new Synonyms();
        List<WordViewWrapper> wordsWrapper = new ArrayList<>();
        List<WordDuoDefinition> listWordsDuoDefinition = null;
        try {
            listWordsDuoDefinition = wordDao.getWordRange(skip,count);
            for (int i = 0; i < listWordsDuoDefinition.size(); i++) {
                List<Synonym> synonymList = synonyms.getSynonymsByWordId(listWordsDuoDefinition.get(i).getId());
                wordsWrapper.add(new WordViewWrapper(listWordsDuoDefinition.get(i),synonymList));
            }

        } catch (InvalidRangeException | NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        return wordsWrapper;
    }
 /*
    public static void main(String[] args) {
        Words wordDao = new Words();
        Synonyms synonyms = new Synonyms();
        List<WordViewWrapper> wordsWrapper = new ArrayList<>();
        List<WordDuoDefinition> listWordsDuoDefinition = null;
        try {
            listWordsDuoDefinition = wordDao.getWordRange(5000,25);
            for (int i = 0; i < listWordsDuoDefinition.size(); i++) {
                List<Synonym> synonymList = synonyms.getSynonymsByWordId(listWordsDuoDefinition.get(i).getId());
                wordsWrapper.add(new WordViewWrapper(listWordsDuoDefinition.get(i),synonymList));
            }

        } catch (InvalidRangeException | NoWordException | NoSynonymException e) {
            e.printStackTrace();
        }
        generateView(wordsWrapper);
    }

  */
}
