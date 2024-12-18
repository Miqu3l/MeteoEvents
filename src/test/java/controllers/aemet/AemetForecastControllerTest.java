package controllers.aemet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.aemet.AemetRequest;
import model.login.LoginClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per a la gestió de la previsió meteorològica amb el controlador
 * AemetForecastController. Aquesta classe verifica les funcionalitats de cerca de
 * previsions meteorològiques utilitzant diferents codis de municipi.
 *
 * @author Miguel Rodríguez
 * @version 1.0
 */
public class AemetForecastControllerTest {

    /**
     * Nom d'usuari vàlid per a l'administrador utilitzat en les proves.
     */
    private static final String VALID_ADMIN_USERNAME = "admin";

    /**
     * Contrasenya vàlida per a l'administrador utilitzada en les proves.
     */
    private static final String VALID_ADMIN_PASSWORD = "admin24";

    /**
     * Instància del client AemetRequest, utilitzada per interactuar amb l'API de l'AEMET
     * durant les proves.
     */
    private static AemetRequest aemetRequest;

    /**
     * Client de login utilitzat per gestionar l'autenticació en les proves.
     */
    private LoginClient loginClient;

    /**
     * Codi de municipi vàlid utilitzat en les proves.
     */
    private static String validTownCode = "08019";

    /**
     * Codi de municipi invàlid utilitzat en les proves.
     */
    private static String invalidTownCode = "080199";

    /**
     * Codi de municipi buit utilitzat en les proves.
     */
    private static String emptyTownCode = "";

    /**
     * Configuració inicial abans de cada prova. Es realitza l'autenticació amb
     * un usuari administrador vàlid i es prepara l'objecte AemetRequest.
     *
     * @throws Exception Si es produeix un error durant la configuració.
     */
    @BeforeEach
    void setUp() throws Exception {
        loginClient = new LoginClient();
        loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
        aemetRequest = new AemetRequest();
    }

    /**
     * Prova per verificar que es poden cercar previsions meteorològiques amb un codi
     * de municipi vàlid. Es comprova que la resposta no sigui nul·la i que sigui
     * un JSON vàlid.
     *
     * @throws Exception Si es produeix un error durant la sol·licitud o el processament de la resposta.
     */
    @Test
    @Order(1)
    void testSearchForecastWithValidTownCode() throws Exception {
        String response = aemetRequest.aemetForecastRequest(validTownCode);

        assertNotNull(response);
        assertDoesNotThrow(() -> {
            new ObjectMapper().readTree(response);
        }, "La resposta no és un Json");
    }

    /**
     * Prova per verificar que cercar una previsió meteorològica amb un codi
     * de municipi buit retorna un missatge d'error adequat.
     *
     * @throws Exception Si es produeix un error durant la sol·licitud o el processament de la resposta.
     */
    @Test
    @Order(2)
    void testSearchForecastWithEmptyTownCode() throws Exception {
        String response = aemetRequest.aemetForecastRequest(emptyTownCode);

        System.out.println(response);
        assertEquals("Error en la resposta de la Aemet", response);
        assertNotNull(response);
    }

    /**
     * Prova per verificar que cercar una previsió meteorològica amb un codi
     * de municipi invàlid retorna un missatge d'error adequat.
     *
     * @throws Exception Si es produeix un error durant la sol·licitud o el processament de la resposta.
     */
    @Test
    @Order(3)
    void testSearchForecastWithInvalidTownCode() throws Exception {
        String response = aemetRequest.aemetForecastRequest(invalidTownCode);

        System.out.println(response);
        assertEquals("El codi del municipi és incorrecte", response);
        assertNotNull(response);
    }
}
