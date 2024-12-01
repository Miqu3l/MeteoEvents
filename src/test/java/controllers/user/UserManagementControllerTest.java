package controllers.user;

import model.model.User;
import model.crud.CrudUser;
import model.login.LoginClient;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per a la gestió d'usuaris.
 * Aquesta classe inclou proves unitàries per verificar les funcionalitats de creació,
 * actualització, eliminació i llistat d'usuaris.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserManagementControllerTest {

    private static final String VALID_ADMIN_USERNAME = "admin";
    private static final String VALID_ADMIN_PASSWORD = "admin24";
    private CrudUser crudUser;
    private LoginClient loginClient;
    private List<User> list;

    private User userTest = new User(
            new java.util.Date(),"Sonia R", "ADM", "Sonia",
            "Sonia","Femení", "El Vendrell", "sonia@exemple.com",
            222334455, "Sonia 1");

    /**
     * Configuració inicial per a les proves.
     * Es crea una instància del client de login i es realitza la connexió amb credencials vàlides.
     * També es configura un objecte CRUD per accedir a les funcionalitats dels usuaris
     * i es carrega la llista inicial d'usuaris.
     *
     * @throws Exception si hi ha errors durant la configuració
     */
    @BeforeEach
    void setUp() throws Exception {
        loginClient = new LoginClient();
        loginClient.loginUsuari(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD);
        crudUser = new CrudUser();
        list = crudUser.getAllUsers();
    }

    /**
     * Prova per a la creació d'un usuari amb èxit.
     * Aquesta prova verifica que es pot crear un nou usuari de manera satisfactòria
     * i que el sistema retorna el missatge correcte de confirmació.
     *
     * @throws Exception si hi ha algun error durant la creació de l'usuari
     */
    @Test
    @Order(1)
    void testCreateUserSuccessfully() throws Exception {
        String createUserResponse = crudUser.createUser(userTest);

        assertEquals("Usuari creat correctament", createUserResponse);
    }

    /**
     * Prova per a l'actualització d'un usuari.
     * Verifica que les dades de l'usuari es modifiquen correctament.
     */
    @Order(2)
    @Test
    void testUpdateUserSuccessfully() throws Exception {
        userTest.setPoblacio("Barcelona");

        for (User u : list) {
            if (u.getNom_c().equals(userTest.getNom_c())) {
                userTest.setID(u.getID());
            }
        }

        crudUser.updateUser(userTest);

        list = crudUser.getAllUsers();
        for (User u : list) {
            if (u.getNom_c().equals(userTest.getNom_c())) {
                String poblacio = u.getPoblacio();
                assertEquals("Barcelona", poblacio);
            }
        }
    }

    /**
     * Prova per a l'eliminació d'un usuari.
     * Verifica que l'usuari esborri correctament de la base de dades.
     */
    @Order(3)
    @Test
    void testDeleteUserSuccessfully() throws Exception {
        for (User u : list) {
            if (u.getNom_c().equals(userTest.getNom_c())) {
                userTest.setID(u.getID());
            }
        }
        String deleteResponse = crudUser.deleteUser(userTest.getID());

        assertEquals("Usuari esborrat correctament", deleteResponse);
    }

    /**
     * Prova per a la gestió d'errors quan no hi ha usuari seleccionat.
     * Simula una situació on no s'ha configurat l'usuari i es comprova el missatge d'error.
     */
    @Test
    @Order(4)
    void testDeleteUserWithNoUserSelected() throws Exception {
        String noUserResponse = crudUser.deleteUser(null);
        assertEquals("Identificador incorrecte", noUserResponse);
    }

    /**
     * Prova per a la gestió d'errors quan l'identificador és erroni.
     * Simula una situació on l'usuari és incorrecte i es comprova el missatge d'error.
     */
    @Order(5)
    @Test
    void testDeleteUserWithIncorrectId() throws Exception {
        String noUserResponse = crudUser.deleteUser("");
        assertEquals("Identificador incorrecte", noUserResponse);
    }

    /**
     * Prova per la comprovació de la càrrega dels usuaris de la base de dades.
     * Simula una petició de llistat de dades.
     */
    @Test
    @Order(6)
    void testListUsersSuccessfully() throws Exception {
        list = crudUser.getAllUsers();
        assertNotNull(list);
    }
}