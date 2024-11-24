package controllers.aemet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.aemet.AemetRequest;
import model.aemet.AemetResponse;
import java.util.List;

public class AemetForecastController {

    private static final String AEMET_ERROR = "Error en la resposta de la Aemet";

    @FXML
    private AnchorPane anch_forecast;

    @FXML
    private Button btn_weather_request;

    @FXML
    private Label lbl_weather_response;

    @FXML
    private TextArea txt_weather_forecast;

    @FXML
    void onWeatherRequestButtonClick(ActionEvent event) throws Exception {
        try {
            AemetRequest request = new AemetRequest();
            String response = request.aemetForecastRequest();

            if (!AEMET_ERROR.equals(response)) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<AemetResponse> aemetResponses = objectMapper.readValue(response,
                        new TypeReference<List<AemetResponse>>() {});

                AemetResponse aemetResponse = aemetResponses.get(0);

                String dataBuscada = "2024-11-26T00:00:00";
                String horaBuscada = "02";
                String periodeBuscat ="0107";

                for (AemetResponse.Prediccion.Dia dia : aemetResponse.getPrediccion().getDia()) {
                    if (dia.getFecha().equals(dataBuscada)) {
                        System.out.println("Dades per la data: " + dataBuscada);

                        //Buscar velicitat de vent mitja i ratxa màxima.
                        if (dia.getVientoAndRachaMax() != null) {
                            for (AemetResponse.Prediccion.Dia.Viento vent : dia.getVientoAndRachaMax()) {
                                if (horaBuscada.equals(vent.getPeriodo())) {
                                    if (vent.getDireccion() != null && vent.getVelocidad() != null) {
                                        System.out.println("Velocitat del vent: " + vent.getVelocidad());
                                    } else if (vent.getValue() != null) {
                                        System.out.println("Ratxa màxima: " + vent.getValue());
                                    }
                                }
                            }
                        }

                        //Buscar probabilitat de pluja.
                        if (dia.getProbPrecipitacion() != null) {
                            for (AemetResponse.Prediccion.Dia.Probabilidad probabilitat : dia.getProbPrecipitacion()) {
                                if (periodeBuscat.equals(probabilitat.getPeriodo())) {
                                    System.out.println("Probabilitat de pluja: " + probabilitat.getValue());
                                }
                            }
                        }

                        //Buscar precipitació
                        if (dia.getPrecipitacion() != null) {
                            for (AemetResponse.Prediccion.Dia.Precipitacion precipitacio : dia.getPrecipitacion()) {
                                if(horaBuscada.equals(precipitacio.getPeriodo())){
                                    System.out.println("Precipitació: " + precipitacio.getValue());
                                }
                            }
                        }

                        //Buscar probabilitat de tempesta
                        if (dia.getProbTormenta() != null) {
                            for (AemetResponse.Prediccion.Dia.ProbTormenta tempesta : dia.getProbTormenta()) {
                                if (periodeBuscat.equals(tempesta.getPeriodo())){
                                    System.out.println("Probabilitat de tempesta: " + tempesta.getValue());
                                }
                            }
                        }
                    }
                }
            } else {
                lbl_weather_response.setText(response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}