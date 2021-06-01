package sample;

import java.io.IOException;
import java.util.HashMap;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Word;
import services.SynonymService;
import services.UserService;
import services.WordService;


public class FXMLGameController {
    private static final Font DEFAULT_FONT = new Font("Courier", 36);
    private SimpleStringProperty word = new SimpleStringProperty();
    private SimpleIntegerProperty lettersToGuess = new SimpleIntegerProperty();
    private SimpleBooleanProperty playable = new SimpleBooleanProperty();
    private ObservableList<Node> letters;
    private HashMap<Character, Text> alphabet = new HashMap<Character, Text>();
    private HangmanRepresentation hangman = new HangmanRepresentation();
    private WordService wordService = new WordService();
    private UserService userService = new UserService();
    private SynonymService synonymService = new SynonymService();
    private Word wordToGuess;
    private Label hintLabel = new Label();
    private Label userScore = new Label();

    public Scene start() throws IOException {
        Parent parent = setGameElements();
        Scene scene = new Scene(parent,1000,600);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;

            char letterChosen = event.getText().toUpperCase().charAt(0);

            if ((letterChosen < 'A' || letterChosen > 'Z') && letterChosen != '-' && letterChosen != 'Ă'
                    && letterChosen != 'Î' && letterChosen != 'Â' && letterChosen != 'Ț' && letterChosen != 'Ș')
                return;

            if (playable.get()) {
                Text t = alphabet.get(letterChosen);
                if (t.isStrikethrough())
                    return;

                t.setFill(Color.RED);
                t.setStrikethrough(true);

                boolean found = false;

                for (Node n : letters) {
                    Letter letter = (Letter) n;
                    if (letter.isEqualTo(letterChosen)) {
                        found = true;
                        lettersToGuess.set(lettersToGuess.get() - 1);
                        letter.show();
                    }
                }

                if (!found) {
                    hangman.takeAwayLife();
                }
            }
        });

        return scene;
    }

    public Parent setGameElements() {
        HBox rowLetters = new HBox();
        rowLetters.setAlignment(Pos.CENTER);
        rowLetters.setPrefHeight(50.0);
        rowLetters.setLayoutX(162.0);
        rowLetters.setLayoutY(300.0);
        letters = rowLetters.getChildren();

        playable.bind(hangman.lives.greaterThan(0).and(lettersToGuess.greaterThan(0)));
        playable.addListener((obs, old, newValue) -> {
            if (!newValue.booleanValue()) {
                stopGame();
                hintLabel.setVisible(false);
                if(hangman.lives.getValue().intValue() > 0) {
                    userService.updateScore(FXMLLoginController.getUser().getUserName());
                    userScore.setText("Score: " + userService.getScore(FXMLLoginController.getUser().getUserName()).toString());
                }
            }
        });

        HBox rowAlphabet = setAlphabet();
        HBox rowHangman = setHangman();

        userScore.setText("SCORE: " + userService.getScore(FXMLLoginController.getUser().getUserName()).toString());
        userScore.setFont(new Font("SansSerif",18));
        userScore.setStyle("-fx-font-weight: bold");
        HBox score = new HBox(userScore);
        score.setAlignment(Pos.BOTTOM_RIGHT);

        hintLabel.setVisible(false);
        hintLabel.setFont(new Font("SansSerif",16));
        hintLabel.setStyle("-fx-font-weight: bold");
        Button hintBtn = new Button("HINT");
        hintBtn.setFont(new Font("SansSerif",12));
        hintBtn.setStyle("-fx-background-color: #FFCF62");
        hintBtn.setPrefWidth(200);
        hintBtn.setPrefHeight(40);
        hintBtn.setOnAction(event -> {
            String synonym = synonymService.getSynonym(wordToGuess.getWord(),0);
            hintLabel.setText(synonym);
            hintLabel.setVisible(true);
        });

        HBox hint = new HBox(30, hintBtn, hintLabel);
        hint.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(rowLetters, rowAlphabet, rowHangman, hint, score);
        vBox.setStyle("-fx-background-color: #F9FFB6");

        return vBox;
    }

    private void stopGame() {
        for (Node n : letters) {
            Letter letter = (Letter) n;
            letter.show();
        }
    }

    public void startGame() {
        for (Text t : alphabet.values()) {
            t.setStrikethrough(false);
            t.setFill(Color.BLACK);
        }

        wordToGuess = wordService.getWordById();

        hangman.reset();
        word.set(wordToGuess.getWord().toUpperCase());
        lettersToGuess.set(word.length().get());

        letters.clear();
        for (char c : word.get().toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    public HBox addLetter(HBox rowAlphabet, char letter) {
        Text t = new Text(String.valueOf(letter));
        t.setFont(DEFAULT_FONT);
        alphabet.put(letter, t);
        rowAlphabet.getChildren().add(t);

        return rowAlphabet;
    }

    public HBox setAlphabet() {
        HBox rowAlphabet = new HBox(5);
        rowAlphabet.setAlignment(Pos.CENTER);

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            rowAlphabet = addLetter(rowAlphabet, letter);

            if(letter == 'A') {
                rowAlphabet = addLetter(rowAlphabet, 'Ă');
                rowAlphabet = addLetter(rowAlphabet, 'Â');
            }

            if(letter == 'I') {
                rowAlphabet = addLetter(rowAlphabet, 'Î');
            }

            if(letter == 'T') {
                rowAlphabet = addLetter(rowAlphabet, 'Ț');
            }

            if(letter == 'S') {
                rowAlphabet = addLetter(rowAlphabet, 'Ș');
            }
        }
        return rowAlphabet;
    }

    public HBox setHangman() {
        Button tryAgainBtn = new Button("Try again!");
        tryAgainBtn.disableProperty().bind(playable);
        tryAgainBtn.setOnAction(event -> startGame());
        tryAgainBtn.setAlignment(Pos.CENTER_LEFT);
        tryAgainBtn.setFont(new Font("SansSerif",16));
        tryAgainBtn.setStyle("-fx-font-weight: bold");

        HBox rowHangman = new HBox(10, tryAgainBtn, hangman);
        rowHangman.setAlignment(Pos.CENTER);
        rowHangman.setPrefHeight(300);

        return rowHangman;
    }
}