package controllers.aemet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.aemet.AemetRequest;
import model.aemet.AemetResponse;
import model.aemet.AlertLevel;

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
    private TextField txt_previsio_municipi;

    @FXML
    private TextField txt_previsio_data;

    @FXML
    private TextField txt_previsio_hora;

    @FXML
    void onWeatherRequestButtonClick(ActionEvent event) throws Exception {
        //Dades recollides de la previsió
        int windAverage = 0;
        int windMax = 0;
        int rainProbability = 0;
        float rainAmount;
        int stormProbability = 0;
        float snowAmount = 0;
        int snowProbability = 0;
        int temperature = 0;
        int thermalSens = 0;
        int relativeHumidity = 0;

        //Nivell d'alerta
        int rainAlert = 0;
        int snowAlert = 0;
        int windAverageAlert = 0;
        int windMaxAlert = 0;
        int temperatureHighAlert = 0;
        int temperatureLowAlert = 0;
        String alertCondition;

        boolean dataTrobada = false;

        AlertLevel alertLevel = new AlertLevel();

        String codiMunicipi = txt_previsio_municipi.getText();
        String dataBuscada = txt_previsio_data.getText() + "T00:00:00";
        String horaBuscada = txt_previsio_hora.getText();
        String periodeBuscat = "";
        switch (horaBuscada){
            case "00":
                periodeBuscat = "1901";
                break;
            case "01":
                periodeBuscat = "0107";
                break;
            case "02":
                periodeBuscat = "0107";
                break;
            case "03":
                periodeBuscat = "0107";
                break;
            case "04":
                periodeBuscat = "0107";
                break;
            case "05":
                periodeBuscat = "0107";
                break;
            case "06":
                periodeBuscat = "0107";
                break;
            case "07":
                periodeBuscat = "0713";
                break;
            case "08":
                periodeBuscat = "0713";
                break;
            case "09":
                periodeBuscat = "0713";
                break;
            case "10":
                periodeBuscat = "0713";
                break;
            case "11":
                periodeBuscat = "0713";
                break;
            case "12":
                periodeBuscat = "0713";
                break;
            case "13":
                periodeBuscat = "1319";
                break;
            case "14":
                periodeBuscat = "1319";
                break;
            case "15":
                periodeBuscat = "1319";
                break;
            case "16":
                periodeBuscat = "1319";
                break;
            case "17":
                periodeBuscat = "1319";
                break;
            case "18":
                periodeBuscat = "1319";
                break;
            case "19":
                periodeBuscat = "1901";
                break;
            case "20":
                periodeBuscat = "1901";
                break;
            case "21":
                periodeBuscat = "1901";
                break;
            case "22":
                periodeBuscat = "1901";
                break;
            case "23":
                periodeBuscat = "1901";
                break;
        }

        if(txt_previsio_municipi.getText().isEmpty()){
            lbl_weather_response.setText("Has d'introduïr el codi del municipi correctament");
            return;
        }

        if(txt_previsio_data.getText().isEmpty()){
            lbl_weather_response.setText("Has d'introduïr la data correctament");
            return;
        }

        if(txt_previsio_hora.getText().isEmpty()){
            lbl_weather_response.setText("Has d'introduïr l'hora correctament");
            return;
        }

        lbl_weather_response.setText("");

        try {
            AemetRequest request = new AemetRequest();
            String response = request.aemetForecastRequest(codiMunicipi);

            //Comprueba si la respuesta es correcta.
            if (!AEMET_ERROR.equals(response)) {
                //Crea un objeto para convertir la respuesta en un objeto AemetResponse.
                //En este caso, en una lista de objetos, ya que así es el formato de la respuesta de la Aemet
                ObjectMapper objectMapper = new ObjectMapper();
                List<AemetResponse> aemetResponses = objectMapper.readValue(response,
                        new TypeReference<List<AemetResponse>>() {});

                //Extrae los datos de la fecha, la hora y la franja horaria introducida en las variable del inicio.
                AemetResponse aemetResponse = aemetResponses.get(0);

                for (AemetResponse.Prediccion.Dia dia : aemetResponse.getPrediccion().getDia()) {
                    if (dia.getFecha().equals(dataBuscada)) {
                        dataTrobada= true;
                        System.out.println("Dades per la data: " + dataBuscada);
                        System.out.println("---------------------------------------------------");

                        //Buscar probabilitat de vent
                        if (dia.getVientoAndRachaMax() != null) {
                            for (AemetResponse.Prediccion.Dia.Viento vent : dia.getVientoAndRachaMax()) {
                                if (horaBuscada.equals(vent.getPeriodo())) {
                                    if (vent.getVelocidad() != null && !vent.getVelocidad().isEmpty()) {
                                        windAverage = Integer.parseInt(vent.getVelocidad().get(0));
                                        windAverageAlert = alertLevel.checkAverageWind(windAverage);
                                        System.out.println("Velocitat mitja de vent: " + windAverage);
                                        System.out.println("Nivell d'alerta per vent: " + windAverageAlert);
                                        System.out.println("---------------------------------------------------");
                                    }

                                    if (vent.getValue() != null) {
                                        windMax = Integer.parseInt(vent.getValue());
                                        windMaxAlert = alertLevel.checkMaxWind(windMax);
                                        System.out.println("Ratxa màxima de vent: " + windMax);
                                        System.out.println("Nivell d'alerta por ratxa màxima: " + windMaxAlert);
                                        System.out.println("---------------------------------------------------");
                                    }
                                }
                            }
                        }

                        //Buscar probabilitat de pluja.
                        if (dia.getProbPrecipitacion() != null) {
                            for (AemetResponse.Prediccion.Dia.Probabilidad probabilitat : dia.getProbPrecipitacion()) {
                                if (periodeBuscat.equals(probabilitat.getPeriodo())) {
                                    rainProbability = Integer.parseInt(probabilitat.getValue());
                                    System.out.println("Probabilitat de pluja: " + rainProbability);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar precipitació
                        if (dia.getPrecipitacion() != null) {
                            for (AemetResponse.Prediccion.Dia.Precipitacion precipitacio : dia.getPrecipitacion()) {
                                if(horaBuscada.equals(precipitacio.getPeriodo())){
                                    System.out.println("Precipitació: " + precipitacio.getValue());
                                    rainAmount = Float.parseFloat(precipitacio.getValue());
                                    rainAlert = alertLevel.checkRain(rainAmount);
                                    System.out.println("Nivell d'alerta per pluja: " + rainAlert);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar probabilitat de tempesta
                        if (dia.getProbTormenta() != null) {
                            for (AemetResponse.Prediccion.Dia.ProbTormenta tempesta : dia.getProbTormenta()) {
                                if (periodeBuscat.equals(tempesta.getPeriodo())){
                                    stormProbability = tempesta.getValue();
                                    System.out.println("Probabilitat de tempesta: " + stormProbability);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar neu
                        if (dia.getNieve() != null) {
                            for (AemetResponse.Prediccion.Dia.Nieve neu : dia.getNieve()) {
                                if (horaBuscada.equals(neu.getPeriodo())){
                                    snowAmount = Float.parseFloat(neu.getValue());
                                    snowAlert = alertLevel.checkSnow(snowAmount);
                                    System.out.println("Neu: " + snowAmount);
                                    System.out.println("Nivell d'alerta per neu: " + snowAlert);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar probabilitat de neu
                        if (dia.getProbNieve() != null) {
                            for (AemetResponse.Prediccion.Dia.probNieve probNeu : dia.getProbNieve()) {
                                if (periodeBuscat.equals(probNeu.getPeriodo())){
                                    snowProbability = Integer.parseInt(probNeu.getValue());
                                    System.out.println("Probabilitat de nevada: " + snowProbability);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar temperatura
                        if (dia.getTemperatura() != null) {
                            for (AemetResponse.Prediccion.Dia.Temperatura temperatura : dia.getTemperatura()) {
                                if (horaBuscada.equals(temperatura.getPeriodo())){
                                    temperature = Integer.parseInt(temperatura.getValue());
                                    temperatureHighAlert = alertLevel.checkHighTemperatureLevel(temperature);
                                    temperatureLowAlert = alertLevel.checkLowTemperatureLevel(temperature);
                                    System.out.println("Temperatura: " + temperature);
                                    System.out.println("Nivell d'alerta per alta temperatura: " + temperatureHighAlert);
                                    System.out.println("Nivell d'alerta per baixa temperatura: " + temperatureLowAlert);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar sensació tèrmica
                        if (dia.getSensTermica() != null) {
                            for (AemetResponse.Prediccion.Dia.sensTermica sensTermica : dia.getSensTermica()) {
                                if (horaBuscada.equals(sensTermica.getPeriodo())){
                                    thermalSens = Integer.parseInt(sensTermica.getValue());
                                    System.out.println("Sensació tèrmica: " + thermalSens);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar humitat relativa
                        if (dia.getHumedadRelativa() != null) {
                            for (AemetResponse.Prediccion.Dia.humedadRelativa humitatRelativa : dia.getHumedadRelativa()) {
                                if (horaBuscada.equals(humitatRelativa.getPeriodo())){
                                    relativeHumidity = Integer.parseInt(humitatRelativa.getValue());
                                    System.out.println("Humitat relativa: " + relativeHumidity);
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        txt_weather_forecast.setText("Dades per la data: " + dataBuscada + "\n" +
                                "---------------------------------------------------\n" +
                                "Velocitat mitja de vent: " + windAverage + "\n" +
                                "Nivell d'alerta per vent: " + windAverageAlert + "\n" +
                                "Ratxa màxima de vent: " + windMax + "\n" +
                                "Nivell d'alerta por ratxa màxima: " + windMaxAlert + "\n" +
                                "Probabilitat de pluja: " + rainProbability + "\n" +
                                "Nivell d'alerta per pluja: " + rainAlert + "\n" +
                                "Probabilitat de tempesta: " + stormProbability + "\n" +
                                "Neu: " + snowAmount + "\n" +
                                "Nivell d'alerta per neu: " + snowAlert + "\n" +
                                "Probabilitat de nevada: " + snowProbability + "\n" +
                                "Temperatura: " + temperature + "\n" +
                                "Nivell d'alerta per alta temperatura: " + temperatureHighAlert + "\n" +
                                "Nivell d'alerta per baixa temperatura: " + temperatureLowAlert + "\n" +
                                "Sensació tèrmica: " + thermalSens + "\n" +
                                "Humitat relativa: " + relativeHumidity);
                    }
                }

                if (!dataTrobada) {
                    System.out.println("La previsió per aquesta data no està disponible");
                }
            } else {
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}