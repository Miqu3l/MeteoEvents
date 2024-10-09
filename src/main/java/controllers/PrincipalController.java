package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrincipalController {

    @FXML
    private AnchorPane anch_princ_main;

    @FXML
    private Button btn_princ_borrar_esd;

    @FXML
    private Button btn_princ_borrar_treb;

    @FXML
    private Button btn_princ_close;

    @FXML
    private Button btn_princ_llistar_esd;

    @FXML
    private Button btn_princ_llistar_treb;

    @FXML
    private Button btn_princ_modificar_esd;

    @FXML
    private Button btn_princ_modificar_treb;

    @FXML
    private Button btn_princ_nou_esd;

    @FXML
    private Button btn_princ_nou_treb;

    @FXML
    private Button btn_princ_veure_esd;

    @FXML
    private Button btn_princ_veure_treb;

    @FXML
    private TextField txt_princ_buscar;

    @FXML
    void onCloseButtonClick(ActionEvent event) {
        //Close the login window.
        Node source = (Node) event.getSource();
        Stage stageLogin = (Stage) source.getScene().getWindow();
        stageLogin.close();
    }
}