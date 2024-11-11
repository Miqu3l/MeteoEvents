package utilities;

/**
 * Classe que conté les URL per a les peticions a l'API.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class URLRequests {

    //LOGIN - LOGOUT

    /**
     * URL per a la petició d'inici de sessió (login).
     * Envia les credencials de l'usuari al servidor per autenticar-se.
     */
    public static final String LOGIN_URL = "http://localhost:8080/api/usuaris/login";

    /**
     * URL per a la petició de tancament de sessió (logout).
     * Envia una sol·licitud al servidor per tancar la sessió de l'usuari.
     */
    public static final String LOGOUT_URL = "http://localhost:8080/api/usuaris/logout";

    //USERS

    /**
     * URL per a la petició de llistar usuaris.
     * Envia una sol·licitud al servidor perquè retorni una llista amb tots els usuaris.
     */
    public static final String USER_LIST_URL = "http://localhost:8080/api/usuaris";

    /**
     * URL per a la petició de creació d'usuaris.
     * Envia una sol·licitud al servidor amb les dades del nou usuari.
     */
    public static final String USER_CREATE_URL = "http://localhost:8080/api/usuaris";

    /**
     * URL per a la petició de recuparar un usuari pel seu identificador.
     * Envia una sol·licitud al servidor perquè retorni un usuari.
     */
    public static final String USER_ID_URL = "http://localhost:8080/api/usuaris/{id}";

}
