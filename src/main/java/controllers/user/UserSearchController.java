package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la funcionalitat de cerca d'usuaris.
 * Gestiona la selecció del criteri de cerca (ID, nom o nom d'usuari)
 * i executa la cerca segons l'atribut i valor introduïts.
 */
public class UserSearchController {

    /** Cadena de text que representa el valor de cerca introduït per l'usuari. */
    private String search;

    /** Cadena de text que indica l'atribut seleccionat per a la cerca (ID, nom o nom d'usuari). */
    private String attribute;

    /** Panell principal de l'àrea de cerca d'usuaris. */
    @FXML
    private AnchorPane anch_user_search;

    /** Etiqueta que mostra el criteri de cerca seleccionat per l'usuari. */
    @FXML
    private Label lbl_user_search;

    /** Etiqueta per mostrar missatges d'error o d'avís si no es troba cap resultat. */
    @FXML
    private Label lbl_response_empty;

    /** Camp de text on l'usuari introdueix el valor que vol buscar. */
    @FXML
    private TextField txt_user_search;

    /** Panell titulat que conté les opcions de selecció de l'atribut de cerca. */
    @FXML
    private TitledPane tit_attribute_search;

    /**
     * Mètode invocat quan es fa clic al botó de cerca per ID.
     * Estableix l'atribut de cerca com a "ID".
     *
     * @param event l'esdeveniment de clic associat al botó ID.
     */
    @FXML
    void onIDButtonClick(ActionEvent event) {
        lbl_user_search.setText("ID");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom.
     * Estableix l'atribut de cerca com a "Nom".
     *
     * @param event l'esdeveniment de clic associat al botó Nom.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        lbl_user_search.setText("Nom");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom d'usuari.
     * Estableix l'atribut de cerca com a "Nom usuari".
     *
     * @param event l'esdeveniment de clic associat al botó Nom d'usuari.
     */
    @FXML
    void onUsernameButtonClick(ActionEvent event) {
        lbl_user_search.setText("Nom usuari");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca.
     * Obté els valors de l'atribut seleccionat i del valor de cerca introduït.
     * Si algun d'aquests valors està buit, mostra un missatge d'error a l'usuari.
     *
     * @param event l'esdeveniment de clic associat al botó de cerca.
     */
    @FXML
    void onSearchButtonClick(ActionEvent event) {
        lbl_response_empty.setText("");
        attribute = lbl_user_search.getText();
        search = txt_user_search.getText();

        if (attribute.isEmpty() || search.isEmpty()) {
            lbl_response_empty.setText("Has d'introduïr l'atribut i el valor a buscar");
        } else {
            // Lògica per a la cerca segons l'atribut i el valor especificats.
        }
    }

    /**
     * Obté el panell de cerca d'usuaris.
     *
     * @return l'AnchorPane de cerca d'usuaris.
     */
    public AnchorPane getAnch_user_search() {
        return anch_user_search;
    }

    /**
     * Estableix el panell de cerca d'usuaris.
     *
     * @param anch_user_search l'AnchorPane a assignar com a panell de cerca d'usuaris.
     */
    public void setAnch_user_search(AnchorPane anch_user_search) {
        this.anch_user_search = anch_user_search;
    }
}