package controllers.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.crud.CrudEvent;
import model.login.LoginClient;
import model.model.Event;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per verificar el comportament de la funcionalitat de gestió d'estat
 * d'esdeveniments amb el controlador EventStatusController. Aquesta classe inclou proves
 * per assegurar que es poden cercar i gestionar correctament els estats dels esdeveniments
 * utilitzant diferents dades d'entrada.
 */
public class EventStatusControllerTest {

    /**
     * Nom d'usuari vàlid per a l'administrador utilitzat en les proves.
     */
    private static final String VALID_ADMIN_USERNAME = "admin";

    /**
     * Contrasenya vàlida per a l'administrador utilitzada en les proves.
     */
    private static final String VALID_ADMIN_PASSWORD = "admin24";

    /**
     * Instància del client CRUD per a esdeveniments, utilitzada per interactuar amb la base de dades
     * durant les proves.
     */
    private static CrudEvent crudEvent;

    /**
     * Client de login utilitzat per gestionar l'autenticació en les proves.
     */
    private LoginClient loginClient;

    /**
     * Esdeveniment vàlid per a utilitzar en les proves.
     * La data de l'esdeveniment és un dia posterior a la data actual per assegurar tindre una
     * previsió meteorològica correcta.
     */
    private static Event validEventTest = new Event(
            "99999",
            "Concert Extremoduro",
            "Concert de despedida.",
            "Rock Produccions",
            "Palau Sant Jordi",
            "08007",
            "Barcelona",
            15000,
            "20:00",
            "22:00",
            LocalDate.now().plusDays(1).toString());

    /**
     * Esdeveniment no vàlid per a utilitzar en les proves.
     * La data de l'esdeveniment és antiga per assegurar que no tindre una previsió meteorològica
     * correcta.
     */
    private static Event invalidEventTest = new Event(
            "88888",
            "Concert Fito",
            "Concert de la gira Teatros y auditorios.",
            "Rock Produccions",
            "Palau Sant Jordi",
            "08007",
            "Barcelona",
            15000,
            "20:00",
            "22:00",
            "2023-12-31");


    /**
     * Configuració inicial per a les proves.
     * Es crea una instància del client de login i es realitza la connexió amb credencials vàlides.
     * També es configura un objecte CRUD per accedir a les funcionalitats dels esdeveniments.
     *
     * @throws Exception si hi ha errors durant la configuració
     */
    @BeforeEach
    void setUp() throws Exception {
        loginClient = new LoginClient();
        loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
        crudEvent = new CrudEvent();
        crudEvent.createEvent(validEventTest);
        crudEvent.createEvent(invalidEventTest);
        List<Event> events = crudEvent.getAllEvents();
        for(Event event : events){
            if(event.getNom().equals(validEventTest.getNom())){
                validEventTest.setId(event.getId());
            }else if(event.getNom().equals(invalidEventTest.getNom())){
                invalidEventTest.setId(event.getId());
            }
        }
    }

    /**
     * Prova per a la cerca de l'estat d'un esdeveniment amb data que retorna una previsió
     * meteorològica vàlida.
     * Aquesta prova verifica que la base de dades retorna l'estat d'un esdeveniment correctament.
     */
    @Test
    @Order(1)
    void testSearchStatusWithValidIdAndValidEventData() throws Exception {
        String response = crudEvent.getStatusById(validEventTest.getId());

        System.out.println(response);
        assertNotNull(response);
        assertDoesNotThrow(() -> {
            new ObjectMapper().readTree(response);
        }, "La resposta no és un Json");
    }

    /**
     * Prova per a la cerca de l'estat d'un esdeveniment amb data que retorna una previsió
     * meteorològica no existent.
     * Aquesta prova verifica que la base de dades retorna un missatge amb la resposta que
     * no s'ha pogut generar el JSON.
     */
    @Test
    @Order(2)
    void testSearchStatusWithInvalidEventData() throws Exception {
        String response = crudEvent.getStatusById(invalidEventTest.getId());

        assertEquals("No s'ha pogut generar el JSON", response);
        assertNotNull(response);
    }

    /**
     * Prova per a la cerca de l'estat d'un esdeveniment introduïnt un esdeveniment inexistent.
     * Aquesta prova verifica que la base de dades retorna un missatge avisant que l'esdeveniment
     * no existeix.
     */
    @Test
    @Order(3)
    void testSearchStatusWithInvalidEventId() throws Exception {
        String response = crudEvent.getStatusById("0");

        assertEquals("Esdeveniment no trobat", response);
        assertNotNull(response);
    }

    /**
     * Prova per a la cerca de l'estat d'un esdeveniment introduïnt un identificador null.
     * Aquesta prova verifica que el mètode per verificar l'estatus de l'esdeveniment retorna un null.
     */
    @Test
    @Order(4)
    void testSearchStatusWithEmptyEventId() throws Exception {
        String response = crudEvent.getStatusById("");

        assertNull(response);
    }

    /**
     * Elimina els esdeveniments creats per la realització de les proves de la base de dades.
     *
     * @throws Exception
     */
    @AfterAll
    static void deleteEvents() throws Exception {
        crudEvent.deleteEvent(validEventTest.getId());
        crudEvent.deleteEvent(invalidEventTest.getId());
    }
}