package controllers.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.crud.CrudEvent;
import model.statusEvent.StatusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador per gestionar l'estat dels esdeveniments i les alertes meteorològiques associades.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class EventStatusController {

    /** Missatge d'error per quan l'identificador de l'esdeveniment no està proporcionat. */
    private static final String NULL_RESPONSE = "Has d'introduir l'identificador de l'esdeveniment.";

    /** Missatge d'error per quan no hi ha dades meteorològiques disponibles per la data especificada. */
    private static final String ERROR_DATE = "No hi ha previsió meteorològica per la data de l'esdeveniment";

    /** Missatge per indicar que no hi ha dades disponibles. */
    private static final String NO_DATA = "No hi ha dades disponibles";

    /** Missatge per indicar que no s'ha trobat l'esdeveniment especificat. */
    private static final String NO_EVENT = "Esdeveniment no trobat";

    /** Missatges de nivell d'alerta meteorològica. */
    private static final String ALERT_LEVEL_1 = "SEGUR";
    private static final String ALERT_LEVEL_2 = "PRECAUCIÓ";
    private static final String ALERT_LEVEL_3 = "VIGILANT";
    private static final String ALERT_LEVEL_4 = "ALERTA";
    private static final String ALERT_LEVEL_5 = "CANCEL·LAT";

    /** Referència al panell principal de l'estat de l'esdeveniment. */
    @FXML
    public AnchorPane anch_event_status;

    /** Camp de text per introduir l'identificador de l'esdeveniment. */
    @FXML
    private TextField txt_event_status_id;

    /** Etiqueta per mostrar respostes d'estat. */
    @FXML
    private Label lbl_status_response;

    /** Instància per gestionar operacions CRUD d'esdeveniments. */
    private CrudEvent crudEvent;

    /** Etiquetes per mostrar alertes meteorològiques específiques. */
    @FXML
    private Label lbl_event_status_alerta_alta_temperatura;

    @FXML
    private Label lbl_event_status_alerta_baixa_temperatura;

    @FXML
    private Label lbl_event_status_alerta_neu;

    @FXML
    private Label lbl_event_status_alerta_ratxa;

    @FXML
    private Label lbl_event_status_alerta_vent;

    /** Etiquetes per mostrar dades detallades de l'estat meteorològic. */
    @FXML
    private Label lbl_event_status_estat;

    @FXML
    private Label lbl_event_status_probabilitat_neu;

    @FXML
    private Label lbl_event_status_probabilitat_pluja;

    @FXML
    private Label lbl_event_status_quantitat_neu;

    @FXML
    private Label lbl_event_status_quantitat_pluja;

    @FXML
    private Label lbl_event_status_quantitat_ratxa;

    @FXML
    private Label lbl_event_status_quantitat_vent;

    @FXML
    private Label lbl_event_status_temperatura_alta;

    @FXML
    private Label lbl_event_status_temperatura_baixa;

    @FXML
    private Label lbl_event_status_alerta_pluja;

    /** Àrea de text per mostrar mesures de prevenció. */
    @FXML
    private TextArea txt_event_status_mesures;

    /** Etiqueta per mostrar l'alerta màxima detectada. */
    @FXML
    private Label lbl_event_status_alerta_maxima;

    /** Contenidors per a diferents seccions d'informació sobre l'estat de l'esdeveniment. */
    @FXML
    private VBox hbox_event_status_data;

    @FXML
    private HBox hbox_event_status_estat;

    @FXML
    private HBox hbox_event_status_search;

    @FXML
    private Label hbox_event_status_mesures;

    /** Dades de la previsió meteorològica. */
    int windAverage = 0;
    int windMax = 0;
    int rainProbability = 0;
    double rainAmount = 0;
    int stormProbability = 0;
    double snowAmount = 0;
    int snowProbability = 0;
    int temperature = 0;
    int relativeHumidity = 0;

    /** Nivells d'alerta meteorològica. */
    int rainAlert = 0;
    int snowAlert = 0;
    int windAverageAlert = 0;
    int windMaxAlert = 0;
    int temperatureHighAlert = 0;
    int temperatureLowAlert = 0;

    /** Condicions d'alerta màxima i mesures de prevenció associades. */
    List<String> mesuresDePrevencio = new ArrayList<>();;
    int alertaMaxima = 0;

    /**
     * Mètode que s'executa en inicialitzar el controlador.
     * Configura l'estat inicial dels elements de la interfície.
     *
     * @throws Exception si hi ha algun error durant la inicialització.
     */
    @FXML
    protected void initialize() throws Exception {
        crudEvent = new CrudEvent();
        hbox_event_status_data.setVisible(false);
        hbox_event_status_estat.setVisible(false);
        txt_event_status_mesures.setVisible(false);
        hbox_event_status_mesures.setVisible(false);
    }

    /**
     * Mètode per gestionar el clic del botó de cerca.
     * Valida l'identificador introduït i mostra l'estat meteorològic associat a l'esdeveniment.
     */
    public void onSearchButtonClick() {
        lbl_status_response.setText("");
        hbox_event_status_data.setVisible(false);
        hbox_event_status_estat.setVisible(false);
        txt_event_status_mesures.setVisible(false);
        hbox_event_status_mesures.setVisible(false);

        try {
            String response = crudEvent.getStatusById(txt_event_status_id.getText());

            if (response == null) {
                lbl_status_response.setText(NULL_RESPONSE);
                return;
            }else if (response.equals("Esdeveniment no trobat")){
                lbl_status_response.setText(NO_EVENT);
                return;
            }else if(!response.trim().startsWith("{") && !response.trim().startsWith("[")) {
                lbl_status_response.setText(ERROR_DATE);
                return;
            }

            resetVariables();
            hbox_event_status_data.setVisible(true);
            hbox_event_status_search.setVisible(true);
            hbox_event_status_estat.setVisible(true);
            txt_event_status_mesures.setVisible(true);

            ObjectMapper mapper = new ObjectMapper();
            StatusEvent statusEvent = mapper.readValue(response, StatusEvent.class);

            Map<String, Object> hourlyData = statusEvent.getHourlyData();

            if (hourlyData == null || hourlyData.isEmpty()) {
                lbl_status_response.setText(NO_DATA);
                return;
            }

            for (Map.Entry<String, Object> entry : hourlyData.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // Converteix el valor a un Map<String, Object>
                Map<String, Object> valueMap = mapper.convertValue(value, Map.class);

                // Iterar sobre cada dada dins del mapa del valor
                for (Map.Entry<String, Object> innerEntry : valueMap.entrySet()) {
                    switch (innerEntry.getKey()){
                        case "VelocitatMitjaVent":
                            if ((int)innerEntry.getValue() > windAverage){
                                windAverage = (int)innerEntry.getValue();
                            }
                            break;
                        case "AlertaVentMitja":
                            if ((int)innerEntry.getValue() > windAverageAlert){
                                windAverageAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "RatxaMaximaVent":
                            if ((int)innerEntry.getValue() > windMax){
                                windMax = (int)innerEntry.getValue();
                            }
                            break;
                        case "AlertaRatxaMaxima":
                            if ((int)innerEntry.getValue() > windMaxAlert){
                                windMaxAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "ProbabilitatPluja":
                            if ((int)innerEntry.getValue() > rainProbability){
                                rainProbability = (int)innerEntry.getValue();
                            }
                            break;
                        case "Precipitacio":
                            if ((double)innerEntry.getValue() > rainAmount){
                                rainAmount = (double)innerEntry.getValue();
                            }
                            break;
                        case "AlertaPluja":
                            if ((int)innerEntry.getValue() > rainAlert){
                                rainAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "ProbabilitatTempesta":
                            if ((int)innerEntry.getValue() > stormProbability){
                                stormProbability = (int)innerEntry.getValue();
                            }
                            break;
                        case "Neu":
                            if ((double)innerEntry.getValue() > snowAmount){
                                snowAmount = (double)innerEntry.getValue();
                            }
                            break;
                        case "AlertaNeu":
                            if ((int)innerEntry.getValue() > snowAlert){
                                snowAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "ProbabilitatNevada":
                            if ((int)innerEntry.getValue() > snowProbability){
                                snowProbability = (int)innerEntry.getValue();
                            }
                            break;
                        case "Temperatura":
                            if ((int)innerEntry.getValue() > temperature){
                                temperature = (int)innerEntry.getValue();
                            }
                            break;
                        case "AlertaAltaTemperatura":
                            if ((int)innerEntry.getValue() > temperatureHighAlert){
                                temperatureHighAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "AlertaBaixaTemperatura":
                            if ((int)innerEntry.getValue() > temperatureLowAlert){
                                temperatureLowAlert = (int)innerEntry.getValue();
                            }
                            if ((int)innerEntry.getValue() > alertaMaxima){
                                alertaMaxima = (int)innerEntry.getValue();
                            }
                            break;
                        case "HumitatRelativa":
                            if ((int)innerEntry.getValue() > relativeHumidity){
                                relativeHumidity = (int)innerEntry.getValue();
                            }
                            break;
                    }

                    Object innerValue = innerEntry.getValue();

                    // Si hi ha un mapa
                    if (innerValue instanceof Map) {
                        Map<String, Object> subMap = (Map<String, Object>) innerValue;
                        for (Map.Entry<String, Object> subEntry : subMap.entrySet()) {
                            if (!mesuresDePrevencio.contains(subEntry.getValue().toString())) {
                                mesuresDePrevencio.add(subEntry.getValue().toString());
                            }
                        }
                    }
                }
            }
            setStatusEvent();
            System.out.println("--------------------------------------------------------------------------");
            printVariable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualitza l'estat dels elements gràfics de la interfície d'usuari amb la informació
     * relacionada amb l'alerta màxima, les condicions meteorològiques i les mesures de prevenció.
     */
    private void setStatusEvent(){
        switch (alertaMaxima) {
            case 1:
                lbl_event_status_estat.setText(ALERT_LEVEL_1);
                break;
            case 2:
                lbl_event_status_estat.setText(ALERT_LEVEL_2);
                break;
            case 3:
                lbl_event_status_estat.setText(ALERT_LEVEL_3);
                break;
            case 4:
                lbl_event_status_estat.setText(ALERT_LEVEL_4);
                break;
            case 5:
                lbl_event_status_estat.setText(ALERT_LEVEL_5);
                break;
        }
        lbl_event_status_alerta_maxima.setText(String.valueOf(alertaMaxima));
        lbl_event_status_alerta_alta_temperatura.setText(String.valueOf(temperatureHighAlert));
        lbl_event_status_alerta_baixa_temperatura.setText(String.valueOf(temperatureLowAlert));
        lbl_event_status_alerta_vent.setText(String.valueOf(windAverageAlert));
        lbl_event_status_alerta_ratxa.setText(String.valueOf(windMaxAlert));
        lbl_event_status_alerta_neu.setText(String.valueOf(snowAlert));
        lbl_event_status_alerta_pluja.setText(String.valueOf(rainAlert));
        lbl_event_status_probabilitat_pluja.setText(String.valueOf(rainProbability));
        lbl_event_status_quantitat_pluja.setText(String.valueOf(rainAmount));
        lbl_event_status_probabilitat_neu.setText(String.valueOf(snowProbability));
        lbl_event_status_quantitat_neu.setText(String.valueOf(snowAmount));
        lbl_event_status_quantitat_vent.setText(String.valueOf(windAverage));
        lbl_event_status_quantitat_ratxa.setText(String.valueOf(windMax));
        lbl_event_status_temperatura_alta.setText(String.valueOf(temperature));
        lbl_event_status_temperatura_baixa.setText(String.valueOf(temperature));
        txt_event_status_mesures.setText(String.join("\n", mesuresDePrevencio));
    }

    /**
     * Inicialitza les variables de les dades meteorològiques i dels nivells d'alerta per fer una nova
     * petició a la base de dades.
     */
    private void resetVariables(){windAverage = 0;
        windMax = 0;
        rainProbability = 0;
        rainAmount = 0;
        stormProbability = 0;
        snowAmount = 0;
        snowProbability = 0;
        temperature = 0;
        relativeHumidity = 0;rainAlert = 0;
        snowAlert = 0;
        windAverageAlert = 0;
        windMaxAlert = 0;
        temperatureHighAlert = 0;
        temperatureLowAlert = 0;
        mesuresDePrevencio.clear();
        alertaMaxima = 0;
    }

    /**
     * Imprimeix les dades per pantalla per un millor control de les dades retornades per la
     * base de dades.
     */
    private void printVariable(){
        System.out.println("VelocitatMitjaVent: " + windAverage);
        System.out.println("RatxaMaximaVent: " + windMax);
        System.out.println("ProbabilitatPluja: " + rainProbability);
        System.out.println("Precipitacio: " + rainAmount);
        System.out.println("ProbabilitatTempesta: " + stormProbability);
        System.out.println("Neu: " + snowAmount);
        System.out.println("ProbabilitatNevada: " + snowProbability);
        System.out.println("Temperatura: " + temperature);
        System.out.println("HumitatRelativa: " + relativeHumidity);
        System.out.println("AlertaPluja: " + rainAlert);
        System.out.println("AlertaNeu: " + snowAlert);
        System.out.println("AlertaVentMitja: " + windAverageAlert);
        System.out.println("AlertaRatxaMaxima: " + windMaxAlert);
        System.out.println("AlertaAltaTemperatura: " + temperatureHighAlert);
        System.out.println("AlertaBaixaTemperatura: " + temperatureLowAlert);
        System.out.println("Mesure de prevencio: " + mesuresDePrevencio);
        System.out.println("Alerta màxima: " + alertaMaxima);
    }
}