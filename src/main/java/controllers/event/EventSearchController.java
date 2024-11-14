package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Event;
import model.crud.CrudEvent;
import utilities.PathsViews;
import java.io.IOException;

/**
 * Controlador per a la funcionalitat de cerca d'esdeveniments.
 * Gestiona la selecció del criteri de cerca (ID, nom o ubicació)
 * i executa la cerca segons l'atribut i valor introduïts.
 */
public class EventSearchController {

    /** Cadena de text que representa el valor de cerca introduït per l'usuari. */
    private String search;

    /** Cadena de text que indica l'atribut seleccionat per a la cerca (ID, nom o ubicació). */
    private String attribute;

    /** Panell principal de l'àrea de cerca d'esdeveniments. */
    @FXML
    private AnchorPane anch_event_search;

    /** Etiqueta que mostra el tipus de cerca seleccionat. */
    @FXML
    private Label lbl_event_search;

    /** Etiqueta que mostra missatges d'error si no es troba cap esdeveniment. */
    @FXML
    private Label lbl_response_event_empty;

    /** Camp de text on l'usuari introdueix el valor a cercar. */
    @FXML
    private TextField txt_event_search;

    private CrudEvent crudEvent;
    private Event event;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        crudEvent = new CrudEvent();
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per ID.
     * Estableix l'atribut de cerca com a "ID".
     *
     * @param event l'esdeveniment de clic associat al botó ID.
     */
    @FXML
    void onIDButtonClick(ActionEvent event) {
        lbl_event_search.setText("ID");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom.
     * Estableix l'atribut de cerca com a "Nom".
     *
     * @param event l'esdeveniment de clic associat al botó Nom.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        lbl_event_search.setText("Nom");
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
        this.event = null;
        lbl_response_event_empty.setText("");
        attribute = lbl_event_search.getText();
        search = txt_event_search.getText();

        if (attribute.isEmpty() || search.isEmpty()) {
            lbl_response_event_empty.setText("Has d'introduïr l'atribut i el valor a buscar");
        } else {
            this.event = crudEvent.getEventById(search);
            if (this.event != null) {
                loadPanel(PathsViews.EVENT_MANAGEMENT_VIEW);
            } else {
                lbl_response_event_empty.setText("Esdeveniment no trobat");
            }
        }
    }

    /**
     * Carrega un nou panell.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Node content = fxmlLoader.load();

            EventManagementController controller = fxmlLoader.getController();
            controller.setEvent(this.event);

            anch_event_search.getChildren().clear();
            anch_event_search.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
