package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió dels treballadors
 * per als usuaris administradors. En aquesta classe es gestionen les interaccions amb els
 * botons per a esborrar, crear o modificar i visualitzar dels treballadors que treballen a l'empresa.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class UserManagementController {

    /** Contenidor de la vista. */
    @FXML
    private AnchorPane anch_user_management;

    /**
     * Etiqueta que mostra l'ID de l'usuari gestionat.
     */
    @FXML
    private Label lbl_user_management_id;

    /**
     * Camp de text per introduir o visualitzar l'adreça de l'usuari.
     */
    @FXML
    private TextField txt_user_management_address;

    /**
     * Camp de text per introduir o visualitzar la data de naixement de l'usuari.
     */
    @FXML
    private TextField txt_user_management_birth;

    /**
     * Camp de text per introduir o visualitzar la ciutat de residència de l'usuari.
     */
    @FXML
    private TextField txt_user_management_city;

    /**
     * Camp de text per introduir o visualitzar l'adreça de correu electrònic de l'usuari.
     */
    @FXML
    private TextField txt_user_management_email;

    /**
     * Camp de text per introduir o visualitzar el nom de l'usuari.
     */
    @FXML
    private TextField txt_user_management_name;

    /**
     * Camp de text per introduir o visualitzar la contrasenya de l'usuari.
     */
    @FXML
    private TextField txt_user_management_password;

    /**
     * Camp de text per introduir o visualitzar el número de telèfon de l'usuari.
     */
    @FXML
    private TextField txt_user_management_phone;

    /**
     * Camp de text per introduir o visualitzar el sexe de l'usuari.
     */
    @FXML
    private TextField txt_user_management_sex;

    /**
     * Camp de text per introduir o visualitzar el tipus d'usuari.
     */
    @FXML
    private TextField txt_user_management_type;

    /**
     * Camp de text per introduir o visualitzar el nom d'usuari o identificador d'accés.
     */
    @FXML
    private TextField txt_user_management_username;

    /**
     * Gestiona l'esdeveniment del botó per esborrar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    /**
     * Gestiona l'esdeveniment del botó per modificar o crear un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {

    }

    /**
     * Mètode getter per gestionar el contenidor de la vista.
     *
     * @return anch_user_management El contenidor de la vista.
     */
    public AnchorPane getAnch_user_management() {
        return anch_user_management;
    }

    /**
     * Mètode setter per gestionar el contenidor de la vista.
     *
     * @param anch_user_management El token JWT.
     */
    public void setAnch_user_management(AnchorPane anch_user_management) {
        this.anch_user_management = anch_user_management;
    }
}