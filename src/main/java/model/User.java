package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * Classe que representa un usuari amb les seves propietats i informació personal.
 * Inclou camps per al nom d'usuari, identificador, informació de connexió, sexe,
 * data de naixement, població, correu electrònic, telèfon i una descripció.
 * Aquesta classe utilitza anotacions de Jackson per a la serialització i
 * deserialització JSON, ignorant propietats desconegudes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    /** Nom d'usuari */
    @JsonProperty("nomUsuari")
    private String nom_usuari;

    /** Identificador únic de l'usuari */
    @JsonProperty("id")
    private String id;

    /** Nom complet de l'usuari */
    @JsonProperty("nom_c")
    private String nom_c;

    /** Identificador funcional de l'usuari */
    @JsonProperty("funcional_id")
    private String funcional_id;

    /** Contrasenya de l'usuari */
    @JsonProperty("contrasenya")
    private String contrasenya;

    /** Data de l'última connexió de l'usuari */
    @JsonProperty("ultima_connexio")
    private Date ultima_connexio;

    /** Data de naixement de l'usuari */
    @JsonProperty("data_naixement")
    private Date data_naixement;

    /** Sexe de l'usuari */
    @JsonProperty("sexe")
    private String sexe;

    /** Població on resideix l'usuari */
    @JsonProperty("poblacio")
    private String poblacio;

    /** Correu electrònic de l'usuari */
    @JsonProperty("email")
    private String email;

    /** Telèfon de contacte de l'usuari */
    @JsonProperty("telefon")
    private int telefon;

    /** Descripció personal de l'usuari */
    @JsonProperty("descripcio")
    private String descripcio;

    /** Constructor per defecte */
    public User() {
    }

    /**
     * Constructor que inicialitza tots els camps de l'usuari.
     *
     * @param data_naixement la data de naixement de l'usuari
     * @param id l'identificador únic de l'usuari
     * @param nom_c el nom complet de l'usuari
     * @param funcional_id l'identificador funcional de l'usuari
     * @param nom_usuari el nom d'usuari
     * @param contrasenya la contrasenya de l'usuari
     * @param sexe el sexe de l'usuari
     * @param poblacio la població on resideix l'usuari
     * @param email el correu electrònic de l'usuari
     * @param telefon el telèfon de contacte de l'usuari
     * @param descripcio una descripció personal de l'usuari
     */
    public User(Date data_naixement, String id, String nom_c, String funcional_id, String nom_usuari,
                String contrasenya, String sexe, String poblacio, String email, int telefon, String descripcio) {

        this.data_naixement = data_naixement;
        this.id = id;
        this.nom_c = nom_c;
        this.funcional_id = funcional_id;
        this.nom_usuari = nom_usuari;
        this.contrasenya = contrasenya;
        this.sexe = sexe;
        this.poblacio = poblacio;
        this.email = email;
        this.telefon = telefon;
        this.descripcio = descripcio;
    }

    /**
     * Constructor que inicialitza tots els camps de l'usuari.
     *
     * @param data_naixement la data de naixement de l'usuari
     * @param nom_c el nom complet de l'usuari
     * @param funcional_id l'identificador funcional de l'usuari
     * @param nom_usuari el nom d'usuari
     * @param contrasenya la contrasenya de l'usuari
     * @param sexe el sexe de l'usuari
     * @param poblacio la població on resideix l'usuari
     * @param email el correu electrònic de l'usuari
     * @param telefon el telèfon de contacte de l'usuari
     * @param descripcio una descripció personal de l'usuari
     */
    public User(Date data_naixement, String nom_c, String funcional_id, String nom_usuari,
                String contrasenya, String sexe, String poblacio, String email, int telefon, String descripcio) {

        this.data_naixement = data_naixement;
        this.nom_c = nom_c;
        this.funcional_id = funcional_id;
        this.nom_usuari = nom_usuari;
        this.contrasenya = contrasenya;
        this.sexe = sexe;
        this.poblacio = poblacio;
        this.email = email;
        this.telefon = telefon;
        this.descripcio = descripcio;
    }

    /**
     * Retorna l'identificador únic de l'usuari.
     *
     * @return l'identificador de l'usuari
     */
    public String getID() {
        return id;
    }

    /**
     * Assigna un valor a l'identificador de l'usuari.
     *
     * @param id el nou identificador de l'usuari
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Retorna el nom complet de l'usuari.
     *
     * @return el nom complet de l'usuari
     */
    public String getNom_c() {
        return nom_c;
    }

    /**
     * Assigna un nou nom complet a l'usuari.
     *
     * @param nom_c el nou nom complet de l'usuari
     */
    public void setNom_c(String nom_c) {
        this.nom_c = nom_c;
    }

    /**
     * Retorna l'identificador funcional de l'usuari.
     *
     * @return l'identificador funcional de l'usuari
     */
    public String getFuncional_id() {
        return funcional_id;
    }

    /**
     * Assigna un nou identificador funcional a l'usuari.
     *
     * @param funcional_id el nou identificador funcional de l'usuari
     */
    public void setFuncional_id(String funcional_id) {
        this.funcional_id = funcional_id;
    }

    /**
     * Retorna el nom d'usuari.
     *
     * @return el nom d'usuari
     */
    public String getNom_usuari() {
        return nom_usuari;
    }

    /**
     * Assigna un nou nom d'usuari.
     *
     * @param nom_usuari el nou nom d'usuari
     */
    public void setNom_usuari(String nom_usuari) {
        this.nom_usuari = nom_usuari;
    }

    /**
     * Retorna la contrasenya de l'usuari.
     *
     * @return la contrasenya de l'usuari
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Assigna una nova contrasenya a l'usuari.
     *
     * @param contrasenya la nova contrasenya de l'usuari
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Retorna la data de l'última connexió de l'usuari.
     *
     * @return la data de l'última connexió
     */
    public Date getUltima_connexio() {
        return ultima_connexio;
    }

    /**
     * Assigna la data de l'última connexió de l'usuari.
     *
     * @param ultima_connexio la nova data de connexió
     */
    public void setUltima_connexio(Date ultima_connexio) {
        this.ultima_connexio = ultima_connexio;
    }

    /**
     * Retorna la data de naixement de l'usuari.
     *
     * @return la data de naixement
     */
    public Date getData_naixement() {
        return data_naixement;
    }

    /**
     * Assigna una nova data de naixement a l'usuari.
     *
     * @param data_naixement la nova data de naixement
     */
    public void setData_naixement(Date data_naixement) {
        this.data_naixement = data_naixement;
    }

    /**
     * Retorna el sexe de l'usuari.
     *
     * @return el sexe de l'usuari
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * Assigna un nou sexe a l'usuari.
     *
     * @param sexe el nou sexe de l'usuari
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * Retorna la població de l'usuari.
     *
     * @return la població de l'usuari
     */
    public String getPoblacio() {
        return poblacio;
    }

    /**
     * Assigna una nova població a l'usuari.
     *
     * @param poblacio la nova població de l'usuari
     */
    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    /**
     * Retorna el correu electrònic de l'usuari.
     *
     * @return el correu electrònic de l'usuari
     */
    public String getEmail() {
        return email;
    }

    /**
     * Assigna un nou correu electrònic a l'usuari.
     *
     * @param email el nou correu electrònic
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna el telèfon de l'usuari.
     *
     * @return el telèfon de l'usuari
     */
    public int getTelefon() {
        return telefon;
    }

    /**
     * Assigna un nou número de telèfon a l'usuari.
     *
     * @param telefon el nou número de telèfon de l'usuari
     */
    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    /**
     * Retorna la descripció de l'usuari.
     *
     * @return la descripció de l'usuari
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Assigna una nova descripció a l'usuari.
     *
     * @param descripcio la nova descripció de l'usuari
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Mètode toString per representar l'usuari amb l'ID, el nom complet i
     * l'identificador funcional.
     *
     * @return una representació en cadena de l'usuari
     */
    @Override
    public String toString() {
        return "User{" +
                "ID='" + id + '\'' +
                ", nom_c='" + nom_c + '\'' +
                ", funcional_id='" + funcional_id + '\'' +
                '}';
    }
}