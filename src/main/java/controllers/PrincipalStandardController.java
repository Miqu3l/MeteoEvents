package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.meteoevents.meteoevents.App;
import utilities.PathsViews;

import java.io.IOException;

public class PrincipalStandardController {

    @FXML
    private AnchorPane anch_princ_main;

    @FXML
    private Button btn_princ_close;

    @FXML
    private Button btn_princ_llistar_esd;

    @FXML
    private Button btn_princ_veure_esd;

    @FXML
    private TextField txt_princ_buscar;

    String jwtToken;

    public void setJwtToken(String token){
        jwtToken = token;
    }

    @FXML
    void onCloseButtonClick(ActionEvent event) {
        //Tanca la finestra actual.
        Node source = (Node) event.getSource();
        Stage stageLogin = (Stage) source.getScene().getWindow();
        stageLogin.close();

        //Esborra el token
        setJwtToken("");

        //S'obre la finestra de login
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.LOGIN_VIEW));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Meteo Events");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onLlistarEsdevenimentButtonClick(ActionEvent event) {
        System.out.println(jwtToken);
    }

    @FXML
    void onVeureEsdevenimentButtonClick(ActionEvent event) {
        System.out.println(jwtToken);
    }
}
