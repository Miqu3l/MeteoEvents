package utilities;

/**
 * Classe que conté les URL per a les peticions a l'API.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class URLRequests {

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

}
