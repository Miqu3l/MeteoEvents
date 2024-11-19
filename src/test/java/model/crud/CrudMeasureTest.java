package model.crud;

import model.Measure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de proves per validar les operacions CRUD (crear, llegir, actualitzar, esborrar)
 * de la classe CrudMeasure. Aquesta classe utilitza Mockito per fer mocks de la classe CrudMeasure
 * i evitar interaccions amb una base de dades real durant les proves.
 */
public class CrudMeasureTest {

    /** Instància de CrudMeasure que utilitzarem per a les proves. */
    private CrudMeasure crudMeasure;

    /** Mesura de prova per simular operacions CRUD. */
    private Measure testMeasure;

    /** Versió mock de CrudMeasure per a simular les respostes del servidor. */
    private CrudMeasure mockedCrudMeasure;

    /**
     * Inicialització abans de cada prova.
     * Aquí es crea l'objecte de la classe CrudMeasure mockejada i una mesura de prova.
     */
    @BeforeEach
    public void setUp() {
        mockedCrudMeasure = mock(CrudMeasure.class);

        testMeasure = new Measure(
                "1", // ID de la mesura
                "Calor extrem", // Nom de la mesura
                35.0, // Valor de la mesura
                "Celsius", // Unitat de mesura
                "Suspensió del esdeveniment" // Descripció
        );

        crudMeasure = mockedCrudMeasure;
    }

    /**
     * Prova per verificar la creació d'una mesura.
     * S'utilitza Mockito per simular la resposta de la creació d'una mesura.
     */
    @Test
    public void testCreateMeasureSuccessfully() {
        try {
            when(mockedCrudMeasure.createMeasure(testMeasure)).thenReturn("Mesura creada correctament");

            String result = crudMeasure.createMeasure(testMeasure);

            assertEquals("Mesura creada correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al crear mesura: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació de totes les mesures.
     * S'utilitza Mockito per simular la resposta d'una llista de mesures.
     */
    @Test
    public void testGetAllMeasures() {
        try {
            when(mockedCrudMeasure.getAllMeasures()).thenReturn(List.of(testMeasure));

            List<Measure> measures = crudMeasure.getAllMeasures();

            assertNotNull(measures, "La llista de mesures no hauria de ser null");
            assertTrue(measures.size() > 0, "La llista de mesures hauria de contenir elements");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir totes les mesures: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació d'una mesura per la seva ID.
     * S'utilitza Mockito per simular la resposta d'una mesura específica.
     */
    @Test
    public void testGetMeasureById() {
        try {
            when(mockedCrudMeasure.getMeasureById(testMeasure.getId())).thenReturn(testMeasure);

            Measure measure = crudMeasure.getMeasureById(testMeasure.getId());

            assertNotNull(measure, "La mesura obtinguda no hauria de ser null");
            assertEquals(testMeasure.getId(), measure.getId(), "L'ID de la mesura no coincideix");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir mesura per ID: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'actualització d'una mesura existent.
     * S'utilitza Mockito per simular la resposta d'actualització de la mesura.
     */
    @Test
    public void testUpdateMeasureSuccessfully() {
        try {
            testMeasure.setValor(30.0);

            when(mockedCrudMeasure.updateMeasure(testMeasure)).thenReturn("Mesura actualitzada correctament");

            String result = crudMeasure.updateMeasure(testMeasure);

            assertEquals("Mesura actualitzada correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al actualitzar mesura: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'eliminació d'una mesura.
     * S'utilitza Mockito per simular la resposta d'eliminació de la mesura.
     */
    @Test
    public void testDeleteMeasureSuccessfully() {
        try {
            when(mockedCrudMeasure.deleteMeasure(testMeasure.getId())).thenReturn("Mesura esborrada correctament");

            String result = crudMeasure.deleteMeasure(testMeasure.getId());

            assertEquals("Mesura esborrada correctament", result);

            when(mockedCrudMeasure.getMeasureById(testMeasure.getId())).thenReturn(null);

            Measure deletedMeasure = crudMeasure.getMeasureById(testMeasure.getId());
            assertNull(deletedMeasure, "La mesura hauria de ser null després de ser esborrada");
        } catch (Exception e) {
            fail("Excepció inesperada al esborrar mesura: " + e.getMessage());
        }
    }
}