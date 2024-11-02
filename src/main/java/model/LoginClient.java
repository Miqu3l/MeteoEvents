package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.meteoevents.meteoevents.App;
import utilities.PathsViews;
import utilities.URLRequests;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Classe que gestiona el procés d'inici de sessió d'un usuari, mitjançant una sol·licitud HTTP,
 * al servidor de l'aplicació.
 *
 * Envia l'usuari i la contrasenya. Si l'usuari s'autentica correctament, retorna en format Json
 * el token JWT de sessió i el tipus d'usuari. A continuació, extreu aquestes dades i retorna un
 * missatge confirmant l'èxit de la connexió.
 *
 * En cas d'haver-hi algun error durant la connexió, retorna un missatge amb la causa de l'errada.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class LoginClient {

    private static final String EXPECTED_LOGIN_MESSAGE = "Login correcte";
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String SERVER_ERROR = "Error en el servidor";
    private static final String ACCES_DENIED = "Acces denegat";
    private static final String UNKNOWN_ERROR = "Error desconegut";
    private static final String URL_LOGIN = URLRequests.LOGIN_URL;
    private static final String DESCONNECTAT = "Usuari desconnectat";
    private static final String ERROR_DESCONNEXIO = "Error en la desconnexio";

    private final HttpClient httpClient;
    private String jwtToken;
    private String funcionalID;
    private HttpResponse<String> response;

    /**
     * Constructor. Crea una nova instància de LoginClient i inicialitza el client HTTP.
     */
    public LoginClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Constructor. Crea una nova instància de LoginClient i inicialitza el client HTTP.
     *
     * @param httpClient Client http per gestionar les connexions amb el backend.
     */
    public LoginClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Inicia sessió a l'aplicació amb les credencials proporcionades.
     * Guarda en memòria el token i el tipus d'usuari que es vol connectar.
     *
     * @param nomUsuari El nom d'usuari de l'usuari que vol iniciar sessió.
     * @param contrasenya La contrasenya de l'usuari que vol iniciar sessió.
     * @return Un missatge que indica l'estat de l'inici de sessió.
     * @throws IOException Si es produeix un error d'entrada/sortida durant l'enviament
     *                     de la petició HTTP.
     * @throws InterruptedException Si el fil que s'està executant es veu interromput
     *                              mentre espera la resposta del servidor.
     */
    public String loginUsuari(String nomUsuari, String contrasenya) throws IOException, InterruptedException {

        // Codifica els paràmetre per obtindre una URL correcta en cas de trobar algun caràcter especial
        String params = "nomUsuari=" + URLEncoder.encode(nomUsuari, StandardCharsets.UTF_8)
                + "&contrasenya=" + URLEncoder.encode(contrasenya, StandardCharsets.UTF_8);

        // Crea la petició HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_LOGIN))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();

        // Envia la petició i obté la resposta
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica l'estat de la resposta, guarda el token, guarda el tipus d'usuari
        // i retorna un missatge amb ladminaa confirmació de la connexió
        switch (response.statusCode()) {
            case 200:
                //Jackson per extreure la informació del JSON retornat
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                jwtToken = jsonNode.get("token").asText();
                funcionalID = jsonNode.get("funcionalId").asText();
                return EXPECTED_LOGIN_MESSAGE;
            case 401:
                return INCORRECT_CREDENTIAL_MESSAGE;
            case 403:
                return ACCES_DENIED;
            case 500:
                return SERVER_ERROR;
            default:
                return UNKNOWN_ERROR;
        }
    }

    /**
     * Tanca la sessió amb el servidor i torna a la finestra de login.
     * Esborra de memòria el token de la sessió.
     *
     * @param token El token de sessió que es vol esborrar
     * @return Un missatge que indica l'estat del tancament de sessió.
     *
     * @throws IOException Si es produeix un error d'entrada/sortida durant l'enviament
     *                     de la petició HTTP.
     * @throws InterruptedException Si el fil que s'està executant es veu interromput
     *                              mentre espera la resposta del servidor.
     */

    public String logoutUsuari(String token) throws IOException, InterruptedException {

        //Petició de logout al backend
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(URLRequests.LOGOUT_URL))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("Codi de resposta del servidor: " + response.statusCode()
                + "\nMissatge del servidor: " + response.body());

        //Esborra el token
        jwtToken = "";

        loadLoginScreen();

        if(response.statusCode()==200){
            return DESCONNECTAT;
        }else{
            return ERROR_DESCONNEXIO;
        }
    }

    /**
     * Mètode encarregat de la càrrega de la pantalla de login una vegada s'ha fet logout
     * amb el servidor
     *
     * @throws IOException Si es produeix un error d'entrada/sortida durant l'enviament
     * de la petició HTTP.
     * @throws InterruptedException Si el fil que s'està executant es veu interromput
     * mentre espera la resposta del servidor.
     */

    public void loadLoginScreen() throws IOException, InterruptedException{
        //S'obre la finestra de login
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.LOGIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Meteo Events");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Retorna el token JWT obtingut després d'un inici de sessió correcte.
     *
     * @return El token JWT com a cadena de text.
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Setter del token JWT.
     *
     * @param jwtToken El token JWT a establir.
     * @return El token com a cadena de text.
     */

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * Retorna el tipus d'usuari que ha iniciat sessió.
     *
     * @return L'ID funcional com a cadena de text.
     */
    public String getFuncionalID() {
        return funcionalID;
    }
}