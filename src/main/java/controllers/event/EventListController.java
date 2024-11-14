package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.TokenSingleton;
import model.Event;
import model.crud.CrudEvent;
import utilities.PathsViews;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

/**
 * Controlador per a la interfície d'usuari de la llista d'esdeveniments.
 * Gestiona les accions dels botons "Eliminar" i "Visualitzar" i proporciona
 * mètodes per obtenir i establir el panell de la llista d'esdeveniments.
 */
public class EventListController {

    /** Missatge d'error quan no s'ha seleccionat un esdeveniment. */
    private static final String RESPONSE_NULL = "Has de seleccionar un esdeveniment.";

    @FXML
    private AnchorPane anch_event_list;

    /** Panell on es mostrarà la llista d'esdeveniments. */
    @FXML
    private ListView<Event> list_events;

    /** Label on es mostra el missatge de resposta del servidor en cas d'error. */
    @FXML
    private Label lbl_response_list_event;

    private static final String PATH = PathsViews.EVENT_MANAGEMENT_VIEW;

    private HttpClient httpClient;
    private String jwtToken;
    private List<Event> list;
    private CrudEvent crudEvent;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        jwtToken = TokenSingleton.getInstance().getJwtToken();
        lbl_response_list_event.setText("");
        crudEvent = new CrudEvent();
        list = requestEvents();
        loadList(list);
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Eliminar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_response_list_event.setText("");

        Event selectedEvent = list_events.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            String id = selectedEvent.getId();
            String status = crudEvent.deleteEvent(id);

            if (status.equals("Esdeveniment esborrat correctament")) {
                list_events.getItems().clear();
                list = requestEvents();
                loadList(list);
            } else {
                lbl_response_list_event.setText(status);
            }
        } else {
            lbl_response_list_event.setText(RESPONSE_NULL);
        }
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onViewButtonClick(ActionEvent event) throws IOException {
        lbl_response_list_event.setText("");

        loadPanel(PATH);
    }

    /**
     * Mètode encarregat de fer la petició a la base de dades per obtenir la llista d'esdeveniments.
     *
     * @return la llista dels esdeveniments trobats a la base de dades.
     */
    private List<Event> requestEvents() throws Exception {
        return crudEvent.getAllEvents();
    }

    /**
     * Mètode encarregat de carregar el ListView amb els esdeveniments retornats per la base de dades.
     *
     * @param list llista d'objectes Event retornada per la base de dades.
     */
    private void loadList(List<Event> list) {
        list_events.getItems().addAll(list);
    }

    /**
     * Carrega un nou panell.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Node content = fxmlLoader.load();

        Event selectedEvent = list_events.getSelectionModel().getSelectedItem();

        EventManagementController controller = fxmlLoader.getController();
        controller.setEvent(selectedEvent);

        anch_event_list.getChildren().clear();
        anch_event_list.getChildren().add(content);

        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }
}