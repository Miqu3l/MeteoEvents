package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la funcionalitat de cerca d'esdeveniments.
 * Permet seleccionar l'atribut de cerca (ID o nom) i realitzar
 * la cerca d'un esdeveniment segons els criteris especificats.
 */
public class EventSearchController {

    /** Panell principal de l'àrea de cerca d'esdeveniments. */
    @FXML
    private AnchorPane anch_event_search;

    /** Botó per iniciar la cerca amb l'atribut i valor introduïts. */
    @FXML
    private Button btn_event_search;

    /** Botó per seleccionar la cerca d'esdeveniment per ID. */
    @FXML
    private Button btn_event_search_id;

    /** Botó per seleccionar la cerca d'esdeveniment per nom. */
    @FXML
    private Button btn_event_search_name;

    /** Etiqueta que mostra l'atribut de cerca seleccionat per l'usuari. */
    @FXML
    private Label lbl_event_search;

    /** Etiqueta per mostrar missatges d'error o avisos en cas de cerca buida. */
    @FXML
    private Label lbl_response_event_empty;

    /** Panell titulat que conté les opcions de selecció de l'atribut de cerca. */
    @FXML
    private TitledPane tit_attribute_search;

    /** Camp de text on l'usuari introdueix el valor que vol buscar. */
    @FXML
    private TextField txt_event_search;

    /**
     * Mètode invocat quan es fa clic al botó de cerca per ID.
     * Estableix l'atribut de cerca com a "ID" per a la cerca d'esdeveniments.
     *
     * @param event l'esdeveniment de clic associat al botó ID.
     */
    @FXML
    void onIDButtonClick(ActionEvent event) {
        // Lògica per establir la cerca per ID
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom.
     * Estableix l'atribut de cerca com a "Nom" per a la cerca d'esdeveniments.
     *
     * @param event l'esdeveniment de clic associat al botó Nom.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        // Lògica per establir la cerca per nom
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca.
     * Recull l'atribut de cerca seleccionat i el valor introduït per a executar la cerca.
     *
     * @param event l'esdeveniment de clic associat al botó de cerca.
     */
    @FXML
    void onSearchButtonClick(ActionEvent event) {
        // Lògica per executar la cerca segons l'atribut i valor seleccionats
    }
}