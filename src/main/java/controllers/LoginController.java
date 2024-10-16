package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.LoginClient;
import org.meteoevents.meteoevents.App;
import javafx.stage.Stage;
import utilities.PathsViews;
import java.io.IOException;

public class LoginController {

    @FXML
    private Label lbl_log_response;

    @FXML
    private TextField txt_log_nom;

    @FXML
    private PasswordField txt_log_password;

    private LoginClient login;

    @FXML
    protected void initialize() {
        login = new LoginClient();
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        try {
            String loginStatus = login.loginUsuari(txt_log_nom.getText(), txt_log_password.getText());

            if(loginStatus.equals("Login correcte")){
                //Es tanca la finestra de login
                Node source = (Node) event.getSource();
                Stage stageLogin = (Stage) source.getScene().getWindow();
                stageLogin.close();

                //S'obra la finestra que correspongui al tipus d'usuari
                if(login.getFuncionalID().equals("ADM")){
                    loadScreen(PathsViews.PRINCIPAL_VIEW);
                }else{
                    loadScreen(PathsViews.PRINCIPAL_STANDARD_VIEW);
                }
            }else{
                setMessage(loginStatus);
            }
        } catch (IOException | InterruptedException e) {
            setMessage("Servidor desconnectat: " + e);
        }
    }

    private void loadScreen(String path){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
            Parent root = fxmlLoader.load();

            if (path.equals(PathsViews.PRINCIPAL_VIEW)) {
                PrincipalController principalController = fxmlLoader.getController();
                principalController.setJwtToken(login.getJwtToken());
            } else if (path.equals(PathsViews.PRINCIPAL_STANDARD_VIEW)) {
                PrincipalStandardController principalStandardController = fxmlLoader.getController();
                principalStandardController.setJwtToken(login.getJwtToken());
            }

            Scene scene = new Scene(root, 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Meteo Events");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMessage(String message){
        lbl_log_response.setVisible(true);
        lbl_log_response.setText(message);
        txt_log_nom.setText("");
        txt_log_password.setText("");
    }
}