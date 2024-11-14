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

    /**
     * URL per a la petició de creació d'usuaris.
     * Envia una sol·licitud al servidor amb les dades del nou usuari.
     */
    public static final String USER_DELETE_URL = "http://localhost:8080/api/usuaris/{id}";

    /**
     * URL per a la petició de creació d'usuaris.
     * Envia una sol·licitud al servidor amb les dades del nou usuari.
     */
    public static final String USER_UPDATE_URL = "http://localhost:8080/api/usuaris/{id}";

    //EVENTS

    /**
     * URL per a la petició de llistar esdeveniments.
     * Envia una sol·licitud al servidor perquè retorni una llista amb tots els esdeveniments.
     */
    public static final String EVENT_LIST_URL = "http://localhost:8080/api/esdeveniments";

    /**
     * URL per a la petició de creació d'esdeveniments.
     * Envia una sol·licitud al servidor amb les dades del nou esdeveniment.
     */
    public static final String EVENT_CREATE_URL = "http://localhost:8080/api/esdeveniments";

    /**
     * URL per a la petició de recuparar un esdeveniment pel seu identificador.
     * Envia una sol·licitud al servidor perquè retorni un esdeveniment.
     */
    public static final String EVENT_ID_URL = "http://localhost:8080/api/esdeveniments/{id}";

    /**
     * URL per a la petició d'esborrar un esdeveniment pel seu identificador.
     * Envia una sol·licitud al servidor perquè elimini un esdeveniment.
     */
    public static final String EVENT_DELETE_URL = "http://localhost:8080/api/esdeveniments/{id}";

    /**
     * URL per a la petició de modificar un esdeveniment pel seu identificador.
     * Envia una sol·licitud al servidor perquè modifiqui un esdeveniment.
     */
    public static final String EVENT_UPDATE_URL = "http://localhost:8080/api/esdeveniments/{id}";

    //MEASURES

    /**
     * URL per a la petició de llistar mesures de prevenció.
     * Envia una sol·licitud al servidor perquè retorni una llista amb totes les mesures de prevenció.
     */
    public static final String MEASURE_LIST_URL = "http://localhost:8080/api/mesures";

    /**
     * URL per a la petició de creació d'una mesura de prevenció.
     * Envia una sol·licitud al servidor amb les dades de la nova mesura de prevenció.
     */
    public static final String MEASURE_CREATE_URL = "http://localhost:8080/api/mesures";

    /**
     * URL per a la petició de recuparar una mesura de prevenció pel seu identificador.
     * Envia una sol·licitud al servidor perquè retorni una mesura de prevenció.
     */
    public static final String MEASURE_ID_URL = "http://localhost:8080/api/mesures/{id}";

    /**
     * URL per a la petició d'esborrar una mesura de prevenció pel seu identificador.
     * Envia una sol·licitud al servidor perquè elimini una mesura de prevenció.
     */
    public static final String MEASURE_DELETE_URL = "http://localhost:8080/api/mesures/{id}";

    /**
     * URL per a la petició de modificar una mesura de prevenció pel seu identificador.
     * Envia una sol·licitud al servidor perquè modifiqui una mesura de prevenció.
     */
    public static final String MEASURE_UPDATE_URL = "http://localhost:8080/api/mesures/{id}";

    /**
     * URL per obtenir els esdeveniments assignats a una mesura de seguretat.
     * Envia una sol·licitud al servidor perquè retorni els esdeveniments on està implementada una mesura de prevenció.
     */
    public static final String MEASURE_EVENT_UPDATE_URL = "http://localhost:8080/api/mesures/{id}/esdeveniments";

}