package model.aemet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de proves unitàries per a la classe AemetRequest.
 * Aquesta classe utilitza mocks per simular les sol·licituds i respostes HTTP
 * necessàries per provar la funcionalitat de la classe AemetRequest.
 */
public class AemetRequestTest {

    /** Instància de la classe AemetRequest que es provarà. */
    private AemetRequest aemetRequest;

    /** Mock de l'objecte HttpClient per simular les sol·licituds HTTP. */
    private HttpClient mockedHttpClient;

    /** Mock de l'objecte HttpResponse per simular les respostes HTTP. */
    private HttpResponse<Object> mockedHttpResponse;

    /** Cadena JSON simulada que representa una resposta vàlida del primer endpoint. */
    private static final String MOCK_FIRST_RESPONSE = "{ \"descripcion\": \"Datos disponibles\", \"datos\": \"http://example.com/data\" }";;

    /** Cadena JSON simulada que representa una resposta vàlida del segon endpoint. */
    private static final String MOCK_SECOND_RESPONSE = "[ {"
            + "\"origen\": {"
            + "\"productor\": \"Agencia Estatal de Meteorología - AEMET. Gobierno de España\","
            + "\"web\": \"https://www.aemet.es\","
            + "\"enlace\": \"https://www.aemet.es/es/eltiempo/prediccion/municipios/horas/bisbal-del-penedes-la-id43028\","
            + "\"language\": \"es\","
            + "\"copyright\": \"© AEMET. Autorizado el uso de la información y su reproducción citando a AEMET como autora de la misma.\","
            + "\"notaLegal\": \"https://www.aemet.es/es/nota_legal\""
            + "},"
            + "\"elaborado\": \"2024-12-16T06:55:08\","
            + "\"nombre\": \"Bisbal del Penedès, La\","
            + "\"provincia\": \"Tarragona\","
            + "\"prediccion\": {"
            + "\"dia\": [ {"
            + "\"estadoCielo\": [ {"
            + "\"value\": \"11n\","
            + "\"periodo\": \"01\","
            + "\"descripcion\": \"Despejado\""
            + "} ]"
            + "} ]"
            + "}"
            + "} ]";;

    /**
     * Inicialització abans de cada prova.
     * Aquí es crea l'objecte AemetRequest amb els mocks necessaris per a HttpClient i HttpResponse.
     */
    @BeforeEach
    public void setUp() {
        mockedHttpClient = mock(HttpClient.class);
        mockedHttpResponse = mock(HttpResponse.class);
        aemetRequest = new AemetRequest();
        aemetRequest.setHttpClient(mockedHttpClient);
    }

    /**
     * Prova per verificar la resposta correcta quan l'API retorna dades vàlides en el segon endpoint.
     */
    @Test
    public void testAemetForecastRequestSuccessful() {
        try {
            HttpClient mockedHttpClient = mock(HttpClient.class);
            HttpResponse<String> mockedHttpResponseFirst = mock(HttpResponse.class);
            HttpResponse<String> mockedHttpResponseSecond = mock(HttpResponse.class);

            when(mockedHttpResponseFirst.statusCode()).thenReturn(200);
            when(mockedHttpResponseFirst.body()).thenReturn(MOCK_FIRST_RESPONSE);

            when(mockedHttpResponseSecond.statusCode()).thenReturn(200);
            when(mockedHttpResponseSecond.body()).thenReturn(MOCK_SECOND_RESPONSE);

            when(mockedHttpClient.send(any(HttpRequest.class), any()))
                    .thenAnswer(invocation -> {
                        HttpRequest request = invocation.getArgument(0);
                        if (request.uri().toString().contains("http://example.com/data")) {
                            return mockedHttpResponseSecond;
                        } else {
                            return mockedHttpResponseFirst;
                        }
                    });

            AemetRequest aemetRequest = new AemetRequest(mockedHttpClient);

            String result = aemetRequest.aemetForecastRequest("08019");

            assertNotNull(result, "La resposta no hauria de ser null");
            assertTrue(result.contains("\"origen\""), "La resposta hauria de contenir el nom del propietari");

        } catch (Exception e) {
            fail("Excepció inesperada durant la sol·licitud a AEMET: " + e.getMessage());
        }
    }


    /**
     * Prova per verificar la resposta quan el primer endpoint retorna un error.
     */
    @Test
    public void testAemetRequestFirstEndpointError() {
        try {
            when(mockedHttpResponse.statusCode()).thenReturn(400);
            when(mockedHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockedHttpResponse);

            String result = aemetRequest.aemetForecastRequest("08019");

            assertNotNull(result, "La resposta no hauria de ser null");
            assertEquals("Error en la resposta de la Aemet", result, "El resultat hauria d'indicar un error en el primer endpoint");
        } catch (Exception e) {
            fail("Excepció inesperada durant la sol·licitud a AEMET: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la resposta quan el segon endpoint retorna un error.
     */
    @Test
    public void testAemetRequestSecondEndpointError() {
        try {
            when(mockedHttpResponse.statusCode()).thenReturn(200);
            when(mockedHttpResponse.body()).thenReturn(MOCK_FIRST_RESPONSE);
            when(mockedHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockedHttpResponse);

            HttpResponse<Object> mockedSecondResponse = mock(HttpResponse.class);
            when(mockedSecondResponse.statusCode()).thenReturn(500);
            when(mockedHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockedSecondResponse);

            String result = aemetRequest.aemetForecastRequest("08019");

            assertNotNull(result, "La resposta no hauria de ser null");
            assertEquals("Error en la resposta de la Aemet", result, "El resultat hauria d'indicar un error en el segon endpoint");
        } catch (Exception e) {
            fail("Excepció inesperada durant la sol·licitud a AEMET: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la resposta quan l'API retorna un missatge d'error en el JSON.
     */
    @Test
    public void testAemetRequestInvalidTownCode() {
        try {
            String mockErrorResponse = "{ \"descripcion\": \"Error al obtener los datos\" }";
            when(mockedHttpResponse.statusCode()).thenReturn(200);
            when(mockedHttpResponse.body()).thenReturn(mockErrorResponse);
            when(mockedHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockedHttpResponse);

            String result = aemetRequest.aemetForecastRequest("00000");

            assertNotNull(result, "La resposta no hauria de ser null");
            assertEquals("El codi del municipi és incorrecte", result, "El resultat hauria d'indicar un error de codi de municipi");
        } catch (Exception e) {
            fail("Excepció inesperada durant la sol·licitud a AEMET: " + e.getMessage());
        }
    }

    /**
     * Prova que valida el comportament de l'API quan el codi del municipi és nul.
     */
    @Test
    public void testAemetRequestNullTownCode() {
        try {
            String result = aemetRequest.aemetForecastRequest(null);

            assertNotNull(result, "La resposta no hauria de ser null");
            assertEquals("El codi del municipi no pot ser nul o buit", result, "El resultat hauria d'indicar un error quan el codi és nul");
        } catch (Exception e) {
            fail("Excepció inesperada durant la prova amb codi nul: " + e.getMessage());
        }
    }

}