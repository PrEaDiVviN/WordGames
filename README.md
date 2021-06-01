<!DOCTYPE html>
<html>
<head>
</head>
<body>
  <h1>Tehnologii folosite</h1>
  <ol>
    <li>Spring Boot + REST API</li>
    <li>Swagger</li>
    <li>Jaunt</li>
    <li>Free Marker</li>
    <li>MySql Connector JDBK</li>
    <li>HikariCP connection Pool</li>
    <li>JavaFx + SceneBuiler</li>
    <li>JavaDoc</li>
  </ol>
  <h1>Contributii</h1>
  <p>Am lucrat impreuna in majoritatea timpului (intalniri pe DISCORD/ZOOM/FIZICE) si nu putem atribui un element unei singure persoane.</p>
  <h1>Elemente deosebite din punct de vedere al programarii</h1>
  <h2>Scrapping</h2>
  <ol>
    <li>Words scrapping de la <a href="https://ro.wiktionary.org/">referinta rowiktionary</a> folosit pentru a obtine toate cuvintele limbii romane. Pe acestea le-am stocat intr-o baza de date. <strong>Timp scrapping: 6ore.</strong></li>
    <li>Definition scrapping de la <a href="https://ro.wiktionary.org/">referinta rowiktionary</a> folosit pentru a obtine definitiile corespunzatoare fiecarui cuvant al limbii romane. Pe acestea le-am stocat intr-o baza de date. <strong>Timp scrapping: 30ore.</strong></li>
    <li>Synonym scrapping de la <a href="https://sin0nime.com/">referinta sin0nime</a> folosit pentru a obtine sinonimele corespunzatoare fiecarui cuvant al limbii romane. Pe acestea le-am stocat intr-o baza de date. <strong>Timp scrapping: 25ore.</strong></li>
    <li>Definition scrapping de la <a href="https://dexonline.ro/">referinta dexonline</a> folosit pentru a obtine definitiile corespunzatoare fiecarui cuvant al limbii romane. Pe acestea le-am stocat intr-o baza de date. Utilizeaza in spate PROXY-uri pentru mascarea identitatii(problema: aprox 700 request-uri produce un block de la dexonline pe o anumita perioada). Se folosesc REGEX-uri pentru parsarea documentelor html primite.</li>
  </ol>  
  <h2>Proxy verifier</h2>
  <ul>
      <li>Primeste o lista de proxy-uri cu anumite porturi si obtine acele proxy-uri ce sunt valabile pe un anumit link. Foloseste in spate JAUNT.</li>  
  </ul>
  <h2>Swagger</h2>
  <ul>
      <li>Am introdus in proiect swagger pentru a avea o reprezentare html a controller-lor si pentru a accesa rutele disponibile in proiect.</li>  
  </ul>
  <h2>Free marker</h2>
  <ul>
    <li>Am utilizat freemarker pentru a genera dinamic un fisier html care contine cuvinte{definitii, sinonime}. Fisierul este obtinut accesand endpoint-ul /view/query?{params - int: skip, count }. Numarul de cuvinte obtinut este count, iar pozitia in baza de date de start este skip. </li>
  </ul>
  <h2>Javafx</h2>
  <ol>
    <li>Jocul care foloseste serviciile implementate este spanzuratoarea.</li>
    <li>Pentru a realiza interfata grafica am utilizat javafx si un scene-builder pentru acesta.</li>
    <li>Jocul contine 3 scene: Login, Register, Jocul propriu zis. Fiecare scena are propriul sau controller.</li>
  </ol>
  <h2>Rest Template</h2>
  <ol>
    <li>Am integrat serviciile REST in joc utilizand REST TEMPLATE.</li>
    <li>Operatiile de obtinerea unui cuvant din baza de date, obtinerea sinonimului acestui cuvant utilizeaza serviciile REST API create de noi.</li>
  </ol>
  <h2>REST API</h2>
  <ol>
    <li><strong>UsersController</strong> - ofera servicii de: logare, inregistrare, obtinerea unui user pe baza unui id, obtinerea scorului pe baza username-ului, modificarea scorului pe username-ului.</li>
    <li><strong>WordsController</strong> - ofera servicii de: obtinerea definitiei unui cuvant pe baza numelui/id-ului acestuia (de pe DEXONLINE/RODIKTIONARY), obtinerea definitiilor cuvintelor dintr-un anumit interval atat de pe DEXONLINE/ROWIKTIONARY/AMBELE, verificarea existentei unui cuvant</li>
    <li><strong>SynonymController</strong> - ofera servicii de: obtinere a listei de sinonime pentru un anumit cuvant de un anumit tip, obtinerea a n-lea sinonim a unui cuvant (de anumit tip(substantiv, verb, prepozitie, etc)), obtinerea tuturor sinonimelor unui cuvant. </li>
  </ol>
  <h2>Daos</h2>
  <ol>
    <li> UsersDao - operatii asupra modelului user in baza de date</li>
    <li> WordsDao - operatii asupra modelului word in baza de date</li>
    <li> SynonymDao - operatii asupra modelului synonym in baza de date</li>
    <li> Clasa care gestioneaza conectarea la baza de date implementeaza design pettern Singleton astfel incat se realizeaza o singura conexiune.</li>
    <li> Alta clasa ce gestioneaza conexiunea la baza de date foloseste tehnologia HIKARICP pentru un connection pool. </li>
  </ol> 
  <h2>Exceptions</h2>
  <ol>
    <p>Au fost create exceptii custom pentru a ilustra probleme la nivelul serviciile API</p>
    <li>InvalidConfigurationException</li>
    <li>InvalidDataException</li>
    <li>InvalidRangeException</li>
    <li>NoAvaibleProxyException</li>
    <li>NoSynonymException</li>
    <li>NoWordException</li>
  </ol>
  <h2>Databases - Tables</h2>
  <ol>
    <li>Database: wordgames_database</li>
    <li>Tables: cuvinte 85158 entries, sinonime, users.</li>
    <li>Exportul bazei de date este in format sql pentru mysql.</li> 
  </ol>  
</body>
</html>
