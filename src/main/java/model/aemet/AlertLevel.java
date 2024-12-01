package model.aemet;

/**
 * Classe per seleccionar els nivells d'alerta meteorològica en funció de les dades declarades
 * com a aconstants.
 */
public class AlertLevel {

    // Constants per determinar els llindars de velocitat mitjana del vent
    private static final int WIND_SPEED_AVERAGE_1 = 10;
    private static final int WIND_SPEED_AVERAGE_2 = 14;
    private static final int WIND_SPEED_AVERAGE_3 = 18;
    private static final int WIND_SPEED_AVERAGE_4 = 22;

    // Constants per determinar els llindars de velocitat màxima del vent
    private static final int WIND_SPEED_MAX_1 = 18;
    private static final int WIND_SPEED_MAX_2 = 21;
    private static final int WIND_SPEED_MAX_3 = 27;
    private static final int WIND_SPEED_MAX_4 = 33;

    // Constants per determinar els llindars de quantitat de pluja
    private static final float RAIN_AMOUNT_1 = 0;
    private static final float RAIN_AMOUNT_2 = 0.5F;
    private static final float RAIN_AMOUNT_3 = 1;
    private static final float RAIN_AMOUNT_4 = 5;

    // Constants per determinar els llindars de quantitat de neu
    private static final float SNOW_AMOUNT_1 = 0;
    private static final float SNOW_AMOUNT_2 = 0.5F;
    private static final float SNOW_AMOUNT_3 = 1;
    private static final float SNOW_AMOUNT_4 = 5;

    // Constants per determinar els llindars de temperatures altes
    private static final int TEMPERATURE_HIGH_1 = 25;
    private static final int TEMPERATURE_HIGH_2 = 28;
    private static final int TEMPERATURE_HIGH_3 = 30;
    private static final int TEMPERATURE_HIGH_4 = 35;

    // Constants per determinar els llindars de temperatures baixes
    private static final int TEMPERATURE_LOW_1 = 5;
    private static final int TEMPERATURE_LOW_2 = 2;
    private static final int TEMPERATURE_LOW_3 = 0;
    private static final int TEMPERATURE_LOW_4 = -5;
    private static final int TEMPERATURE_LOW_5 = -10;

    // Atributs per emmagatzemar els valors meteorològics
    private int windAverage;
    private int windMax;
    private float rainAmount;
    private float snowAmount;
    private int temperature;

    /**
     * Constructor per defecte.
     */
    public AlertLevel() {}

    // Getters i setters

    /**
     * @return La velocitat mitjana del vent.
     */
    public int getWindAverage() {
        return windAverage;
    }

    /**
     * @param windAverage La velocitat mitjana del vent a establir.
     */
    public void setWindAverage(int windAverage) {
        this.windAverage = windAverage;
    }

    /**
     * @return La velocitat màxima del vent.
     */
    public int getWindMax() {
        return windMax;
    }

    /**
     * @param windMax La velocitat màxima del vent a establir.
     */
    public void setWindMax(int windMax) {
        this.windMax = windMax;
    }

    /**
     * @return La quantitat de pluja.
     */
    public float getRainAmount() {
        return rainAmount;
    }

    /**
     * @param rainAmount La quantitat de pluja a establir.
     */
    public void setRainAmount(float rainAmount) {
        this.rainAmount = rainAmount;
    }

    /**
     * @return La quantitat de neu.
     */
    public float getSnowAmount() {
        return snowAmount;
    }

    /**
     * @param snowAmount La quantitat de neu a establir.
     */
    public void setSnowAmount(float snowAmount) {
        this.snowAmount = snowAmount;
    }

    /**
     * @return La temperatura.
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * @param temperature La temperatura a establir.
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }



    /**
     * Determina el nivell d'alerta per a temperatures altes segons la temperatura donada.
     *
     * @param temperature La temperatura a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkHighTemperatureLevel(int temperature) {
        if (temperature > 5 && temperature <= TEMPERATURE_HIGH_1) {
            return 1;
        } else if (temperature <= TEMPERATURE_HIGH_2) {
            return 2;
        } else if (temperature <= TEMPERATURE_HIGH_3) {
            return 3;
        } else if (temperature <= TEMPERATURE_HIGH_4) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Determina el nivell d'alerta per a temperatures baixes segons la temperatura donada.
     *
     * @param temperature La temperatura a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkLowTemperatureLevel(int temperature) {
        if (temperature > TEMPERATURE_LOW_1) {
            return 1;
        } else if (temperature >= TEMPERATURE_LOW_2) {
            return 2;
        } else if (temperature >= TEMPERATURE_LOW_3) {
            return 3;
        } else if (temperature >= TEMPERATURE_LOW_4) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Determina el nivell d'alerta per a la velocitat mitjana del vent.
     *
     * @param windAverage La velocitat mitjana del vent a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkAverageWind(int windAverage) {
        if (windAverage <= WIND_SPEED_AVERAGE_1) {
            return 1;
        } else if (windAverage <= WIND_SPEED_AVERAGE_2) {
            return 2;
        } else if (windAverage <= WIND_SPEED_AVERAGE_3) {
            return 3;
        } else if (windAverage <= WIND_SPEED_AVERAGE_4) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Determina el nivell d'alerta per a la velocitat màxima del vent.
     *
     * @param maxWind La velocitat màxima del vent a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkMaxWind(int maxWind) {
        if (maxWind <= WIND_SPEED_MAX_1) {
            return 1;
        } else if (maxWind <= WIND_SPEED_MAX_2) {
            return 2;
        } else if (maxWind <= WIND_SPEED_MAX_3) {
            return 3;
        } else if (maxWind <= WIND_SPEED_MAX_4) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Determina el nivell d'alerta per a la quantitat de pluja.
     *
     * @param rainAmount La quantitat de pluja a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkRain(float rainAmount) {
        if (rainAmount == RAIN_AMOUNT_1) {
            return 1;
        } else if (rainAmount < RAIN_AMOUNT_2) {
            return 2;
        } else if (rainAmount < RAIN_AMOUNT_3) {
            return 3;
        } else if (rainAmount < RAIN_AMOUNT_4) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Determina el nivell d'alerta per a la quantitat de neu.
     *
     * @param snowAmountAmount La quantitat de neu a verificar.
     * @return El nivell d'alerta corresponent.
     */
    public int checkSnow(float snowAmountAmount) {
        if (snowAmountAmount == SNOW_AMOUNT_1) {
            return 1;
        } else if (snowAmountAmount < SNOW_AMOUNT_2) {
            return 2;
        } else if (snowAmountAmount < SNOW_AMOUNT_3) {
            return 3;
        } else if (snowAmountAmount < SNOW_AMOUNT_4) {
            return 4;
        } else {
            return 5;
        }
    }
}