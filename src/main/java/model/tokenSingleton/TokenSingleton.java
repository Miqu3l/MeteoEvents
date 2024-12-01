package model.tokenSingleton;

/**
 * Classe que implementa el patró Singleton per gestionar una instància única d'un token JWT.
 * Aquest patró garanteix que només hi hagi una instància de la classe, evitant la creació d'objectes duplicats
 * i facilitant l'accés centralitzat al token.
 */
public class TokenSingleton {

    /**
     * Instància única de la classe. Es crea automàticament en inicialitzar la classe.
     */
    private static TokenSingleton instance = new TokenSingleton();

    /**
     * Variable per emmagatzemar el token JWT.
     */
    private String jwtToken;

    /**
     * Variable per emmagatzemar l'identificador de l'usuari.
     */
    private String id;

    /**
     * Constructor privat per impedir la creació de noves instàncies des de fora de la classe,
     * assegurant que només hi hagi una instància única de TokenSingleton.
     */
    private TokenSingleton() {}

    /**
     * Mètode estàtic per obtenir la instància única de la classe.
     * Aquest mètode permet l'accés global a la instància.
     *
     * @return La instància única de TokenSingleton.
     */
    public static TokenSingleton getInstance() {
        return instance;
    }

    /**
     * Mètode per establir el valor del token JWT.
     *
     * @param jwtToken El token JWT a emmagatzemar.
     */
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * Mètode per obtenir el valor actual del token JWT.
     *
     * @return El token JWT emmagatzemat.
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Mètode per obtenir el valor actual de l'identificador.
     *
     * @return L'identificador emmagatzemat.
     */
    public String getId() {
        return id;
    }

    /**
     * Mètode per establir el valor de l'identificador.
     *
     * @param id El token JWT a emmagatzemar.
     */
    public void setId(String id) {
        this.id = id;
    }
}