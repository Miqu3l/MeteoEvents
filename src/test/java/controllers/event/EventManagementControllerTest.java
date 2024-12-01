package controllers.event;

import model.model.Event;
import model.crud.CrudEvent;
import model.login.LoginClient;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per a la gestió d'esdeveniments.
 * Aquesta classe inclou proves unitàries per verificar les funcionalitats de creació,
 * actualització, eliminació i llistat d'esdeveniments.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventManagementControllerTest {

    private static final String VALID_ADMIN_USERNAME = "admin";
    private static final String VALID_ADMIN_PASSWORD = "admin24";
    private CrudEvent crudEvent;
    private LoginClient loginClient;
    private List<Event> list;

    private Event eventTest = new Event(
            "99999",
            "Concert Extremoduro",
            "Concert de despedida.",
            "Rock Produccions",
            "Palau Sant Jordi",
            "08007",
            "Barcelona",
            15000,
            "20:00 - 23:00");

    /**
     * Configuració inicial per a les proves.
     * Es crea una instància del client de login i es realitza la connexió amb credencials vàlides.
     * També es configura un objecte CRUD per accedir a les funcionalitats dels esdeveniments
     * i es carrega la llista inicial d'esdeveniments.
     *
     * @throws Exception si hi ha errors durant la configuració
     */
    @BeforeEach
    void setUp() throws Exception {
        loginClient = new LoginClient();
        loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
        crudEvent = new CrudEvent();
        list = crudEvent.getAllEvents();
    }

    /**
     * Prova per a la creació d'un esdeveniment amb èxit.
     * Aquesta prova verifica que es pot crear un nou esdeveniment de manera satisfactòria
     * i que el sistema retorna el missatge correcte de confirmació.
     */
    @Test
    @Order(1)
    void testCreateEventSuccessfully() throws Exception {
        String createEventResponse = crudEvent.createEvent(eventTest);

        assertEquals("Esdeveniment creat correctament", createEventResponse);
    }

    /**
     * Prova per a l'actualització d'un esdeveniment.
     * Verifica que les dades de l'esdeveniment es modifiquen correctament.
     */
    @Order(2)
    @Test
    void testUpdateEventSuccessfully() throws Exception {
        eventTest.setPoblacio("Girona");

        for (Event e : list) {
            if (e.getNom().equals(eventTest.getNom())) {
                eventTest.setId(e.getId());
            }
        }

        crudEvent.updateEvent(eventTest);

        list = crudEvent.getAllEvents();
        for (Event e : list) {
            if (e.getNom().equals(eventTest.getNom())) {
                String poblacio = e.getPoblacio();
                assertEquals("Girona", poblacio);
            }
        }
    }

    /**
     * Prova per a l'eliminació d'un esdeveniment.
     * Verifica que l'esdeveniment esborri correctament de la base de dades.
     */
    @Order(3)
    @Test
    void testDeleteEventSuccessfully() throws Exception {
        for (Event e : list) {
            if (e.getNom().equals(eventTest.getNom())) {
                eventTest.setId(e.getId());
            }
        }
        String deleteResponse = crudEvent.deleteEvent(eventTest.getId());

        assertEquals("Esdeveniment esborrat correctament", deleteResponse);
    }

    /**
     * Prova per a la gestió d'errors quan no hi ha esdeveniment seleccionat.
     * Simula una situació on no s'ha configurat l'esdeveniment i es comprova el missatge d'error.
     */
    @Test
    @Order(4)
    void testDeleteEventWithNoEventSelected() throws Exception {
        String noEventResponse = crudEvent.deleteEvent(null);
        assertEquals("Identificador incorrecte", noEventResponse);
    }

    /**
     * Prova per a la gestió d'errors quan l'identificador és erroni.
     * Simula una situació on l'esdeveniment és incorrecte i es comprova el missatge d'error.
     */
    @Order(5)
    @Test
    void testDeleteEventWithIncorrectId() throws Exception {
        String noEventResponse = crudEvent.deleteEvent("");
        assertEquals("Identificador incorrecte", noEventResponse);
    }

    /**
     * Prova per la comprovació de la càrrega dels esdeveniments de la base de dades.
     * Simula una petició de llistat de dades.
     */
    @Test
    @Order(6)
    void testListEventsSuccessfully() throws Exception {
        list = crudEvent.getAllEvents();
        assertNotNull(list);
    }
}
