package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private void initialize(){
        lbl_log_response.setVisible(false);
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        boolean admin = false;

        try {

            LoginClient login = new LoginClient();
            String loginStatus = login.loginUsuari(txt_log_nom.getText(), txt_log_password.getText());

            if(loginStatus.equals("Login correcte")){
                Node source = (Node) event.getSource();
                Stage stageLogin = (Stage) source.getScene().getWindow();
                stageLogin.close();

                if(!admin){

                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.PRINCIPAL_VIEW));
                    Parent root = fxmlLoader.load();
                    PrincipalController principalController = fxmlLoader.getController();
                    principalController.setJwtToken(loginStatus);
                    Scene scene = new Scene(root, 900, 600);
                    Stage stage = new Stage();
                    stage.setTitle("Meteo Events");
                    stage.setScene(scene);
                    stage.show();

                }else{

                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.PRINCIPAL_STANDARD_VIEW));
                    Parent root = fxmlLoader.load();
                    PrincipalController principalController = fxmlLoader.getController();
                    principalController.setJwtToken(loginStatus);
                    Scene scene = new Scene(root, 900, 600);
                    Stage stage = new Stage();
                    stage.setTitle("Meteo Events");
                    stage.setScene(scene);
                    stage.show();
                }

            }else{
                lbl_log_response.setVisible(true);
                lbl_log_response.setText(loginStatus);
                txt_log_nom.setText("");
                txt_log_password.setText("");
            }

        } catch (IOException | InterruptedException e) {

            lbl_log_response.setVisible(true);
            lbl_log_response.setText("Servidor desconnectat");
            txt_log_nom.setText("");
            txt_log_password.setText("");
        }
    }
}