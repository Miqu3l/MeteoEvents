package utilities;

/**
 * Classe que conté, mitjançant constants, els camins (paths) de les vistes FXML
 * utilitzades en l'aplicació.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */

public class PathsViews {

    /**
     * Ruta a la vista de login (login-view.fxml).
     * Vista utilitzada per a la pantalla d'inici de sessió dels usuaris.
     */
    public static final String LOGIN_VIEW = "/org/meteoevents/meteoevents/login/login-view.fxml";

    /**
     * Ruta a la vista principal dels usuaris administradors (principal-view.fxml).
     * Vista utilitzada pels usuaris administradors de l'aplicació.
     */
    public static final String PRINCIPAL_VIEW = "/org/meteoevents/meteoevents/principal/principal-view.fxml";

    /**
     * Ruta a la vista principal dels usuaris estàndard (principal-standard-view.fxml).
     * Vista utilitzada pels usuaris estàndards de l'aplicació.
     */
    public static final String PRINCIPAL_STANDARD_VIEW = "/org/meteoevents/meteoevents/principal/principal-standard-view.fxml";

    /**
     * Ruta a la vista de gestió dels usuaris (user-management-view.fxml).
     * Vista utilitzada per la gestió dels usuaris de la base de dades.
     */
    public static final String USER_MANAGEMENT_VIEW = "/org/meteoevents/meteoevents/user/user-management-view.fxml";

    /**
     * Ruta a la vista de gestió dels esdeveniments (event-management-view.fxml).
     * Vista utilitzada per la gestió dels esdeveniments de la base de dades.
     */
    public static final String EVENT_MANAGEMENT_VIEW = "/org/meteoevents/meteoevents/event/event-management-view.fxml";

    /**
     * Ruta a la vista de gestió de les mesures de prevenció (measure-management-view.fxml).
     * Vista utilitzada per la gestió de les mesures de prevenció de la base de dades.
     */
    public static final String MEASURE_MANAGEMENT_VIEW = "/org/meteoevents/meteoevents/measure/measure-management-view.fxml";

    /**
     * Ruta a la vista per llistar esdeveniments (event-list-view.fxml).
     * Vista utilitzada per la visualització d'un esdeveniment de la base de dades.
     */
    public static final String EVENT_LIST_VIEW = "/org/meteoevents/meteoevents/event/event-list-view.fxml";

    /**
     * Ruta a la vista per llistar les mesures de prevenció (measure-list-view.fxml).
     * Vista utilitzada per la visualització d'una mesura de prevenció de la base de dades.
     */
    public static final String MEASURE_LIST_VIEW = "/org/meteoevents/meteoevents/measure/measure-list-view.fxml";

    /**
     * Ruta a la vista per llistar usuaris (event-list-view.fxml).
     * Vista utilitzada per la visualització d'un usuari de la base de dades.
     */
    public static final String USER_LIST_VIEW = "/org/meteoevents/meteoevents/user/user-list-view.fxml";

    /**
     * Ruta a la vista de cerca d'un usuari per ser esborrat, visualitzat o modificat (user-search-view.fxml).
     * Vista utilitzada per cercar un usuari de la base de dades.
     */
    public static final String USER_SEARCH_VIEW = "/org/meteoevents/meteoevents/user/user-search-view.fxml";

    /**
     * Ruta a la vista de cerca d'una mesura de prevenció per ser esborrada, visualitzada o modificada
     * (user-search-view.fxml).
     * Vista utilitzada per cercar una mesura de prevenció de la base de dades.
     */
    public static final String MEASURE_SEARCH_VIEW = "/org/meteoevents/meteoevents/measure/measure-search-view.fxml";

    /**
     * Ruta a la vista de cerca d'un esdeveniment per ser esborrat, visualitzat o modificat (user-search-view.fxml).
     * Vista utilitzada per cercar un esdeveniment de la base de dades.
     */
    public static final String EVENT_SEARCH_VIEW = "/org/meteoevents/meteoevents/event/event-search-view.fxml";

    /**
     * Ruta a la vista de cerca d'un esdeveniment per visualitzar el seu estat (user-status-view.fxml).
     * Vista utilitzada per mostrar la informació de l'estat d'un esdeveniment de la base de dades.
     */
    public static final String EVENT_STATUS_VIEW = "/org/meteoevents/meteoevents/event/event-status-view.fxml";

    /**
     * Ruta a la vista per demanar i visualitzar una previsió meteorològica per municipi a l'Aemet.
     * Vista utilitzada per demanar la previsió meteorològica a l'Aemet.
     */
    public static final String AEMET_FORECAST_VIEW = "/org/meteoevents/meteoevents/aemet/aemet-forecast-view.fxml";
}