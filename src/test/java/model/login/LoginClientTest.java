package model.login;

import model.encryption.CipherUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Classe per la realització dels tests de la classe loginClient.
 * Aquesta classe fa servir Mockito per la realització de les proves unitàries, amb la finalitat
 * de simular respostes del servidor.
 *
 * @author Miguel Rodriguez Garriga
 */
class LoginClientTest {

    private static final String VALID_ADMIN_USERNAME = "admin";
    private static final String VALID_ADMIN_PASSWORD = "admin24";
    private static final String VALID_STANDARD_USERNAME = "convidat";
    private static final String VALID_STANDARD_PASSWORD = "convidat24";
    private static final String INVALID_USERNAME = "user";
    private static final String INVALID_PASSWORD = "1234";
    private static final String EXPECTED_LOGIN_MESSAGE = "Login correcte";
    private static final String INCORRECT_CREDENTIAL_MESSAGE = "Credencials incorrectes";
    private static final String SERVER_ERROR = "Error en el servidor";
    private static final String ACCES_DENIED = "Acces denegat";
    private static final String SIMULATED_EXCEPTION = "Exception";

    private LoginClient loginClient;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockResponse;
    private String responseBodyAdmin;
    private String responseBodyStandard;

    /**
     * Configuració inicial de la classe, instanciant totes les classes necessàries per simular la
     * resposta del servidor a totes les proves.
     */
    @BeforeEach
    void setUp() throws Exception {
        mockHttpClient = mock(HttpClient.class);
        loginClient = new LoginClient(mockHttpClient);
        mockResponse = mock(HttpResponse.class);
        responseBodyAdmin = CipherUtil.encrypt("{\"token\": \"testToken\", \"funcionalId\": \"ADM\", \"usuariId\": \"testUserId\"}");
        responseBodyStandard = CipherUtil.encrypt("{\"token\": \"testToken\", \"funcionalId\": \"USR\", \"usuariId\": \"testUserId\"}");
    }

    /**
     * Prova del login amb credencials d'administrador vàlides.
     * Simula una resposta del servidor amb èxit (200) i comprova que el resultat del login,
     * el token JWT i el funcional ID siguin correctes.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    void testAdminLoginSuccessfull() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBodyAdmin);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);

        assertThat(result).isEqualTo(EXPECTED_LOGIN_MESSAGE);
        assertThat(loginClient.getJwtToken()).isEqualTo("testToken");
        assertThat(loginClient.getFuncionalID()).isEqualTo("ADM");
    }

    /**
     * Prova del login amb credencials d'usuari estàndard vàlides.
     * Simula una resposta del servidor amb èxit (200) i comprova que el resultat del login,
     * el token JWT i el funcional ID siguin correctes.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    void testStandardLoginSuccessfull() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBodyStandard);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String encriptedName = CipherUtil.encrypt(VALID_STANDARD_USERNAME);
        String Base64Name = Base64.getEncoder().encodeToString(encriptedName.getBytes());

        String contrasenya = VALID_STANDARD_PASSWORD + "|" + Instant.now();
        String encryptedPassword = CipherUtil.encrypt(contrasenya);
        String Base64Password = Base64.getEncoder().encodeToString(encryptedPassword.getBytes());

        String result = loginClient.loginUsuari(Base64Name, Base64Password);

        assertThat(result).isEqualTo(EXPECTED_LOGIN_MESSAGE);
        assertThat(loginClient.getJwtToken()).isEqualTo("testToken");
        assertThat(loginClient.getFuncionalID()).isEqualTo("USR");
    }

    /**
     * Prova del login amb credencials incorrectes.
     * Simula una resposta del servidor amb codi 401 i comprova que el missatge de retorn
     * sigui de credencials incorrectes i que no es generi cap token JWT.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void incorrectCredentialsTest() throws Exception {
        when(mockResponse.statusCode()).thenReturn(401);
        when(mockResponse.body()).thenReturn(responseBodyAdmin);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String encriptedName = CipherUtil.encrypt(INVALID_USERNAME);
        String Base64Name = Base64.getEncoder().encodeToString(encriptedName.getBytes());

        String contrasenya = INVALID_PASSWORD + "|" + Instant.now();
        String encryptedPassword = CipherUtil.encrypt(contrasenya);
        String Base64Password = Base64.getEncoder().encodeToString(encryptedPassword.getBytes());

        String result = loginClient.loginUsuari(Base64Name, Base64Password);

        assertThat(result).isEqualTo(INCORRECT_CREDENTIAL_MESSAGE);
        assertNull(loginClient.getJwtToken());
    }

    /**
     * Prova del login quan hi ha un error al servidor.
     * Simula una resposta del servidor amb codi 500 i comprova que el missatge de retorn
     * sigui d'error en el servidor i que no es generi cap token JWT.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void testErrorServer() throws Exception {
        when(mockResponse.statusCode()).thenReturn(500);
        when(mockResponse.body()).thenReturn(responseBodyAdmin);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);

        assertThat(result).isEqualTo(SERVER_ERROR);
        assertNull(loginClient.getJwtToken());
    }

    /**
     * Prova del login quan l'accés és denegat.
     * Simula una resposta del servidor amb codi 403 i comprova que el missatge de retorn
     * sigui d'accés denegat i que no es generi cap token JWT.
     *
     * @throws IOException si ocorre un error d'entrada/sortida
     * @throws InterruptedException si el fil es veu interromput
     */
    @Test
    public void testAccesDenied() throws Exception {
        when(mockResponse.statusCode()).thenReturn(403);
        when(mockResponse.body()).thenReturn(responseBodyAdmin);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String encriptedName = CipherUtil.encrypt(VALID_STANDARD_USERNAME);
        String Base64Name = Base64.getEncoder().encodeToString(encriptedName.getBytes());

        String contrasenya = VALID_STANDARD_PASSWORD + "|" + Instant.now();
        String encryptedPassword = CipherUtil.encrypt(contrasenya);
        String Base64Password = Base64.getEncoder().encodeToString(encryptedPassword.getBytes());

        String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);

        assertThat(result).isEqualTo(ACCES_DENIED);
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
            String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
            assertTrue(result.contains(SIMULATED_EXCEPTION));
            assertNull(loginClient.getJwtToken());
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            String result = loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
            assertTrue(result.contains(SIMULATED_EXCEPTION));
            assertNull(loginClient.getJwtToken());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}