package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió de les mesures de prevenció
 * per als usuaris administradors. En aquesta classe es gestionen les interaccions amb els
 * botons per a esborrar, crear o modificar i visualitzar de les mesures de prevenció que gestiona l'empresa.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class MeasureManagementController {

    /** Contenidor de la vista. */
    @FXML
    private AnchorPane anch_measure_management;

    /**
     * Etiqueta que mostra l'ID de la mesura gestionada.
     */
    @FXML
    private Label lbl_measure_management_id;

    /**
     * Camp de text per introduir o visualitzar l'adreça associada a la mesura.
     */
    @FXML
    private TextField txt_measure_management_address;

    /**
     * Camp de text per introduir o visualitzar l'adreça de correu electrònic associada a la mesura.
     */
    @FXML
    private TextField txt_measure_management_email;

    /**
     * Camp de text per introduir o visualitzar el nom associat a la mesura.
     */
    @FXML
    private TextField txt_measure_management_name;

    /**
     * Camp de text per introduir o visualitzar la contrasenya de l'usuari associat a la mesura.
     */
    @FXML
    private TextField txt_measure_management_password;

    /**
     * Camp de text per introduir o visualitzar el número de telèfon associat a la mesura.
     */
    @FXML
    private TextField txt_measure_management_phone;

    /**
     * Camp de text per introduir o visualitzar el nom d'usuari associat a la mesura.
     */
    @FXML
    private TextField txt_measure_management_username;

    /**
     * Gestiona l'esdeveniment del botó per esborrar una mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    /**
     * Gestiona l'esdeveniment del botó per modificar o crear una mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {

    }

    /**
     * Mètode getter per gestionar el contenidor de la vista.
     *
     * @return anch_measure_management El contenidor de la vista.
     */
    public AnchorPane getAnch_measure_management() {
        return anch_measure_management;
    }

    /**
     * Mètode setter per gestionar el contenidor de la vista.
     *
     * @param anch_measure_management El token JWT.
     */
    public void setAnch_measure_management(AnchorPane anch_measure_management) {
        this.anch_measure_management = anch_measure_management;
    }
}