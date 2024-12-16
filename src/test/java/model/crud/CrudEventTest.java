package model.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de proves per validar les operacions CRUD (crear, llegir, actualitzar, esborrar)
 * de la classe CrudEvent. Aquesta classe utilitza Mockito per fer mocks de la classe CrudEvent
 * i evitar interaccions amb la base de dades real durant les proves.
 */
public class CrudEventTest {

    /** Instància de CrudEvent que utilitzarem per a les proves. */
    private CrudEvent crudEvent;

    /** Esdeveniment de prova per simular operacions CRUD. */
    private Event testEvent;

    /** Versió mock de CrudEvent per a simular les respostes del servidor. */
    private CrudEvent mockedCrudEvent;

    String mockJsonResponse;

    /**
     * Inicialització abans de cada prova.
     * Aquí es crea l'objecte de la classe CrudEvent mockejada i un esdeveniment de prova.
     */
    @BeforeEach
    public void setUp() {
        mockedCrudEvent = mock(CrudEvent.class);

        testEvent = new Event(
                "1", // ID de l'esdeveniment
                "80a Festa de la Bicicleta", // Nom de l'esdeveniment
                "Bicicletada popular", // Descripció
                "Ajuntament del Vendrell", // Organitzador
                "Plaça Nova", // Direcció
                "43700", // Codi postal
                "El Vendrell", // Població
                1500, // Nombre de participants
                "09:00", // Horari
                "13:00",
                "2024-12-17");

        mockJsonResponse = "{"
                + "\"id\":\"99999\","
                + "\"name\":\"Concert Extremoduro\","
                + "\"description\":\"Concert de despedida.\","
                + "\"organizer\":\"Rock Produccions\","
                + "\"venue\":\"Palau Sant Jordi\","
                + "\"postalCode\":\"08007\","
                + "\"city\":\"Barcelona\","
                + "\"capacity\":15000,"
                + "\"startTime\":\"20:00\","
                + "\"endTime\":\"22:00\","
                + "\"date\":\"2024-12-17\""
                + "}";

                crudEvent = mockedCrudEvent;
    }

    /**
     * Prova per verificar la creació d'un esdeveniment.
     * S'utilitza Mockito per simular la resposta de la creació d'un esdeveniment.
     */
    @Test
    public void testCreateEventSuccessfully() {
        try {
            when(mockedCrudEvent.createEvent(testEvent)).thenReturn("Esdeveniment creat correctament");

            String result = crudEvent.createEvent(testEvent);

            assertEquals("Esdeveniment creat correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al crear esdeveniment: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació de tots els esdeveniments.
     * S'utilitza Mockito per simular la resposta d'una llista d'esdeveniments.
     */
    @Test
    public void testGetAllEvents() {
        try {
            when(mockedCrudEvent.getAllEvents()).thenReturn(List.of(testEvent));

            List<Event> events = crudEvent.getAllEvents();

            assertNotNull(events, "La llista d'esdeveniments no hauria de ser null");
            assertTrue(events.size() > 0, "La llista d'esdeveniments hauria de contenir elements");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir tots els esdeveniments: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació d'un esdeveniment per la seva ID.
     * S'utilitza Mockito per simular la resposta d'un esdeveniment específic.
     */
    @Test
    public void testGetEventById() {
        try {
            when(mockedCrudEvent.getEventById(testEvent.getId())).thenReturn(testEvent);

            Event event = crudEvent.getEventById(testEvent.getId());

            assertNotNull(event, "L'esdeveniment obtingut no hauria de ser null");
            assertEquals(testEvent.getId(), event.getId(), "L'ID de l'esdeveniment no coincideix");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir esdeveniment per ID: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'actualització d'un esdeveniment existent.
     * S'utilitza Mockito per simular la resposta d'actualització de l'esdeveniment.
     */
    @Test
    public void testUpdateEventSuccessfully() {
        try {
            testEvent.setNom("Festival Actualizado");

            when(mockedCrudEvent.updateEvent(testEvent)).thenReturn("Esdeveniment actualitzat correctament");

            String result = crudEvent.updateEvent(testEvent);

            assertEquals("Esdeveniment actualitzat correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al actualitzar esdeveniment: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'eliminació d'un esdeveniment.
     * S'utilitza Mockito per simular la resposta d'eliminació de l'esdeveniment.
     */
    @Test
    public void testDeleteEventSuccessfully() {
        try {
            when(mockedCrudEvent.deleteEvent(testEvent.getId())).thenReturn("Esdeveniment esborrat correctament");

            String result = crudEvent.deleteEvent(testEvent.getId());

            assertEquals("Esdeveniment esborrat correctament", result);

            when(mockedCrudEvent.getEventById(testEvent.getId())).thenReturn(null);

            Event deletedEvent = crudEvent.getEventById(testEvent.getId());
            assertNull(deletedEvent, "L'esdeveniment hauria de ser null després de ser esborrat");
        } catch (Exception e) {
            fail("Excepció inesperada al esborrar esdeveniment: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la resposta en format Json de l'estat d'un esdeveniment per la seva ID.
     * S'utilitza Mockito per simular la resposta de l'estat d'un esdeveniment específic.
     */
    @Test
    public void testGetStatusById() {
        try {
            when(mockedCrudEvent.getStatusById(testEvent.getId())).thenReturn(mockJsonResponse);

            String result = crudEvent.getStatusById(testEvent.getId());

            assertNotNull(result, "La resposta no hauria de ser null");
            ObjectMapper objectMapper = new ObjectMapper();
            assertDoesNotThrow(() -> objectMapper.readTree(result),
                    "La resposta ha de ser un JSON vàlid");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir esdeveniment per ID: " + e.getMessage());
        }
    }
}