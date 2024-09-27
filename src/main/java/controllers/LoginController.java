package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private TextField txt_log_password;

    @FXML
    protected void onLoginButtonClick() {
        lbl_log_response.setText("Conectado");
    }
}