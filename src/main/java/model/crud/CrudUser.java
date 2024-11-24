package model.crud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Classe CrudUser que proporciona mètodes per realitzar operacions CRUD
 * (crear, llegir, actualitzar i esborrar) sobre els usuaris en el servidor.
 * Les peticions s'envien al servidor mitjançant la classe HttpClient i inclouen
 * autenticació amb token JWT.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class CrudUser {

    /** Constants per gestionar els missatges d'error durant la petició al servidor. */
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String SERVER_ERROR = "Error en el servidor";
    private static final String ACCES_DENIED = "Accés denegat";
    private static final String UNKNOWN_ERROR = "Error desconegut";
    private static final String UNKNOWN_USER = "Usuari no trobat";
    private static final String TOKEN_ERROR = "Token no proporcionat";
    private static final String NEW_USER = "Usuari creat correctament";
    private static final String CORRECT = "Operació correcta";
    private static final String USER_EXIST = "Aquest usuari ja existeix a la base de dades.";
    private static final String USER_MODIFY = "Usuari actualitzat correctament";
    private static final String USER_DELETE = "Usuari esborrat correctament";
    private static final String ID_ERROR = "Identificador incorrecte";

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private HttpRequest request;
    private String jwtToken;

    /**
     * Constructor per defecte que inicialitza l'HttpClient i recupera el token JWT
     * per realitzar les peticions al servidor.
     */
    public CrudUser() {
        this.httpClient = HttpClient.newHttpClient();
        this.jwtToken = TokenSingleton.getInstance().getJwtToken();
    }

    /**
     * Crea un nou usuari al servidor.
     *
     * @param user L'objecte User que conté la informació del nou usuari.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String createUser(User user) throws Exception {
        if(checkUser(user)){
            return USER_EXIST;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(user);

        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.USER_CREATE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest, StandardCharsets.UTF_8))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            return NEW_USER;
        }else{
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Obté la llista de tots els usuaris del servidor.
     *
     * @return Una llista d'objectes User.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public List<User> getAllUsers() throws Exception {
        request = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.USER_LIST_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<List<User>>() {});
        } else {
            throw new RuntimeException("Error en la solicitud: " + response.statusCode());
        }
    }

    /**
     * Obté la informació d'un usuari pel seu ID.
     *
     * @param id L'identificador de l'usuari.
     * @return L'objecte User si es troba l'usuari, o null si no es troba.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public User getUserById(String id) throws Exception {
        String url = URLRequests.USER_ID_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<User>() {});
        } else {
            return null;
        }
    }

    /**
     * Actualitza la informació d'un usuari existent.
     *
     * @param user L'objecte User amb la informació actualitzada.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String updateUser(User user) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);

        String url = URLRequests.USER_CREATE_URL + "/" + user.getID();

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            return USER_MODIFY;
        }else{
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Esborra un usuari pel seu ID.
     *
     * @param id L'identificador de l'usuari a esborrar.
     * @return Un missatge amb el resultat de l'operació.
     * @throws Exception Si es produeix un error en l'enviament de la petició HTTP.
     */
    public String deleteUser(String id) throws Exception {
        if(id==null || id.isEmpty()){
            return ID_ERROR;
        }

        String url = URLRequests.USER_ID_URL.replace("{id}", id);

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .DELETE()
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            return USER_DELETE;
        }else{
            return returnMessage(response.statusCode());
        }
    }

    /**
     * Retorna un missatge en funció del codi d'estat rebut del servidor.
     *
     * @param statusCode El codi d'estat de la resposta HTTP.
     * @return Un missatge corresponent al codi d'estat.
     */
    public String returnMessage(int statusCode){
        return switch (statusCode) {
            case 200 -> CORRECT;
            case 201 -> NEW_USER;
            case 400 -> TOKEN_ERROR + ". Code: " + statusCode;
            case 401 -> INCORRECT_CREDENTIAL_MESSAGE + ". Code: " + statusCode;
            case 403 -> ACCES_DENIED + ". Code: " + statusCode;
            case 404 -> UNKNOWN_USER + ". Code: " + statusCode;
            case 500 -> SERVER_ERROR + ". Code: " + statusCode;
            default -> UNKNOWN_ERROR + ". Code: " + statusCode;
        };
    }

    /**
     * Comprova si el nom d'usuari (username) del User passat per a paràmetre
     * coincideix amb el nom d'usuari d'algun dels usuaris de la base de dades.
     *
     * @param user L'usuari que volem comprovar.
     * @return si el nom d'usuari de l'usuari passat com a paràmetre
     * coincideix amb algun de la llista.
     * @throws Exception Si es produeix un error en obtenir la llista d'usuaris amb getAllUsers().
     */
    public boolean checkUser(User user) throws Exception {
        List<User> list = getAllUsers();
        for (User u : list) {
            if (u.getNom_usuari().equals(user.getNom_usuari())) {
                return true;
            }
        }
        return false;
    }
}