package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String URL_LOGIN = URLRequests.LOGIN_URL;
    private final HttpClient httpClient;
    private String jwtToken;
    private String funcionalID;

    /**
     * Constructor. Crea una nova instància de LoginClient i inicialitza el client HTTP.
     */
    public LoginClient() {
        this.httpClient = HttpClient.newHttpClient();
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
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica l'estat de la resposta, guarda el token, guarda el tipus d'usuari
        // i retorna un missatge amb la confirmació de la connexió
        switch (response.statusCode()) {
            case 200:
                //Jackson per extreure la informació del JSON retornat
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                jwtToken = jsonNode.get("token").asText();
                funcionalID = jsonNode.get("funcionalId").asText();
                return "Login correcte";
            case 401:
                return "Credencials incorrectes";
            case 403:
                return "Acces denegat";
            case 500:
                return "Error en el servidor";
            default:
                return "Error desconegut: " + response.statusCode();
        }

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
     * Retorna el tipus d'usuari que ha iniciat sessió.
     *
     * @return L'ID funcional com a cadena de text.
     */
    public String getFuncionalID() {
        return funcionalID;
    }
}