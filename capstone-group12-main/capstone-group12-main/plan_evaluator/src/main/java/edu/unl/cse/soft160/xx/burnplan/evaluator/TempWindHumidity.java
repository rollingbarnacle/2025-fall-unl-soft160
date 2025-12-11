package edu.unl.cse.soft160.xx.burnplan.evaluator;

public class TempWindHumidity {

    public BurnDetermination checkTempWindHumidity(double temp, double windSpeed, double humidity) {
        double MAX_TEMP = 80.0;
        double MAX_WIND_SPEED = 20.0;
        double MIN_HUMIDITY = .20;
        if (temp > MAX_TEMP) {
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        } else if (windSpeed > MAX_WIND_SPEED) {
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        } else if (humidity < MIN_HUMIDITY) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        } else {
            return BurnDetermination.ACCEPTABLE;
        }
    }
}
