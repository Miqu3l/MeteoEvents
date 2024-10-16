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

public class LoginClient {

    private static final String URL_LOGIN = URLRequests.LOGIN_URL;
    private final HttpClient httpClient;
    private String jwtToken;
    private String funcionalID;

    public LoginClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

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

    public String getJwtToken() {
        return jwtToken;
    }

    public String getFuncionalID() {
        return funcionalID;
    }
}

/*
 // Método para hacer peticiones autenticadas con el JWT
    public String peticionAutenticada(String resourceUrl) throws IOException, InterruptedException {
        if (jwtToken == null) {
            return "Error: No hay token JWT. Debes hacer login primero.";
        }

        // Crear petición HTTP GET con el encabezado Authorization Bearer
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(resourceUrl))
                .header("Authorization", "Bearer " + jwtToken)  // Enviar el JWT
                .GET()
                .build();

        // Enviar la petición
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "Respuesta de recurso autenticado: " + response.body();
        } else {
            return "Error: " + response.statusCode();
        }
    }
 */