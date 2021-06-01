package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import services.LoginService;
import model.User;

import java.io.File;
import java.io.IOException;

import static javafx.print.PrintColor.COLOR;

public class FXMLLoginController {
    private static User user = new User();
    private LoginService loginService = new LoginService();

    @FXML
    private TextField userInput;
    @FXML
    private TextField passInput;

    @FXML
    private Label statusLoginFail;

    @FXML
    private void goToRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\Spanzuratoarea\\src\\main\\java\\sample\\register.fxml").toURI().toURL());
        Parent home_page_parent =loader.load();
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        fillUserInput();
        fillPassInput();
        if(loginService.login(user.getUserName(), user.getPassword())) {
            FXMLGameController fxmlGameController = new FXMLGameController();
            Scene home_page_scene = fxmlGameController.start();
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
            fxmlGameController.startGame();
        }
        else
            statusLoginFail.setVisible(true);
    }

    @FXML
    private void fillUserInput() {
        user.setUserName(userInput.getText());
    }
    @FXML
    private void fillPassInput() {
        user.setPassword(passInput.getText());
    }

    public static User getUser() {
        return user;
    }
}