package scrapper;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import dao.Words;
import model.Word;

public class WordsScrapper {
    //scraping all the words from https://ro.wiktionary.org and inserting them into the database

    public static void ScrapRoWiktionary() {
        try{
            UserAgent userAgent = new UserAgent();
            userAgent.visit("https://ro.wiktionary.org/w/index.php?title=Categorie:Rom%C3%A2n%C4%83&from=A"); //pagina pe care o accesam
            Element body = userAgent.doc.findFirst("<body class=\"mediawiki ltr sitedir-ltr mw-hide-empty-elt ns-14 ns-subject mw-editable page-Categorie_Română rootpage-Categorie_Română skin-vector action-view skin-vector-legacy\">"); //luam elementul body
            Element divElement = body.findFirst("<div id=\"mw-pages\">"); //selectam div-ul ce contine cuvintele
            Element ulElement = divElement.findFirst("ul"); //selectam lista
            String text = ulElement.getTextContent(); //luam doar text-ul din li-uri
            String[] cuvinte = text.split("\n"); // separam acest text prin '\n'

            //Cream instanta Dao care o vom folosi pt adaugarea cuvintelor, si un singur cuvant ce va fi folosit pt adaugare pt a nu risipi memoria
            Words words = new Words();
            Word word = new Word();

            //Adaugam cuvintele la baza de date
            for (int i1 = 0; i1 < cuvinte.length; i1++) {
                word.setWord(cuvinte[i1]);
                word.setDefinition(" ");
                words.insertWord(word);
            }

            while(true) { //repetam procedeul atat timp cat mai exista o pagina urmatoare
                Element nextPageLink = userAgent.doc.findFirst("<a href>pagina următoare</a>");
                userAgent.visit(nextPageLink.getAtString("href").replaceAll("&amp;","&"));                        //visit a url
                body = userAgent.doc.findFirst("<body class=\"mediawiki ltr sitedir-ltr mw-hide-empty-elt ns-14 ns-subject mw-editable page-Categorie_Română rootpage-Categorie_Română skin-vector action-view skin-vector-legacy\">");
                divElement = body.findFirst("<div id=\"mw-pages\">");
                ulElement = divElement.findFirst("ul");
                text = ulElement.getTextContent();
                //Adaugam cuvintele la baza de date
                cuvinte = text.split("\n");
                for (int i1 = 0; i1 < cuvinte.length; i1++) {
                    word.setWord(cuvinte[i1]);
                    word.setDefinition(" ");
                    words.insertWord(word);
                }
            }
        }
        catch(JauntException e){
            System.out.println(e);
        }
        finally {
            System.out.println("We finished the scrapping!");
        }
    }

    public static void main(String[] args) {
        WordsScrapper.ScrapRoWiktionary();
    }
}
