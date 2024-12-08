package model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa una mesura amb els seus atributs associats.
 * Inclou camps per a l'identificador, condició, valor, valor de la unitat de mesura,
 * i acció. Aquesta classe utilitza anotacions de Jackson per a la serialització i
 * deserialització JSON, ignorant propietats desconegudes.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Measure {

    /** Identificador únic de la mesura */
    @JsonProperty("id")
    private String id;

    /** Condició associada a la mesura */
    @JsonProperty("condicio")
    private String condicio;

    /** Valor numèric de la mesura */
    @JsonProperty("valor")
    private double valor;

    /** Valor de la unitat de mesura */
    @JsonProperty("valorUm")
    private String valorUm;

    /** Acció associada a la mesura */
    @JsonProperty("accio")
    private String accio;

    /** Acció associada al nivell de risc de la mesura */
    @JsonProperty("nivell_mesura")
    private String nivell_mesura;

    /** Constructor per defecte */
    public Measure() {
    }

    /**
     * Constructor que inicialitza tots els camps de la mesura.
     *
     * @param id l'identificador de la mesura
     * @param condicio la condició associada a la mesura
     * @param valor el valor numèric de la mesura
     * @param valorUm la unitat de mesura
     * @param accio l'acció associada a la mesura
     */
    public Measure(String id, String condicio, double valor, String valorUm, String accio, String nivellMesura) {
        this.id = id;
        this.condicio = condicio;
        this.valor = valor;
        this.valorUm = valorUm;
        this.accio = accio;
        this.nivell_mesura = nivellMesura;
    }

    /**
     * Retorna l'identificador de la mesura.
     *
     * @return l'identificador de la mesura
     */
    public String getId() {
        return id;
    }

    /**
     * Assigna un nou identificador a la mesura.
     *
     * @param id el nou identificador de la mesura
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna la condició de la mesura.
     *
     * @return la condició de la mesura
     */
    public String getCondicio() {
        return condicio;
    }

    /**
     * Assigna una nova condició a la mesura.
     *
     * @param condicio la nova condició de la mesura
     */
    public void setCondicio(String condicio) {
        this.condicio = condicio;
    }

    /**
     * Retorna el valor de la mesura.
     *
     * @return el valor de la mesura
     */
    public double getValor() {
        return valor;
    }

    /**
     * Assigna un nou valor a la mesura.
     *
     * @param valor el nou valor de la mesura
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna la unitat de mesura.
     *
     * @return la unitat de mesura
     */
    public String getValorUm() {
        return valorUm;
    }

    /**
     * Assigna una nova unitat de mesura.
     *
     * @param valorUm la nova unitat de mesura
     */
    public void setValorUm(String valorUm) {
        this.valorUm = valorUm;
    }

    /**
     * Retorna l'acció associada a la mesura.
     *
     * @return l'acció de la mesura
     */
    public String getAccio() {
        return accio;
    }

    /**
     * Assigna una nova acció a la mesura.
     *
     * @param accio la nova acció de la mesura
     */
    public void setAccio(String accio) {
        this.accio = accio;
    }

    /**
     * Retorna el nivell d'alerta associat a la mesura.
     *
     * @return l'acció de la mesura
     */
    public String getNivell_mesura() {
        return nivell_mesura;
    }

    /**
     * Assigna el nivell d'alerta de la mesura.
     *
     * @param nivell_mesura la nova acció de la mesura
     */
    public void setNivell_mesura(String nivell_mesura) {
        this.nivell_mesura = nivell_mesura;
    }

    /**
     * Mètode toString per representar la mesura amb l'ID, condició, valor i acció.
     *
     * @return una representació en cadena de la mesura
     */
    @Override
    public String toString() {
        return "Measure{" +
                "id='" + id + '\'' +
                ", condicio='" + condicio + '\'' +
                ", valor=" + valor +
                ", valorUm='" + valorUm + '\'' +
                ", accio='" + accio + '\'' +
                ", nivell alerta='" + nivell_mesura + '\'' +
                '}';
    }
}