package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.meteoevents.meteoevents.App;
import javafx.stage.Stage;
import utilities.Paths;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button btn_log_login;

    @FXML
    private Label lbl_log_inici;

    @FXML
    private Label lbl_log_response;

    @FXML
    private TextField txt_log_nom;

    @FXML
    private PasswordField txt_log_password;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        boolean connectat = true;
        boolean standard = false;

        if(connectat){
            //Close the login window.
            Node source = (Node) event.getSource();
            Stage stageLogin = (Stage) source.getScene().getWindow();
            stageLogin.close();

            if(!standard){
                loadScreen(Paths.PRINCIPAL_VIEW);
            }else{
                loadScreen(Paths.PRINCIPAL_STANDARD_VIEW);
            }
        }
    }

    private void loadScreen(String path){
        //Load the main screen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Meteo Events");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}