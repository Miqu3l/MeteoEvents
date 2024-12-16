package controllers.measure;

import model.model.Measure;
import model.crud.CrudMeasure;
import model.login.LoginClient;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per a la gestió de mesures de prevenció.
 * Aquesta classe inclou proves unitàries per verificar les funcionalitats de creació,
 * actualització, eliminació i llistat de mesures de prevenció.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeasureManagementControllerTest {

    private static final String VALID_ADMIN_USERNAME = "admin";
    private static final String VALID_ADMIN_PASSWORD = "admin24";
    private CrudMeasure crudMeasure;
    private LoginClient loginClient;
    private List<Measure> list;

    private Measure measureTest = new Measure(
            "1",         // ID ficticio per a proves
            "Temperatura superior a 30°C",
            30.5,
            "°C",
            "Activar ventilació",
            "4");

    /**
     * Configuració inicial per a les proves.
     * Es crea una instància del client de login i es realitza la connexió amb credencials vàlides.
     * També es configura un objecte CRUD per accedir a les funcionalitats de les mesures de prevenció
     * i es carrega la llista inicial de mesures de prevenció.
     *
     * @throws Exception si hi ha errors durant la configuració
     */
    @BeforeEach
    void setUp() throws Exception {
        loginClient = new LoginClient();
        loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
        crudMeasure = new CrudMeasure();
        list = crudMeasure.getAllMeasures();
    }

    /**
     * Prova per a la creació d'una mesura amb èxit.
     * Aquesta prova verifica que es pot crear una nova mesura de manera satisfactòria
     * i que el sistema retorna el missatge correcte de confirmació.
     */
    @Test
    @Order(1)
    void testCreateMeasureSuccessfully() throws Exception {
        String createMeasureResponse = crudMeasure.createMeasure(measureTest);

        assertEquals("Mesura creada correctament", createMeasureResponse);
    }

    /**
     * Prova per a l'actualització d'una mesura.
     * Verifica que les dades de la mesura es modifiquen correctament.
     */
    @Order(2)
    @Test
    void testUpdateMeasureSuccessfully() throws Exception {
        measureTest.setCondicio("Temperatura superior a 35°C");
        measureTest.setValor(35.0);

        for (Measure m : list) {
            if (m.getCondicio().equals(measureTest.getCondicio())) {
                measureTest.setId(m.getId());
            }
        }

        crudMeasure.updateMeasure(measureTest);

        list = crudMeasure.getAllMeasures();
        for (Measure m : list) {
            if (m.getCondicio().equals(measureTest.getCondicio())) {
                double valor = m.getValor();
                assertEquals(35.0, valor);
            }
        }
    }

    /**
     * Prova per a l'eliminació d'una mesura.
     * Verifica que la mesura esborri correctament de la base de dades.
     */
    @Order(3)
    @Test
    void testDeleteMeasureSuccessfully() throws Exception {
        for (Measure m : list) {
            if (m.getCondicio().equals(measureTest.getCondicio())) {
                measureTest.setId(m.getId());
            }
        }
        String deleteResponse = crudMeasure.deleteMeasure(measureTest.getId());

        assertEquals("Mesura esborrada correctament", deleteResponse);
    }

    /**
     * Prova per a la gestió d'errors quan no hi ha mesura seleccionada.
     * Simula una situació on no s'ha configurat la mesura i es comprova el missatge d'error.
     */
    @Test
    @Order(4)
    void testDeleteMeasureWithNoMeasureSelected() throws Exception {
        String noMeasureResponse = crudMeasure.deleteMeasure(null);
        assertEquals("Identificador incorrecte", noMeasureResponse);
    }

    /**
     * Prova per a la gestió d'errors quan l'identificador és erroni.
     * Simula una situació on la mesura és incorrecta i es comprova el missatge d'error.
     */
    @Order(5)
    @Test
    void testDeleteMeasureWithIncorrectId() throws Exception {
        String noMeasureResponse = crudMeasure.deleteMeasure("");
        assertEquals("Identificador incorrecte", noMeasureResponse);
    }

    /**
     * Prova per la comprovació de la càrrega de les mesures des de la base de dades.
     * Simula una petició de llistat de dades.
     */
    @Test
    @Order(6)
    void testListMeasuresSuccessfully() throws Exception {
        list = crudMeasure.getAllMeasures();
        assertNotNull(list);
    }
}