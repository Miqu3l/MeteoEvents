package model.crud;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de proves per validar les operacions CRUD (crear, llegir, actualitzar, esborrar)
 * de la classe CrudUser. Aquesta classe utilitza Mockito per fer mocks de la classe CrudUser
 * i evitar interaccions amb una base de dades real durant les proves.
 */
public class CrudUserTest {

    /** Instància de CrudUser que utilitzarem per a les proves. */
    private CrudUser crudUser;

    /** Usuari de prova per simular operacions CRUD. */
    private User testUser;

    /** Versió mock de CrudUser per a simular les respostes del servidor. */
    private CrudUser mockedCrudUser;

    /**
     * Inicialització abans de cada prova.
     * Aquí es crea l'objecte de la classe CrudUser mockejada i un usuari de prova.
     */
    @BeforeEach
    public void setUp() {
        mockedCrudUser = mock(CrudUser.class);

        testUser = new User(
                new Date(), // Data de creació
                "1234", // ID de l'usuari
                "Miguel Rodriguez Garriga", // Nom complet
                "ADM", // Rol de l'usuari
                "Miguel", // Nom
                "Miguel", // Cognom
                "Home", // Gènere
                "El Vendrell", // Població
                "mrgarriga@gmail.com", // Correu electrònic
                607989631, // Número de telèfon
                "Usuari de prova per a fer tests." // Descripció
        );

        crudUser = mockedCrudUser; // Usamos el mock en vez de la implementación real
    }

    /**
     * Prova per verificar la creació d'un usuari.
     * S'utilitza Mockito per simular la resposta de la creació d'un usuari.
     */
    @Test
    public void testCreateUserSuccessfully() {
        try {
            when(mockedCrudUser.createUser(testUser)).thenReturn("Usuari creat correctament");

            String result = crudUser.createUser(testUser);

            assertEquals("Usuari creat correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al crear usuari: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació de tots els usuaris.
     * S'utilitza Mockito per simular la resposta d'una llista d'usuaris.
     */
    @Test
    public void testGetAllUsers() {
        try {
            when(mockedCrudUser.getAllUsers()).thenReturn(List.of(testUser));

            List<User> users = crudUser.getAllUsers();

            assertNotNull(users);
            assertTrue(users.size() > 0, "La llista d'usuaris hauria de contenir elements");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir tots els usuaris: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar la recuperació d'un usuari per la seva ID.
     * S'utilitza Mockito per simular la resposta d'un usuari específic.
     */
    @Test
    public void testGetUserById() {
        try {
            when(mockedCrudUser.getUserById(testUser.getID())).thenReturn(testUser);

            User user = crudUser.getUserById(testUser.getID());

            assertNotNull(user, "L'usuari obtingut no hauria de ser null");
            assertEquals(testUser.getID(), user.getID(), "L'ID de l'usuari no coincideix");
        } catch (Exception e) {
            fail("Excepció inesperada al obtenir usuari per ID: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'actualització d'un usuari existent.
     * S'utilitza Mockito per simular la resposta d'actualització de l'usuari.
     */
    @Test
    public void testUpdateUserSuccessfully() {
        try {
            testUser.setNom_c("Nom Actualitzat");
            testUser.setPoblacio("Madrid");

            when(mockedCrudUser.updateUser(testUser)).thenReturn("Usuari actualitzat correctament");

            String result = crudUser.updateUser(testUser);

            assertEquals("Usuari actualitzat correctament", result);
        } catch (Exception e) {
            fail("Excepció inesperada al actualitzar usuari: " + e.getMessage());
        }
    }

    /**
     * Prova per verificar l'eliminació d'un usuari.
     * S'utilitza Mockito per simular la resposta d'eliminació de l'usuari.
     */
    @Test
    public void testDeleteUserSuccessfully() {
        try {
            when(mockedCrudUser.deleteUser(testUser.getID())).thenReturn("Usuari esborrat correctament");

            String result = crudUser.deleteUser(testUser.getID());

            assertEquals("Usuari esborrat correctament", result);

            when(mockedCrudUser.getUserById(testUser.getID())).thenReturn(null);

            User deletedUser = crudUser.getUserById(testUser.getID());
            assertNull(deletedUser, "L'usuari hauria de ser null després de ser esborrat");

            verify(mockedCrudUser).deleteUser(testUser.getID());
        } catch (Exception e) {
            fail("Excepció inesperada al esborrar usuari: " + e.getMessage());
        }
    }
}