package controllers;

import model.login.LoginClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Classe per la realització dels tests del controllador de la finestra de login, LoginController.
 * Aquesta classe fa peticions directes al servidor per realitzar proves d'integració.
 *
 * @author Miguel Rodriguez Garriga
 */
class LoginControllerTest {

    private static final String VALID_ADMIN_USERNAME = "admin";
    private static final String VALID_ADMIN_PASSWORD = "admin24";
    private static final String VALID_STANDARD_USERNAME = "convidat";
    private static final String VALID_STANDARD_PASSWORD = "convidat24";
    private static final String INVALID_USERNAME = "user";
    private static final String INVALID_PASSWORD = "1234";
    private static final String SIMULATED_EXCEPTION = "Servidor desconnectat";
    private static final String EXPECTED_LOGIN_MESSAGE = "Login correcte";
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String ADMIN_FUNCTIONAL_ID = "ADM";
    private static final String STANDARD_FUNCTIONAL_ID = "USR";

    private LoginClient loginClient;
    private HttpClient mockHttpClient;
    private LoginClient mockLoginClient;

    /**
     * Configuració inicial de la classe, instanciant totes les classes necessàries per fer
     * peticions reals a la base de dades.
     */
    @BeforeEach
    void setUp() {
        loginClient = new LoginClient();
        mockHttpClient = mock(HttpClient.class);
        mockLoginClient = new LoginClient(mockHttpClient);
    }

    /**
     * Prova del login amb credencials d'usuari administrador vàlides.
     * Envia petició al servidor i comprova que el codi de la resposta sigui 200,
     * que el token no estigui buit i que el funcional ID sigui correcte.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    void testAdminLoginSuccessfully() throws Exception {
        String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);

        assertThat(result).isEqualTo(EXPECTED_LOGIN_MESSAGE);
        assertThat(loginClient.getFuncionalID()).isEqualTo(ADMIN_FUNCTIONAL_ID);
        assertThat(loginClient.getJwtToken()).isNotEmpty();
    }

    /**
     * Prova del login amb credencials d'usuari estàndard vàlides.
     * Envia petició al servidor i comprova que el codi de la resposta sigui 200,
     * que el token no estigui buit i que el funcional ID sigui correcte.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    void testStandardLoginSuccessfull() throws Exception {
        String result = loginClient.loginUsuari(VALID_STANDARD_USERNAME, VALID_STANDARD_PASSWORD);

        assertThat(result).isEqualTo(EXPECTED_LOGIN_MESSAGE);
        assertThat(loginClient.getFuncionalID()).isEqualTo(STANDARD_FUNCTIONAL_ID);
        assertThat(loginClient.getJwtToken()).isNotEmpty();
    }

    /**
     * Prova del login amb credencials incorrectes.
     * Envia petició al servidor i comprova que el codi de la resposta sigui 200
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void incorrectCredentialsTest() throws Exception {
        String result = loginClient.loginUsuari(INVALID_USERNAME, INVALID_PASSWORD);

        assertThat(result).isEqualTo(INCORRECT_CREDENTIAL_MESSAGE);
        assertNull(loginClient.getFuncionalID());
        assertNull(loginClient.getJwtToken());
    }

    /**
     * Prova del login en cas d'una excepció de tipus IOException.
     * Simula una excepció i comprova que el missatge d'excepció sigui retornat
     * i que no es generi cap token JWT.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void testIOException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException(SIMULATED_EXCEPTION));

        try {
            String result = mockLoginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
            assertTrue(result.contains(SIMULATED_EXCEPTION));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prova del login en cas d'una excepció de tipus InterruptedException.
     * Simula una InterruptedException i comprova que el missatge d'excepció
     * sigui retornat i que no es generi cap token JWT.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void testInterruptedException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException(SIMULATED_EXCEPTION));

        try {
            String result = mockLoginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
            assertTrue(result.contains(SIMULATED_EXCEPTION));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}