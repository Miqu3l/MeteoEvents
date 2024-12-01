package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import model.model.Measure;
import model.tokenSingleton.TokenSingleton;
import model.crud.CrudMeasure;
import utilities.PathsViews;
import java.io.IOException;

/**
 * Controlador per a la funcionalitat de cerca de mesures.
 * Gestiona la selecció del criteri de cerca (ID o tipus)
 * i executa la cerca segons l'atribut i valor introduïts.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class MeasureSearchController {

    /** Cadena de text que representa el valor de cerca introduït per l'usuari. */
    private String search;

    /** Cadena de text que indica l'atribut seleccionat per a la cerca (ID o tipus). */
    private String attribute;

    /** Panell principal de l'àrea de cerca de mesures. */
    @FXML
    private AnchorPane anch_measure_search;

    /** Etiqueta que mostra el criteri de cerca seleccionat per l'usuari. */
    @FXML
    private Label lbl_measure_search;

    /** Etiqueta per mostrar missatges d'error o d'avís si no es troba cap resultat. */
    @FXML
    private Label lbl_response;

    /** Camp de text on l'usuari introdueix el valor que vol buscar. */
    @FXML
    private TextField txt_measure_search;

    /** Panell titulat que conté les opcions de selecció de l'atribut de cerca. */
    @FXML
    private TitledPane tit_attribute_search;

    private String jwtToken;
    private CrudMeasure crudMeasure;
    private Measure measure;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        jwtToken = TokenSingleton.getInstance().getJwtToken();
        crudMeasure = new CrudMeasure();
    }

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
     * Mètode invocat quan es fa clic al botó de cerca per tipus.
     * Estableix l'atribut de cerca com a "Tipus".
     *
     * @param event l'esdeveniment de clic associat al botó Tipus.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        //TODO
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca.
     * Obté els valors de l'atribut seleccionat i del valor de cerca introduït.
     * Si algun d'aquests valors està buit, mostra un missatge d'error a l'usuari.
     *
     * @param event l'esdeveniment de clic associat al botó de cerca.
     */
    @FXML
    void onSearchButtonClick(ActionEvent event) throws Exception {
        measure = null;
        lbl_response.setText("");
        attribute = lbl_measure_search.getText();
        search = txt_measure_search.getText();

        if (attribute.isEmpty() || search.isEmpty()) {
            lbl_response.setText("Has d'introduïr l'atribut i el valor a buscar");
        } else {
            if (attribute.equals("ID")) {
                measure = crudMeasure.getMeasureById(search);
            } else if (attribute.equals("Nom")) {
                measure = crudMeasure.getMeasureById(search);
            }

            if (measure != null) {
                loadPanel(PathsViews.MEASURE_MANAGEMENT_VIEW);
            } else {
                lbl_response.setText("Mesura no trobada");
            }
        }
    }

    /**
     * Carrega un nou panell per gestionar la mesura trobada.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Node content = fxmlLoader.load();

            MeasureManagementController controller = fxmlLoader.getController();
            controller.setMeasure(measure);

            anch_measure_search.getChildren().clear();
            anch_measure_search.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}