package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import model.model.Measure;
import model.tokenSingleton.TokenSingleton;
import model.crud.CrudMeasure;
import utilities.PathsViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

/**
 * Controlador per a la visualització de la llista de mesures.
 * Gestiona les accions dels botons "Guardar" i "Visualitzar" i proporciona
 * mètodes per obtenir i establir el panell de la llista de mesures.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class MeasureListController {

    /** Panell de tipus AnchorPane que conté la llista de mesures. */
    @FXML
    private AnchorPane anch_measure_list;

    /** Panell on es mostrarà la llista de mesures. */
    @FXML
    private ListView<Measure> list_measures;

    /** Label on es mostra el missatge de resposta del servidor en cas d'error. */
    @FXML
    private Label lbl_response_list_measure;

    /** Constant per gestionar que s'ha seleccionat una mesura. */
    private static final String RESPONSE_NULL = "Has de seleccionar una mesura.";
    private static final String PATH = PathsViews.MEASURE_MANAGEMENT_VIEW;

    private HttpClient httpClient;
    private String jwtToken;
    private List<Measure> list;
    private CrudMeasure crudMeasure;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        jwtToken = TokenSingleton.getInstance().getJwtToken();
        lbl_response_list_measure.setText("");
        crudMeasure = new CrudMeasure();
        list = requestMeasures();
        loadList(list);
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Guardar".
     * Permet gestionar l'acció de guardar les dades de les mesures.
     *
     * @param event l'esdeveniment de clic associat al botó de "Guardar".
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_response_list_measure.setText("");

        Measure selectedMeasure = list_measures.getSelectionModel().getSelectedItem();

        if (selectedMeasure != null) {
            String id = selectedMeasure.getId();
            String status = crudMeasure.deleteMeasure(id);

            if (status.equals("Mesura esborrada correctament")) {
                list_measures.getItems().clear();
                list = requestMeasures();
                loadList(list);
            } else {
                lbl_response_list_measure.setText(status);
            }
        } else {
            lbl_response_list_measure.setText(RESPONSE_NULL);
        }
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     * Permet gestionar l'acció per visualitzar els detalls de la mesura seleccionada.
     *
     * @param event l'esdeveniment de clic associat al botó de "Visualitzar".
     */
    @FXML
    void onViewButtonClick(ActionEvent event) throws IOException {
        lbl_response_list_measure.setText("");
        loadPanel(PATH);
    }

    /**
     * Mètode encarregat de fer la petició a la base de dades.
     *
     * @return la llista de mesures trobades a la base de dades.
     */
    public List<Measure> requestMeasures() throws Exception {
        return crudMeasure.getAllMeasures();
    }

    /**
     * Mètode encarregat de carregat el ListView amb les mesures retornades per
     * la base de dades.
     *
     * @param list llista d'objectes Measure retornada per la base de dades.
     */
    private void loadList(List<Measure> list) throws Exception {
        list_measures.getItems().addAll(list);
    }

    /**
     * Carrega un nou panell.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Node content = fxmlLoader.load();

        Measure selectedMeasure = list_measures.getSelectionModel().getSelectedItem();

        MeasureManagementController controller = fxmlLoader.getController();
        controller.setMeasure(selectedMeasure);

        anch_measure_list.getChildren().clear();
        anch_measure_list.getChildren().add(content);

        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }

    /**
     * Obté el panell de la llista de mesures.
     *
     * @return l'AnchorPane que conté la llista de mesures.
     */
    public AnchorPane getAnch_user_list() {
        return anch_measure_list;
    }

    /**
     * Estableix el panell de la llista de mesures.
     *
     * @param anch_user_list l'AnchorPane a assignar com a panell de la llista de mesures.
     */
    public void setAnch_user_list(AnchorPane anch_user_list) {
        this.anch_measure_list = anch_user_list;
    }

    /**
     * Configura el CrudMeasure, útil per injectar mocks en prova.
     *
     * @param crudMeasure la instància de CrudEvent a injectar.
     */
    public void setCrudMeasure(CrudMeasure crudMeasure) {
        this.crudMeasure = crudMeasure;
    }
}