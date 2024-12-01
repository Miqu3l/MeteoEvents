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
    void onWeatherRequestButtonClick(ActionEvent event) throws Exception {
        //Dades recollides de la previsió
        int windAverage;
        int windMax;
        int rainProbability;
        float rainAmount;
        int stormProbability;
        float snowAmount;
        int snowProbability;
        int temperature;
        int thermalSens;
        int relativeHumidity;

        //Nivell d'alerta
        int rainAlert;
        int snowAlert;
        int windAverageAlert;
        int windMaxAlert;
        int temperatureHighAlert;
        int temperatureLowAlert;
        String alertCondition;

        AlertLevel alertLevel = new AlertLevel();

        try {
            //Declaración de variables con los datos de la petición a la Aemet.
            String codiMunicipi = "25086";  //Código de la población de la Aemet
            String dataBuscada = "2024-11-30T00:00:00"; //Fecha. (Previsión de 40 horas desde la fecha)
            String horaBuscada = "02";      //Hora del dia. (Desde las 00 hasta las 23)
            String periodeBuscat ="0107";   //Franja horario. (Entre las 0107, las 0713, las 1319 y las 1901)
            //Si no encuentra la fecha
            boolean dataTrobada = false;

            //Genera la petición a la Aemet i recibe la respuesta.
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
                                    System.out.println("Probabilitat de pluja: " + probabilitat.getValue());
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
                                    System.out.println("Probabilitat de tempesta: " + tempesta.getValue());
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
                                    System.out.println("Neu: " + neu.getValue());
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
                                    System.out.println("Probabilitat de nevada: " + probNeu.getValue());
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
                                    System.out.println("Temperatura: " + temperatura.getValue());
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
                                    System.out.println("Sensació tèrmica: " + sensTermica.getValue());
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }

                        //Buscar humitat relativa
                        if (dia.getHumedadRelativa() != null) {
                            for (AemetResponse.Prediccion.Dia.humedadRelativa humitatRelativa : dia.getHumedadRelativa()) {
                                if (horaBuscada.equals(humitatRelativa.getPeriodo())){
                                    relativeHumidity = Integer.parseInt(humitatRelativa.getValue());
                                    System.out.println("Humitat relativa: " + humitatRelativa.getValue());
                                    System.out.println("---------------------------------------------------");
                                }
                            }
                        }
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