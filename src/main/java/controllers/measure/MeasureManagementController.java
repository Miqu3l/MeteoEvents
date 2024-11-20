package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Measure;
import model.crud.CrudMeasure;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió de les mesures.
 * En aquesta classe es gestionen les interaccions amb els botons per a esborrar,
 * crear o modificar les mesures registrades.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class MeasureManagementController {

    /**
     * Constant amb el missatge d'error quan es produeix un problema durant la petició.
     */
    private static final String ERROR = "S'ha produït un error.";

    @FXML
    private AnchorPane anch_measure_management;

    /**
     * Etiqueta que mostra l'ID de la mesura gestionada.
     */
    @FXML
    private Label lbl_measure_management_id;

    /** Etiqueta que mostra la resposta després d'una operació (creació, modificació o esborrat) d'una mesura. */
    @FXML
    private Label lbl_measure_response;

    /** Camp de text per introduir o mostrar l'acció de la mesura. */
    @FXML
    private TextField txt_measure_management_accio;

    /** Camp de text per introduir o mostrar la condició de la mesura. */
    @FXML
    private TextField txt_measure_management_condicio;

    /** Camp de text per introduir o mostrar el valor numèric de la mesura. */
    @FXML
    private TextField txt_measure_management_valor;

    /** Camp de text per introduir o mostrar la unitat de mesura del valor. */
    @FXML
    private TextField txt_measure_management_valor_uml;

    private Measure measure;
    private CrudMeasure crudMeasure;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        crudMeasure = new CrudMeasure();
        measure = new Measure();
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar una mesura.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_measure_response.setText("");

        if (measure != null) {
            String response = crudMeasure.deleteMeasure(measure.getId());
            lbl_measure_response.setText(response);
            cleanMeasure();
        } else {
            lbl_measure_response.setText(ERROR);
        }
    }

    /**
     * Gestiona l'esdeveniment del botó per crear una mesura.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) throws Exception {
        lbl_measure_response.setText("");
        saveMeasure();
        String response = crudMeasure.createMeasure(measure);
        lbl_measure_response.setText(response);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar una mesura.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModifyButtonClick(ActionEvent event) throws Exception {
        lbl_measure_response.setText("");
        saveMeasure();
        if (measure != null) {
            String response = crudMeasure.updateMeasure(measure);
            lbl_measure_response.setText(response);
        } else {
            lbl_measure_response.setText(ERROR);
        }
    }

    /**
     * Estableix la mesura actual i inicialitza els camps de la interfície d'usuari
     * amb les dades de la mesura proporcionada.
     *
     * @param measure La mesura que es vol gestionar.
     */
    public void setMeasure(Measure measure) {
        this.measure = measure;
        initializeMeasure(measure);
    }

    /**
     * Inicialitza els camps de la interfície d'usuari amb la informació de la mesura.
     *
     * @param measure La mesura que es vol visualitzar.
     */
    private void initializeMeasure(Measure measure) {
        lbl_measure_management_id.setText(measure.getId());
        txt_measure_management_valor.setText(Double.toString(measure.getValor()));
        txt_measure_management_valor_uml.setText(measure.getValorUm());
        txt_measure_management_accio.setText(measure.getAccio());
        txt_measure_management_condicio.setText(measure.getCondicio());
    }

    /**
     * Neteja tots els camps de la interfície d'usuari.
     */
    private void cleanMeasure() {
        lbl_measure_management_id.setText("");
        txt_measure_management_valor.setText("");
        txt_measure_management_valor_uml.setText("");
        txt_measure_management_accio.setText("");
        txt_measure_management_condicio.setText("");
    }

    /**
     * Guarda tots els camps a l'objecte mesura.
     */
    private void saveMeasure() {
        measure.setAccio(txt_measure_management_accio.getText());
        measure.setValor(Double.parseDouble(txt_measure_management_valor.getText()));
        measure.setCondicio(txt_measure_management_condicio.getText());
        measure.setValorUm(txt_measure_management_valor_uml.getText());
    }
}