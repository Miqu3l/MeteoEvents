package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.tokenSingleton.TokenSingleton;
import model.model.Event;
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
    private static final String DELETE_OK = "Esdeveniment esborrat correctament";

    @FXML
    private AnchorPane anch_event_list;

    /** Panell on es mostrarà la llista d'esdeveniments. */
    @FXML
    private ListView<Event> list_events;

    /** Label on es mostra el missatge de resposta del servidor en cas d'error. */
    @FXML
    private Label lbl_response_list_event;

    /** Botó per la gestió de l'esborrament d'esdeveniments */
    @FXML
    private Button btn_event_list_delete;

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
    protected void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_response_list_event.setText("");

        Event selectedEvent = list_events.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            String id = selectedEvent.getId();
            String status = crudEvent.deleteEvent(id);

            if (status.equals(DELETE_OK)) {
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
    protected void onViewButtonClick(ActionEvent event) throws IOException {
        lbl_response_list_event.setText("");

        loadPanel(PATH);
    }

    /**
     * Mètode encarregat de fer la petició a la base de dades per obtenir la llista d'esdeveniments.
     *
     * @return la llista dels esdeveniments trobats a la base de dades.
     */
    public List<Event> requestEvents() throws Exception {
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
    public void loadPanel(String path) throws IOException {
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

    public CrudEvent getCrudEvent() {
        return crudEvent;
    }

    /**
     * Configura el CrudEvent, útil per injectar mocks en prova.
     *
     * @param crudEvent la instància de CrudEvent a injectar.
     */
    public void setCrudEvent(CrudEvent crudEvent) {
        this.crudEvent = crudEvent;
    }

    /**
     * Obté el ListView que conté els esdeveniments.
     *
     * @return el ListView d'esdeveniments.
     */
    public ListView<Event> getList_events() {
        return list_events;
    }

    /**
     * Estableix el ListView que conté els esdeveniments.
     *
     * @param list_events el ListView d'esdeveniments a establir.
     */
    public void setList_events(ListView<Event> list_events) {
        this.list_events = list_events;
    }

    /**
     * Obté la llista d'objectes Event.
     *
     * @return la llista d'esdeveniments.
     */
    public List<Event> getList() {
        return list;
    }

    /**
     * Estableix la llista d'objectes Event.
     *
     * @param list la llista d'esdeveniments a establir.
     */
    public void setList(List<Event> list) {
        this.list = list;
    }

    /**
     * Obté l'etiqueta Label utilitzada per mostrar missatges de resposta.
     *
     * @return el Label de resposta.
     */
    public Label getLbl_response_list_event() {
        return lbl_response_list_event;
    }

    /**
     * Estableix l'etiqueta Label utilitzada per mostrar missatges de resposta.
     *
     * @param lbl_response_list_event el Label de resposta a establir.
     */
    public void setLbl_response_list_event(Label lbl_response_list_event) {
        this.lbl_response_list_event = lbl_response_list_event;
    }

    /**
     * Obté el botó d'eliminació per a la gestió d'esdeveniments.
     *
     * @return el botó btn_event_management_delete
     */
    public Button getBtn_event_list_delete() {
        return btn_event_list_delete;
    }
}