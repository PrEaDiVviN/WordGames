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

import services.RegisterService;
import model.User;

import java.io.File;
import java.io.IOException;

public class FXMLRegisterController {
    private User user = new User();
    private RegisterService registerService = new RegisterService();

    @FXML
    private TextField userInput;
    @FXML
    private TextField passInput;
    @FXML
    private TextField passConfirmInput;

    @FXML
    private Label toLogInLabel;
    @FXML
    private Label statusRegisterFail;

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\PrEaD\\Desktop\\REST_spanzuratoarea\\Spanzuratoarea\\src\\main\\java\\sample\\login.fxml").toURI().toURL());
        Parent home_page_parent = loader.load();
        Scene home_page_scene = new Scene(home_page_parent,500,500);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void getRegisterMessage(ActionEvent event) throws IOException {
        fillUserInput();
        fillPassInput();
        String confirmPass = fillPassConfirmInput();
        if(registerService.register(user.getUserName(), user.getPassword(), confirmPass))
            toLogInLabel.setVisible(true);
        else
            statusRegisterFail.setVisible(true);
    }

    @FXML
    private void fillUserInput() {
        user.setUserName(userInput.getText());
    }
    @FXML
    private void fillPassInput() {
        user.setPassword(passInput.getText());
    }

    @FXML
    private String fillPassConfirmInput() {
        return passConfirmInput.getText();
    }
}
