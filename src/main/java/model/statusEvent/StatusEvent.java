package model.statusEvent;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe POJO que representa l'estat d'un esdeveniment.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class StatusEvent {

    /**
     * Llista d'usuaris participants en l'esdeveniment.
     */
    @JsonProperty("Usuaris participants")
    private List<String> usuarisParticipants;

    /**
     * Mapa que conté dades horàries, identificades per una clau.
     */
    private Map<String, Object> hourlyData = new HashMap<>();

    /**
     * Retorna la llista d'usuaris participants en l'esdeveniment.
     *
     * @return Llista d'usuaris participants.
     */
    public List<String> getUsuarisParticipants() {
        return usuarisParticipants;
    }

    /**
     * Estableix la llista d'usuaris participants en l'esdeveniment.
     *
     * @param usuarisParticipants Llista d'usuaris participants.
     */
    public void setUsuarisParticipants(List<String> usuarisParticipants) {
        this.usuarisParticipants = usuarisParticipants;
    }

    /**
     * Retorna el mapa amb les dades horàries.
     *
     * @return Mapa de dades horàries.
     */
    public Map<String, Object> getHourlyData() {
        return hourlyData;
    }

    /**
     * Afegeix una dada horària al mapa de dades.
     *
     * @param key  La clau que identifica la dada.
     * @param data L'objecte que representa la dada.
     */
    @JsonAnySetter
    public void addHourlyData(String key, Object data) {
        this.hourlyData.put(key, data);
    }

    /**
     * Classe interna que representa les dades horàries específiques de la previsió meteorològica demanada.
     */
    public static class HourlyData {

        /**
         * Velocitat mitjana del vent.
         */
        @JsonProperty("VelocitatMitjaVent")
        private int velocitatMitjaVent;

        /**
         * Ratxa màxima del vent registrada (en km/h).
         */
        @JsonProperty("RatxaMaximaVent")
        private int ratxaMaximaVent;

        /**
         * Temperatura registrada (en graus Celsius).
         */
        @JsonProperty("Temperatura")
        private int temperatura;

        /**
         * Probabilitat de pluja (percentatge).
         */
        @JsonProperty("ProbabilitatPluja")
        private int probabilitatPluja;

        /**
         * Humitat relativa (percentatge).
         */
        @JsonProperty("HumitatRelativa")
        private int humitatRelativa;

        /**
         * Retorna la velocitat mitjana del vent (en km/h).
         *
         * @return Velocitat mitjana del vent.
         */
        public int getVelocitatMitjaVent() {
            return velocitatMitjaVent;
        }

        /**
         * Estableix la velocitat mitjana del vent (en km/h).
         *
         * @param velocitatMitjaVent Velocitat mitjana del vent.
         */
        public void setVelocitatMitjaVent(int velocitatMitjaVent) {
            this.velocitatMitjaVent = velocitatMitjaVent;
        }

        /**
         * Retorna la ratxa màxima del vent registrada (en km/h).
         *
         * @return Ratxa màxima del vent.
         */
        public int getRatxaMaximaVent() {
            return ratxaMaximaVent;
        }

        /**
         * Estableix la ratxa màxima del vent registrada (en km/h).
         *
         * @param ratxaMaximaVent Ratxa màxima del vent.
         */
        public void setRatxaMaximaVent(int ratxaMaximaVent) {
            this.ratxaMaximaVent = ratxaMaximaVent;
        }

        /**
         * Retorna la temperatura registrada (en graus Celsius).
         *
         * @return Temperatura.
         */
        public int getTemperatura() {
            return temperatura;
        }

        /**
         * Estableix la temperatura registrada (en graus Celsius).
         *
         * @param temperatura Temperatura.
         */
        public void setTemperatura(int temperatura) {
            this.temperatura = temperatura;
        }

        /**
         * Retorna la probabilitat de pluja (en percentatge).
         *
         * @return Probabilitat de pluja.
         */
        public int getProbabilitatPluja() {
            return probabilitatPluja;
        }

        /**
         * Estableix la probabilitat de pluja (en percentatge).
         *
         * @param probabilitatPluja Probabilitat de pluja.
         */
        public void setProbabilitatPluja(int probabilitatPluja) {
            this.probabilitatPluja = probabilitatPluja;
        }

        /**
         * Retorna la humitat relativa (en percentatge).
         *
         * @return Humitat relativa.
         */
        public int getHumitatRelativa() {
            return humitatRelativa;
        }

        /**
         * Estableix la humitat relativa (en percentatge).
         *
         * @param humitatRelativa Humitat relativa.
         */
        public void setHumitatRelativa(int humitatRelativa) {
            this.humitatRelativa = humitatRelativa;
        }
    }
}