package model.aemet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe que permet fer sol·licituds a l'API d'AEMET per obtenir prediccions horàries
 * de municipis específics.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class AemetRequest {

    private static final String URL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/";
    private static final String AEMET_TOKEN = "/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtcmdhcnJpZ2FAZ21haWwuY29t" +
            "IiwianRpIjoiNGI5NTljNTktNTJhMS00NDE1LTk5ZGEtZjY2ZTNiNGI2ZDAxIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE3MzM5MzA0O" +
            "TUsInVzZXJJZCI6IjRiOTU5YzU5LTUyYTEtNDQxNS05OWRhLWY2NmUzYjRiNmQwMSIsInJvbGUiOiIifQ.2kzhhHwQVc2f0TNBOb7" +
            "CnjMx5z7tsNdxxAzI9SY75C4";

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private HttpRequest request;

    /**
     * Constructor per defecte que inicialitza el client HTTP.
     */
    public AemetRequest() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Constructor per fer les proves.
     */
    public AemetRequest(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Realitza una sol·licitud a l'API d'AEMET per obtenir la predicció horària d'un municipi
     * específic identificat pel seu codi.
     *
     * @param codiMunicipi El codi del municipi per al qual es vol obtenir la predicció.
     * @return Una cadena amb les dades de la predicció en format JSON o retorna un missatge d'error.
     * @throws Exception Si es produeix algun error durant la sol·licitud o el processament.
     */
    public String aemetForecastRequest(String codiMunicipi) throws Exception{
        if(codiMunicipi == null){
            return "El codi del municipi no pot ser nul o buit";
        }

        request = HttpRequest.newBuilder()
                .uri(URI.create(URL + codiMunicipi + AEMET_TOKEN))
                .GET()
                .header("cache-control", "no-cache")
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if(jsonNode.get("descripcion").asText().equals("Error al obtener los datos")){
                return "El codi del municipi és incorrecte";
            }
            String data = jsonNode.get("datos").asText();

            request = HttpRequest.newBuilder()
                    .uri(URI.create(data))
                    .GET()
                    .header("cache-control", "no-cache")
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode()==200){
                return response.body();
            }else{
                return "Error en la resposta de la Aemet";
            }
        }else{
            return "Error en la resposta de la Aemet";
        }
    }

    /**
     * Obté el client HTTP associat a aquesta instància.
     *
     * @return L'objecte HttpClient actualment configurat per realitzar les sol·licituds HTTP.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Estableix un client HTTP personalitzat per a aquesta instància.
     *
     * @param httpClient L'objecte HttpClient que s'utilitzarà per realitzar les sol·licituds HTTP.
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}