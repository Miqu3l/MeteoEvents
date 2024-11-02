package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la funcionalitat de cerca de mesures de prevenció.
 * Permet a l'usuari seleccionar l'atribut de cerca (ID o nom) i
 * iniciar una cerca segons l'atribut i valor especificats.
 */
public class MeasureSearchController {

    /** Panell principal de l'àrea de cerca de mesures. */
    @FXML
    private AnchorPane anch_user_search;

    /** Botó per iniciar la cerca amb el criteri seleccionat. */
    @FXML
    private Button btn_measure_search;

    /** Botó per seleccionar la cerca de mesura per ID. */
    @FXML
    private Button btn_measure_search_id;

    /** Botó per seleccionar la cerca de mesura per nom. */
    @FXML
    private Button btn_measure_search_name;

    /** Etiqueta que mostra l'atribut de cerca seleccionat (ID o nom). */
    @FXML
    private Label lbl_measure_search;

    /** Etiqueta per mostrar missatges d'error o d'avís si no es troba cap resultat. */
    @FXML
    private Label lbl_response_measure_empty;

    /** Panell titulat que conté les opcions de selecció de l'atribut de cerca. */
    @FXML
    private TitledPane tit_attribute_search;

    /** Camp de text on l'usuari introdueix el valor que vol buscar. */
    @FXML
    private TextField txt_measure_search;

    /**
     * Mètode invocat quan es fa clic al botó de cerca per ID.
     * Estableix l'atribut de cerca com a "ID".
     *
     * @param event l'esdeveniment de clic associat al botó ID.
     */
    @FXML
    void onIDButtonClick(ActionEvent event) {
        lbl_measure_search.setText("ID");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom.
     * Estableix l'atribut de cerca com a "Nom".
     *
     * @param event l'esdeveniment de clic associat al botó Nom.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        lbl_measure_search.setText("Nom");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca.
     * Aquest mètode obté l'atribut i el valor introduït per a la cerca
     * i executa la cerca segons aquests criteris.
     *
     * @param event l'esdeveniment de clic associat al botó de cerca.
     */
    @FXML
    void onSearchButtonClick(ActionEvent event) {
        // Lògica per a la cerca segons l'atribut i el valor especificats.
    }
}