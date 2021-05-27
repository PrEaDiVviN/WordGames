package scrapper;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import dao.Synonyms;
import dao.Words;
import exceptions.InvalidRangeException;
import exceptions.NoWordException;
import model.Synonym;
import model.Word;

public class SinonimeScrapper {
    public static void scrapSin0nime() {
        Words words = new Words();
        Synonyms synonyms = new Synonyms();
        UserAgent userAgent = new UserAgent();

        for(int wordNumberID = 1; wordNumberID <= 85158; wordNumberID++) {
            try{
                Word word = words.getWordById(wordNumberID);
                userAgent.visit("https://sin0nime.com/dex/index.php?cheie=" + word.getWord().replace(" ", "+") ); //pagina pe care o accesam
                Element body = userAgent.doc.findFirst("<body>"); //luam elementul body
                Element divElement = body.findFirst("<div class=\"cell colspan6\">"); //selectam span-ul ce contine cuvintele
                String text = divElement.getTextContent();//selectam lista
                if(! text.contains("Nu există rezultate pentru termenul sau termenii căutați.")) {
                    String firstSplit = text.replaceAll("\r\n", "");
                    String[] ArraySplit = firstSplit.split("&nbsp");
                    String secondSplit = ArraySplit[0];
                    String[] ArraySplit2 = secondSplit.split("\\.");

                    for (int i = 0; i < ArraySplit2.length; i++) {
                        String[] ArraySplit3 = ArraySplit2[i].split("Sinonime:");
                        String type = null;
                        String value = null;
                        for (int j = 0; j < ArraySplit3.length; j++)
                            if (j % 2 == 0) { //luam tipul sinonimului
                                String[] arraySplitComma = ArraySplit3[j].split(",");
                                if (arraySplitComma.length > 1) {
                                    while (arraySplitComma[1].charAt(0) == ' ')
                                        arraySplitComma[1] = arraySplitComma[1].substring(1, arraySplitComma[1].length());
                                    type = arraySplitComma[1];
                                }
                            } else {
                                while (ArraySplit3[j].charAt(0) == ' ')
                                    ArraySplit3[j] = ArraySplit3[j].substring(1, ArraySplit3[j].length());
                                value = ArraySplit3[j];
                                synonyms.insertSynonym(new Synonym(wordNumberID,type,value));
                            }
                    }
                }
                else
                    System.out.println("Nu s-au gasit rezultate pentru [" + wordNumberID + "]");
            }
            catch(JauntException | StringIndexOutOfBoundsException | NoWordException | InvalidRangeException e){
                System.out.println("Am intampinat eroare la [" + wordNumberID + "]");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done");
    }

    public static void main(String[] args) {
        scrapSin0nime();
    }
}
