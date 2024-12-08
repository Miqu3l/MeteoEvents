package controllers.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.login.LoginClient;
import org.meteoevents.meteoevents.App;
import javafx.stage.Stage;
import utilities.PathsViews;
import java.io.IOException;

/**
 * Controlador de la vista de login.
 * Gestiona autenticació de l'usuari amb la base de dades.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class LoginController {

    /** Missatge de resposta del servidor. */
    @FXML
    private Label lbl_log_response;

    /** Camp de text on s'introdueix el nom d'usuari. */
    @FXML
    private TextField txt_log_nom;

    /** Camp de text on s'introdueix la contrasenya de l'usuari. */
    @FXML
    private PasswordField txt_log_password;

    /** Instància de la classe LoginClient per gestionar la petició de login. */
    private LoginClient login;

    /**
     * Mètode que s'executa en crear el controlador i que inicialitza la instància de LoginClient.
     */
    @FXML
    protected void initialize() {
        login = new LoginClient();
    }

    /**
     * Gestiona l'esdeveniment del botó de login. Autentica l'usuari amb les credencials introduïdes.
     * Si el login és correcte, es tanca la finestra de login i s'obre la finestra corresponent
     * segons el tipus d'usuari.
     * Si el login falla, es mostra un missatge d'error.
     *
     * @param event L'esdeveniment que s'activa quan es fa clic al botó de login.
     */
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
        } catch (Exception e) {
            setMessage("Servidor desconnectat");
        }
    }

    /**
     * Carrega i envia el token JWT a la nova finestra.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadScreen(String path){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Meteo Events");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mostra un missatge a l'usuari al label de resposta i neteja els camps de text.
     *
     * @param message El missatge que es mostrarà a l'usuari.
     */
    private void setMessage(String message){
        lbl_log_response.setVisible(true);
        lbl_log_response.setText(message);
        txt_log_nom.setText("");
        txt_log_password.setText("");
    }
}