package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió dels esdeveniments
 * per als usuaris administradors. En aquesta classe es gestionen les interaccions amb els
 * botons per a esborrar, crear o modificar i visualitzar dels esdeveniments que gestiona l'empresa.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class EventManagementController {

    /** Contenidor de la vista. */
    @FXML
    private AnchorPane anch_event_management;

    /**
     * Etiqueta que mostra l'ID de l'esdeveniment gestionat.
     */
    @FXML
    private Label lbl_event_management_id;

    /**
     * Camp de text per introduir o visualitzar l'adreça associada a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_address;

    /**
     * Camp de text per introduir o visualitzar la data de naixement associada a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_birth;

    /**
     * Camp de text per introduir o visualitzar la ciutat associada a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_city;

    /**
     * Camp de text per introduir o visualitzar l'email associat a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_email;

    /**
     * Camp de text per introduir o visualitzar el nom associat a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_name;

    /**
     * Camp de text per introduir o visualitzar la contrasenya de l'usuari associat a l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_password;

    /**
     * Camp de text per introduir o visualitzar el telèfon associat a l'esdeveniment o usuari.
     */
    @FXML
    private TextField txt_event_management_phone;

    /**
     * Camp de text per introduir o visualitzar el nom d'usuari associat a l'esdeveniment o perfil.
     */
    @FXML
    private TextField txt_event_management_username;

    /**
     * Gestiona l'esdeveniment del botó per esborrar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    /**
     * Gestiona l'esdeveniment del botó per modificar o crear un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {

    }

    /**
     * Mètode getter per gestionar el contenidor de la vista.
     *
     * @return anch_event_management El contenidor de la vista.
     */
    public AnchorPane getAnch_event_management() {
        return anch_event_management;
    }

    /**
     * Mètode setter per gestionar el contenidor de la vista.
     *
     * @param anch_event_management El token JWT.
     */
    public void setAnch_event_management(AnchorPane anch_event_management) {
        this.anch_event_management = anch_event_management;
    }
}