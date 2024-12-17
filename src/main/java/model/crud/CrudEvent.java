package model.crud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.encryption.CipherUtil;
import model.tokenSingleton.TokenSingleton;
import model.model.Event;
import utilities.URLRequests;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Classe CrudEvent que proporciona mètodes per realitzar operacions CRUD
 * (crear, llegir, actualitzar i esborrar) sobre els esdeveniments en el servidor.
 * Les peticions s'envien al servidor mitjançant la classe HttpClient i inclouen
 * autenticació amb token JWT.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class CrudEvent {

    /** Constants per gestionar els missatges d'error durant la petició al servidor. */
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String SERVER_ERROR = "Error en el servidor";
    private static final String ACCES_DENIED = "Accés denegat";
    private static final String UNKNOWN_ERROR = "Error desconegut";
    private static final String UNKNOWN_EVENT = "Esdeveniment no trobat";
    private static final String TOKEN_ERROR = "Token no proporcionat";
    private static final String NEW_EVENT = "Esdeveniment creat correctament";
    private static final String CORRECT = "Operació correcta";
    private static final String EVENT_EXIST = "Aquest esdeveniment ja existeix.";
    private static final String ID_ERROR = "Identificador incorrecte";

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private HttpRequest request;
    private String jwtToken;

    /**
     * Constructor per defecte que inicialitza l'HttpClient i recupera el token JWT
     * per realitzar les peticions al servidor.
     */
    public CrudEvent() {
        this.httpClient = HttpClient.newHttpClient();
        this.jwtToken = TokenSingleton.getInstance().getJwtToken();
    }

    /**
     * Crea un nou esdeveniment al servidor.
     *
     * @param event L'objecte Event que conté la informació del nou esdeveniment.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String createEvent(Event event) throws Exception {
        if(checkEvent(event)){
            return EVENT_EXIST;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(event);
        String encryptedEvent = CipherUtil.encrypt(jsonRequest);

        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.EVENT_CREATE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .POST(HttpRequest.BodyPublishers.ofString(encryptedEvent, StandardCharsets.UTF_8))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return NEW_EVENT;
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Obté la llista de tots els esdeveniments del servidor.
     *
     * @return Una llista d'objectes Event.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public List<Event> getAllEvents() throws Exception {
        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.EVENT_LIST_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String decryptedEvent = CipherUtil.decrypt(response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(decryptedEvent, new TypeReference<List<Event>>() {});
        } else {
            throw new RuntimeException("Error en la solicitud: " + response.statusCode());
        }
    }

    /**
     * Obté la informació d'un esdeveniment pel seu ID.
     *
     * @param id L'identificador de l'esdeveniment.
     * @return L'objecte Event si es troba l'esdeveniment, o null si no es troba.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public Event getEventById(String id) throws Exception {
        String url = URLRequests.EVENT_ID_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String decryptedEvent = CipherUtil.decrypt(response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(decryptedEvent);
            JsonNode bodyNode = rootNode.get("body");
            return objectMapper.treeToValue(bodyNode, Event.class);
        } else {
            return null;
        }
    }

    /**
     * Actualitza la informació d'un esdeveniment existent.
     *
     * @param event L'objecte Event amb la informació actualitzada.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String updateEvent(Event event) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(event);
        String encryptedRequest = CipherUtil.encrypt(requestBody);

        String url = URLRequests.EVENT_CREATE_URL + "/" + event.getId();

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .PUT(HttpRequest.BodyPublishers.ofString(encryptedRequest))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "Esdeveniment actualitzat correctament";
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Esborra un esdeveniment pel seu ID.
     *
     * @param id L'identificador de l'esdeveniment a esborrar.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String deleteEvent(String id) throws Exception {
        if(id==null || id.isEmpty()){
            return ID_ERROR;
        }

        String url = URLRequests.EVENT_DELETE_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .DELETE()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "Esdeveniment esborrat correctament";
        } else {
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Obté la informació de l'estat d'un esdeveniment pel seu ID.
     *
     * @param id L'identificador de l'esdeveniment.
     * @return L'estat d'un esdeveniment o un missatge d'error al generar la petició per falta de previsió en la
     * data de l'esdeveniment. Només hi ha previsió meteorològica dos dies abans de l'esdeveniment.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String getStatusById(String id) throws Exception {
        if(getEventById(id) == null){
            return UNKNOWN_EVENT;
        }

        String url = URLRequests.EVENT_STATUS_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CipherUtil.encrypt(jwtToken))
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return CipherUtil.decrypt(response.body());
        }else if (response.statusCode() == 404){
            return "No s'ha pogut generar el JSON";
        }else{
            return null;
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
            case 201 -> NEW_EVENT;
            case 400 -> TOKEN_ERROR + ". Code: " + statusCode;
            case 401 -> INCORRECT_CREDENTIAL_MESSAGE + ". Code: " + statusCode;
            case 403 -> ACCES_DENIED + ". Code: " + statusCode;
            case 404 -> UNKNOWN_EVENT + ". Code: " + statusCode;
            case 500 -> SERVER_ERROR + ". Code: " + statusCode;
            default -> UNKNOWN_ERROR + ". Code: " + statusCode;
        };
    }

    /**
     * Comprova si el nom de l'esdeveniment de l'esdeveniment passat per a paràmetre
     * coincideix amb el nom de l'esdeveniment d'algun dels esdeveniments de la base de dades.
     *
     * @param event L'esdeveniment que volem comprovar.
     * @return si el nom de l'esdeveniment passat com a paràmetre
     * coincideix amb algun de la llista.
     * @throws Exception Si es produeix un error en obtenir la llista d'esdeveniments amb getAllEvents().
     */
    public boolean checkEvent(Event event) throws Exception {
        List<Event> list = getAllEvents();
        for (Event e : list) {
            if (e.getNom().equals(event.getNom())) {
                return true;
            }
        }
        return false;
    }
}