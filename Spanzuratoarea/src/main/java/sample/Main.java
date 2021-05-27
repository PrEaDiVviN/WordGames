package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {
    private Parent rootNode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Spanzuratoarea");
        primaryStage.setScene(new Scene(rootNode, 500, 500));
        primaryStage.show();
    }

    public void init() throws Exception {
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\Spanzuratoarea\\src\\main\\java\\sample\\login.fxml").toURI().toURL());
        rootNode = loader.load();
    }
}