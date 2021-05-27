package scrapper;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import dao.Words;
import exceptions.InvalidRangeException;
import exceptions.NoWordException;
import model.Word;


public class DefinitionScrapper {

    public static void ScrapDexOnlineDefinition() {
        String whitespace_chars = "["
                + "\\u0009" // CHARACTER TABULATION
                + "\\u000A" // LINE FEED (LF)
                + "\\u000B" // LINE TABULATION
                + "\\u000C" // FORM FEED (FF)
                + "\\u000D" // CARRIAGE RETURN (CR)
                + "\\u0020" // SPACE
                + "\\u0085" // NEXT LINE (NEL)
                + "\\u00A0" // NO-BREAK SPACE
                + "\\u1680" // OGHAM SPACE MARK
                + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
                + "\\u2000" // EN QUAD
                + "\\u2001" // EM QUAD
                + "\\u2002" // EN SPACE
                + "\\u2003" // EM SPACE
                + "\\u2004" // THREE-PER-EM SPACE
                + "\\u2005" // FOUR-PER-EM SPACE
                + "\\u2006" // SIX-PER-EM SPACE
                + "\\u2007" // FIGURE SPACE
                + "\\u2008" // PUNCTUATION SPACE
                + "\\u2009" // THIN SPACE
                + "\\u200A" // HAIR SPACE
                + "\\u2028" // LINE SEPARATOR
                + "\\u2029" // PARAGRAPH SEPARATOR
                + "\\u202F" // NARROW NO-BREAK SPACE
                + "\\u205F" // MEDIUM MATHEMATICAL SPACE
                + "\\u3000" // IDEOGRAPHIC SPACE
                + "]"
                ;

        /*
        34.122.40.247	3128
        103.213.213.14	84
        184.82.128.211	8080
         */
        String[] ipArray = new String [] { "13.72.71.159", "124.106.224.5", "178.205.169.210", "183.88.226.50", "165.227.173.87", "23.251.138.105", "95.217.34.209", "188.240.71.213", "51.178.220.22", "34.122.40.247"};
        Integer[] portArray = new Integer[] { 80, 8080, 3128, 8080, 40107, 8080, 3128, 3128, 80, 3128};
        int proxyCount = 1;

        Words words = new Words();
        UserAgent userAgent = new UserAgent();
        userAgent.settings.responseTimeout = 7000; //setam numarul maxim de milisecunde pe care trebuie sa il astepte agentul pana renunta la pagina

        for(int wordNumberID = 4900; wordNumberID <= 85158; wordNumberID++) {
            if(wordNumberID%700 == 0) {
                userAgent.setProxyHost(ipArray[proxyCount]);
                userAgent.setProxyPort(portArray[proxyCount]);
                System.out.println("We entered proxy [" + ipArray[proxyCount] + "] [" + portArray[proxyCount] +"]");
                proxyCount++;
                proxyCount = proxyCount % 10;
            }

            try{
                Word word = words.getWordById(wordNumberID);

                userAgent.visit("https://dexonline.ro/definitie/" + word.getWord()); //pagina pe care o accesam
                Element body = userAgent.doc.findFirst("<body class=\"search\">"); //luam elementul body
                Element spanElement = body.findFirst("<span class=\"def\">"); //selectam span-ul ce contine cuvintele
                String text = spanElement.innerHTML();//selectam lista

                while (text.charAt(0) == ' ' || text.charAt(0) == ',') {
                    text = text.substring(1, text.length());
                }
                text = text.replaceAll("<sup>[0-9]+</sup>", "");
                text = text.replaceAll("\\([0-9]+\\)", "");
                text = text.replaceAll("\\[[\\(\\p{ASCII} :ĂẤșăĂâÂ’ÎîȘȚțÁÉÍÓÖŐÚÜŰáéôïíóvãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–ö„”őúüű\"\\)]*\\]", "");
                text = text.replaceAll("<abbr[\\p{ASCII} șăĂĂẤâÂÎîȘȚțÁÉÍÓÖŐÚÜŰáïéțïôíțóövãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–„”őúüű&&[^<]]*</abbr>", "");
                text = text.replaceAll("<span[\\p{ASCII} șăĂĂẤâÂÎîȘȚțĂẤÁÉÍÓÖŐÚÜŰáïțôïéíț„”óvãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–öőúüű&&[^<]]*</span>", "");
                text = text.replaceAll("<b>[`~1!2@3#4\\$5%6\\^7&8\\*9\\(\\)_=q wertĂẤyuôiopațï„”asdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,\\.:;\\\"'\\?/\\{\\[\\}\\]\\|\\\\șăĂâÂÎîȘȚțÁÉÍÓÖŐÚÜŰáéíóöőúüű–\\-]*</b>", "");
                text = text.replaceAll("<i>[`~1!2@3#4\\$5%6\\^7&8\\*9\\(\\)_=q wertyuiôopaïsdfgãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–hțj„”klzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,\\.:;\\\"'\\?/\\{\\[\\}\\]\\|\\\\șăĂâÂÎîȘȚțÁÉÍÓÖŐÚÜŰáéíóöőúüű–\\-]*</i>", "");
                text = text.replaceAll("[◊♦’]", "");
                text = text.replaceAll("<i>", "");
                text = text.replaceAll("</i>", "");
                text = text.replaceAll("<b>","");
                text = text.replaceAll("</b>", "");
                text = text.replaceAll("\\(\\)", "");
                text = text.replaceAll("\\(;", "(");
                text = text.replaceAll(" [\\+\\-–] ", " ");

                String text1 = "";
                while (text.toString().compareTo(text1.toString()) != 0) {
                    text1 = new String(text);
                    text = text.replaceAll(whitespace_chars + whitespace_chars, " ");
                }
                if (text.length() > 3 && (text.charAt(0) == ')' || text.charAt(0) == '(' || text.charAt(0) == ',') && text.charAt(1) == ' ')
                    text = text.substring(2, text.length() - 1);

                text = text.replaceAll("[ \\-\\+\\(]*$", "");
                text = text.replaceAll("\\. –[ ]*[a-zA-ZșăĂâÂÎîȘȚțÁÉÍÓÖĂẤŐÚÜŰáé„”ãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–ïôíóöőúüű]*$", ".");
                text = text.replaceAll("^[0-9]+ ", "");
                text = text.replaceAll("^[A-Z]\\.", "");
                text = text.replaceAll("^ și |^și", "");
                text = text.replaceAll("[\\+\\-= –]*[a-zA-ZșăĂâÂÎîȘȚțÁÉÍÓĂẤÖŐÚÜŰáé„”ãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–ôíóöőúüű]*$", "");
                text = text.replaceAll("\\( ", "(");
                text = text.replaceAll(", ; ,", "");
                text = text.replaceAll(" [A-Z]\\. ", "");
                text = text.replaceAll(" = ", " ");
                text = text.replaceAll("[IV]+[\\.]*","");
                text = text.replaceAll("\\(și ; [ a-zA-ZșăĂâÂÎîȘȚțÁÉÍÓÖŐÚĂẤÜŰáéíóöőôãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–„”úüű]*\\)","");
                text = text.replaceAll("\\[[\\(\\p{ASCII} :șăĂâÂ’ÎîȘȚôțÁÉÍĂẤÓÖŐÚÜŰá„ãèἄϰαμλοςãŋglədéșbæntiŋexλæəã□–”éïíóöőúüű\"\\)]*\\]", "");
                text = text.replaceAll("\\(\\)","");
                text1 = "";
                while (text.length() > 1 && text.toString().compareTo(text1.toString()) != 0) {
                    text1 = new String(text);
                    text = text.replaceAll(whitespace_chars + whitespace_chars, " ");
                }
                if (text.length() > 2 && (text.charAt(0) == ')' || text.charAt(0) == '(' || text.charAt(0) == ',') && text.charAt(1) == ' ')
                    text = text.substring(2, text.length() - 1);

                while (text.length() > 1 && text.charAt(0) == ' ' || text.charAt(0) == ',') {
                    text = text.substring(1, text.length());
                }
                if(text.length() > 4 && text.charAt(0) == 'ș' && text.charAt(1) == 'i' &&  text.charAt(2) == ' ')
                    text = text.substring(3,text.length());
                text = text.replaceAll("\\. , \\(", ". (");
                text = text.replaceAll("\\.;",";");
                text = text.replaceAll(" \\)", ")");
                text = text.replaceAll("Din$","");
                text = text.replaceAll("Din [\\.]*$","");
                if(text.length() > 2 && text.charAt(text.length()-2) == ',')
                    text = text.substring(0,text.length()-2) + ".";
                text = text.replaceAll("s. v.","");



                //System.out.println(text);

                words.modifyDefinitionByName(word.getWord(), text);
            }
            catch(JauntException | StringIndexOutOfBoundsException | InvalidRangeException | NoWordException e){
                System.out.println("Am intampinat eroare la [" + wordNumberID + "]");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done");
    }

    public static void ScrapRoWiktionaryDefinition() {
        Words words = new Words();
        UserAgent userAgent = new UserAgent();

        for(int wordNumberID = 1  ; wordNumberID <= 85158; wordNumberID++) {
            try{
                Word word = words.getWordById(wordNumberID);

                userAgent.visit("https://ro.wiktionary.org/wiki/" + word.getWord().replace(" ", "_")); //pagina pe care o accesam
                Element body = userAgent.doc.findFirst("<body>"); //luam elementul body
                Element olElement = body.findFirst("<ol>"); //selectam span-ul ce contine cuvintele
                Element liElement = olElement.getFirst("<li>");
                String text = liElement.getTextContent();//selectam lista

                words.modifyDefinitionROWIKTIONARYByName(word.getWord(), text);
            }
            catch(JauntException | StringIndexOutOfBoundsException | InvalidRangeException | NoWordException e){
                System.out.println("Am intampinat eroare la [" + wordNumberID + "]");
            }
        }
        System.out.println("Done");
    }

    public static void main(String[] args) {
        ScrapDexOnlineDefinition();
    }
}
