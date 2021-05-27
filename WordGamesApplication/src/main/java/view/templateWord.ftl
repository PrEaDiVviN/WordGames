<html>
    <head>
        <title> Words html representation </title>
        <style>
            button {
              text-decoration: none;
              display: inline-block;
              padding: 8px 16px;
            }

            button:hover {
              background-color: #ddd;
              color: black;
            }

            .previous {
              background-color: #f1f1f1;
              color: black;
            }

            .next {
              background-color: #04AA6D;
              color: white;
            }

            .round {
              border-radius: 50%;
            }
            .center-buttons {
                 width: 100%;
                 display: flex;
                 justify-content: center;
                 align-items: center;
            }

            h1 {
              font: 400 55px/1.5 Helvetica, Verdana, sans-serif;
              margin: 0;
              padding: 0;
            }

            h3 {
              font: 400 45px/1.5 Helvetica, Verdana, sans-serif;
              margin: 0;
              padding: 0;
            }

            ul {
              display: flex;
              justify-content: center;
              align-items: center;
              flex-direction: column;
              list-style-type: none;
              margin: 0;
              padding: 0;
            }

            li {
              font: 200 20px/1.5 Helvetica, Verdana, sans-serif;
              border-bottom: 1px solid #ccc;
            }

            li:last-child {
              border: none;
            }

            li {
              text-decoration: none;
              color: #000;
              display: block;
              width: 100%;
              -webkit-transition: font-size 0.3s ease, background-color 0.3s ease;
              -moz-transition: font-size 0.3s ease, background-color 0.3s ease;
              -o-transition: font-size 0.3s ease, background-color 0.3s ease;
              -ms-transition: font-size 0.3s ease, background-color 0.3s ease;
              transition: font-size 0.3s ease, background-color 0.3s ease;
            }

            li:hover {
              font-size: 23px;
              background: #f6f6f6;
            }
        </style>
    </head>
    <body>
        <h1>Html representation for words (Entry #${entry}) </h1>
        <ul>
            <#list word as words>
                <h3 style="color: purple;">${words.getWordDuoDefinition().getWord()}</h3>
                <li> Definitie dexonline: ${words.getWordDuoDefinition().getDefinitionDEXONLINE()} </li>
                <li> Definitie rowiktionary: ${words.getWordDuoDefinition().getDefinitionROWIKTIONARY()} </li>
                <li> Informatie sinonime:
                    <ul>
                        <#list words.getSynonym() as synonyms>
                            <li>Tipul sinonumului: ${synonyms.getTipSinonime()}</li>
                            <li>Textul sinonimului: ${synonyms.getTextSinonime()}</li>
                        </#list>
                    </ul>
                </li>
            </#list>
        </ul>
        <div class="center-buttons">
            <button class="previous" onclick="prev()">&laquo; Previous Page </button>
            <button class="next" onclick="next()">Next Page &raquo;</button>
        </div>
        <script>
            function next() {
                window.location.replace("http://localhost:8081/view/query?" + "skip=" + (${entry}*10 + 10) + "&count=10");
            }
            function prev() {
                window.location.replace("http://localhost:8081/view/query?" + "skip=" + (${entry}*10 - 10) + "&count=10");
            }
        </script>
    </body>
</html>