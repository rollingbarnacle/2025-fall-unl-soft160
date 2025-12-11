package edu.unl.cse.soft160.xx.burnplan.evaluator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CheckWeather {
    private final double MAX_RAIN_PROBABILITY = 0.50;
    private final double MAX_RAIN_AMOUNT = 10.0;
    private CheckLand checkLand;

    public CheckWeather(CheckLand checkLand) {
        this.checkLand = checkLand;
    }

    public BurnDetermination checkForecast(
            double rainProbability,
            double rainAmount,
            LocalDate burnDate,
            LocalDate coldFront,
            double priorDayRainProbability) {

        LandTypes land = checkLand.getLand();

        if (land == LandTypes.HEAVY && priorDayRainProbability > MAX_RAIN_PROBABILITY) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (rainProbability > MAX_RAIN_PROBABILITY || rainAmount > MAX_RAIN_AMOUNT) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        long hoursToColdFront = ChronoUnit.HOURS.between(
                burnDate.atTime(16, 0),
                coldFront.atStartOfDay()
        );

        if (hoursToColdFront < 12) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        return BurnDetermination.ACCEPTABLE;
    }
}