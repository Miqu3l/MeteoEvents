package model.crud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Measure;
import model.TokenSingleton;
import model.User;
import utilities.URLRequests;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Classe CrudMeasure que proporciona mètodes per realitzar operacions CRUD
 * (crear, llegir, actualitzar i esborrar) sobre les mesures en el servidor.
 * Les peticions s'envien al servidor mitjançant la classe HttpClient i inclouen
 * autenticació amb token JWT.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class CrudMeasure {

    /** Constants per gestionar els missatges d'error durant la petició al servidor. */
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String SERVER_ERROR = "Error en el servidor";
    private static final String ACCESS_DENIED = "Accés denegat";
    private static final String UNKNOWN_ERROR = "Error desconegut";
    private static final String UNKNOWN_MEASURE = "Mesura no trobada";
    private static final String TOKEN_ERROR = "Token no proporcionat";
    private static final String NEW_MEASURE = "Mesura creada correctament";
    private static final String CORRECT = "Operació correcta";
    private static final String ID_ERROR = "Identificador incorrecte";

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private HttpRequest request;
    private String jwtToken;

    public CrudMeasure() {
        this.httpClient = HttpClient.newHttpClient();
        this.jwtToken = TokenSingleton.getInstance().getJwtToken();
    }

    /**
     * Crea una nova mesura al servidor.
     *
     * @param measure L'objecte Measure que conté la informació de la nova mesura.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String createMeasure(Measure measure) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(measure);

        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.MEASURE_CREATE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest, StandardCharsets.UTF_8))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return NEW_MEASURE;
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Obté la llista de totes les mesures del servidor.
     *
     * @return Una llista d'objectes Measure.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public List<Measure> getAllMeasures() throws Exception {
        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.MEASURE_LIST_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<List<Measure>>() {});
        } else {
            throw new RuntimeException("Error en la solicitud: " + response.statusCode());
        }
    }

    /**
     * Obté la informació d'una mesura pel seu ID.
     *
     * @param id L'identificador de la mesura.
     * @return L'objecte Measure si es troba la mesura, o null si no es troba.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public Measure getMeasureById(String id) throws Exception {
        String url = URLRequests.MEASURE_ID_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<Measure>() {});
        } else {
            return null;
        }
    }

    /**
     * Actualitza la informació d'una mesura existent.
     *
     * @param measure L'objecte Measure amb la informació actualitzada.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String updateMeasure(Measure measure) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(measure);

        String url = URLRequests.MEASURE_CREATE_URL + "/" + measure.getId();

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "Mesura actualitzada correctament";
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Esborra una mesura pel seu ID.
     *
     * @param id L'identificador de la mesura a esborrar.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String deleteMeasure(String id) throws Exception {
        if(id==null || id.isEmpty()){
            return ID_ERROR;
        }

        String url = URLRequests.MEASURE_ID_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .DELETE()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "Mesura esborrada correctament";
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Retorna un missatge en funció del codi d'estat rebut del servidor.
     *
     * @param statusCode El codi d'estat de la resposta HTTP.
     * @return Un missatge corresponent al codi d'estat.
     */
    private String returnMessage(int statusCode) {
        return switch (statusCode) {
            case 200 -> CORRECT;
            case 201 -> NEW_MEASURE;
            case 400 -> TOKEN_ERROR + ". Code: " + statusCode;
            case 401 -> INCORRECT_CREDENTIAL_MESSAGE + ". Code: " + statusCode;
            case 403 -> ACCESS_DENIED + ". Code: " + statusCode;
            case 404 -> UNKNOWN_MEASURE + ". Code: " + statusCode;
            case 500 -> SERVER_ERROR + ". Code: " + statusCode;
            default -> UNKNOWN_ERROR + ". Code: " + statusCode;
        };
    }
}
