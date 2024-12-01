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
 */
public class AemetRequest {

    private static final String URL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/";
    private static final String AEMET_TOKEN = "/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtcmdhcnJpZ2FAZ21haWwuY29tIiwianRpIjoiZDEwMT" +
            "MyNGEtNTE4ZC00OTQ4LTk2NzYtYTBiYjBhMjU5OTZjIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE3MjU0NzE1NTksInVzZXJJZCI6" +
            "ImQxMDEzMjRhLTUxOGQtNDk0OC05Njc2LWEwYmIwYTI1OTk2YyIsInJvbGUiOiIifQ.MeI-InPKbLkkDibtj4KWpT8V-Tz3eoz" +
            "Zfn3CwiquLdQ";

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
     * Realitza una sol·licitud a l'API d'AEMET per obtenir la predicció horària d'un municipi
     * específic identificat pel seu codi.
     *
     * @param codiMunicipi El codi del municipi per al qual es vol obtenir la predicció.
     * @return Una cadena amb les dades de la predicció en format JSON o retorna un missatge d'error.
     * @throws Exception Si es produeix algun error durant la sol·licitud o el processament.
     */
    public String aemetForecastRequest(String codiMunicipi) throws Exception {
        request = HttpRequest.newBuilder()
                .uri(URI.create(URL + codiMunicipi + AEMET_TOKEN))
                .GET()
                .header("cache-control", "no-cache")
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) { // Verificar si la resposta inicial és correcta
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String data = jsonNode.get("datos").asText(); // Obtenir l'URL de les dades reals

            request = HttpRequest.newBuilder()
                    .uri(URI.create(data))
                    .GET()
                    .header("cache-control", "no-cache")
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error en la resposta de la Aemet";
            }
        } else {
            return "Error en la resposta de la Aemet";
        }
    }
}