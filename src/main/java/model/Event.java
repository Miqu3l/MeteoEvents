package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa un esdeveniment amb les seves propietats i informació.
 * Inclou camps per al nom de l'esdeveniment, identificador, descripció, organitzador,
 * direcció, codi postal, població, aforament i horari.
 * Aquesta classe utilitza anotacions de Jackson per a la serialització i
 * deserialització JSON, ignorant propietats desconegudes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    /** Identificador únic de l'esdeveniment */
    @JsonProperty("id")
    private String id;

    /** Nom de l'esdeveniment */
    @JsonProperty("nom")
    private String nom;

    /** Descripció de l'esdeveniment */
    @JsonProperty("descripcio")
    private String descripcio;

    /** Organitzador de l'esdeveniment */
    @JsonProperty("organitzador")
    private String organitzador;

    /** Direcció de l'esdeveniment */
    @JsonProperty("direccio")
    private String direccio;

    /** Codi postal de l'esdeveniment */
    @JsonProperty("codi_postal")
    private String codiPostal;

    /** Població de l'esdeveniment */
    @JsonProperty("poblacio")
    private String poblacio;

    /** Aforament màxim de l'esdeveniment */
    @JsonProperty("aforament")
    private int aforament;

    /** Horari de l'esdeveniment */
    @JsonProperty("horari")
    private String horari;

    /** Constructor per defecte */
    public Event() {
    }

    /**
     * Constructor que inicialitza tots els camps de l'esdeveniment.
     *
     * @param id l'identificador únic de l'esdeveniment
     * @param nom el nom de l'esdeveniment
     * @param descripcio la descripció de l'esdeveniment
     * @param organitzador l'organitzador de l'esdeveniment
     * @param direccio la direcció de l'esdeveniment
     * @param codiPostal el codi postal de l'esdeveniment
     * @param poblacio la població de l'esdeveniment
     * @param aforament l'aforament màxim de l'esdeveniment
     * @param horari l'horari de l'esdeveniment
     */
    public Event(String id, String nom, String descripcio, String organitzador, String direccio, String codiPostal,
                 String poblacio, int aforament, String horari) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.organitzador = organitzador;
        this.direccio = direccio;
        this.codiPostal = codiPostal;
        this.poblacio = poblacio;
        this.aforament = aforament;
        this.horari = horari;
    }

    /**
     * Obté l'identificador de l'esdeveniment.
     *
     * @return l'identificador de l'esdeveniment
     */
    public String getId() {
        return id;
    }

    /**
     * Estableix l'identificador de l'esdeveniment.
     *
     * @param id l'identificador únic de l'esdeveniment
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obté el nom de l'esdeveniment.
     *
     * @return el nom de l'esdeveniment
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de l'esdeveniment.
     *
     * @param nom el nom de l'esdeveniment
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció de l'esdeveniment.
     *
     * @return la descripció de l'esdeveniment
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció de l'esdeveniment.
     *
     * @param descripcio la descripció de l'esdeveniment
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté l'organitzador de l'esdeveniment.
     *
     * @return l'organitzador de l'esdeveniment
     */
    public String getOrganitzador() {
        return organitzador;
    }

    /**
     * Estableix l'organitzador de l'esdeveniment.
     *
     * @param organitzador l'organitzador de l'esdeveniment
     */
    public void setOrganitzador(String organitzador) {
        this.organitzador = organitzador;
    }

    /**
     * Obté la direcció de l'esdeveniment.
     *
     * @return la direcció de l'esdeveniment
     */
    public String getDireccio() {
        return direccio;
    }

    /**
     * Estableix la direcció de l'esdeveniment.
     *
     * @param direccio la direcció de l'esdeveniment
     */
    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    /**
     * Obté el codi postal de l'esdeveniment.
     *
     * @return el codi postal de l'esdeveniment
     */
    public String getCodiPostal() {
        return codiPostal;
    }

    /**
     * Estableix el codi postal de l'esdeveniment.
     *
     * @param codiPostal el codi postal de l'esdeveniment
     */
    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    /**
     * Obté la població de l'esdeveniment.
     *
     * @return la població de l'esdeveniment
     */
    public String getPoblacio() {
        return poblacio;
    }

    /**
     * Estableix la població de l'esdeveniment.
     *
     * @param poblacio la població de l'esdeveniment
     */
    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    /**
     * Obté l'aforament màxim de l'esdeveniment.
     *
     * @return l'aforament màxim de l'esdeveniment
     */
    public int getAforament() {
        return aforament;
    }

    /**
     * Estableix l'aforament màxim de l'esdeveniment.
     *
     * @param aforament l'aforament màxim de l'esdeveniment
     */
    public void setAforament(int aforament) {
        this.aforament = aforament;
    }

    /**
     * Obté l'horari de l'esdeveniment.
     *
     * @return l'horari de l'esdeveniment
     */
    public String getHorari() {
        return horari;
    }

    /**
     * Estableix l'horari de l'esdeveniment.
     *
     * @param horari l'horari de l'esdeveniment
     */
    public void setHorari(String horari) {
        this.horari = horari;
    }


    /**
     * Mètode toString per representar l'esdeveniment amb l'ID, nom i organitzador.
     *
     * @return una representació en cadena de l'esdeveniment
     */
    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", organitzador='" + organitzador + '\'' +
                '}';
    }
}
