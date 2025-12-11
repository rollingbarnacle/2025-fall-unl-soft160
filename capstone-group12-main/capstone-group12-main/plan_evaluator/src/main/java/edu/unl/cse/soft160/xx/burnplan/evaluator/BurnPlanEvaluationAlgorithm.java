package edu.unl.cse.soft160.xx.burnplan.evaluator;

// Andrew Churchich, Owen Philips, Riley, Lingenfelter, Evan Wiles, Abdel Ahmed

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class BurnPlanEvaluationAlgorithm {
    public static BurnDetermination evaluate(BurnPlan burnPlan) {

        if (burnPlan == null) {
            return BurnDetermination.INDETERMINATE;
        }

        if (!burnPlan.hasRequiredData()) {
            return BurnDetermination.INDETERMINATE;
        }

        if (burnPlan.getColdFront() != null && burnPlan.getColdFront()) {
            return BurnDetermination.BURNING_PROHIBITED;
        }

        RedFlag redFlagChecker = new RedFlag();

        double hoursToColdFront = 999.0;
        if (burnPlan.getColdFrontDate() != null && burnPlan.getBurnDate() != null) {
            LocalDate burnLocalDate = burnPlan.getBurnDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate coldFrontLocalDate = burnPlan.getColdFrontDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            double daysDifference = ChronoUnit.DAYS.between(coldFrontLocalDate, burnLocalDate);
            hoursToColdFront = (daysDifference * 24.0) - 16.0;
        }

        BurnDetermination redFlagResult = redFlagChecker.checkRedFlags(
                burnPlan.getWindSpeed(),
                burnPlan.getHumidity() * 100.0,
                burnPlan.getTemperature(),
                hoursToColdFront,
                burnPlan.getWeatherForecast()
        );

        if (redFlagResult == BurnDetermination.BURNING_PROHIBITED) {
            return BurnDetermination.BURNING_PROHIBITED;
        }


        BurnDate burnDateChecker = new BurnDate();
        LocalDate burnLocalDate = burnPlan.getBurnDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        BurnDetermination dateResult = burnDateChecker.checkDate(burnLocalDate);

        if (dateResult == BurnDetermination.NOT_RECOMMENDED_OTHER) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }



        CheckSupplies supplyChecker = new CheckSupplies();
        if (burnPlan.getSupplyAvailable() != null) {
            for (Supply s : burnPlan.getSupplyAvailable()) {
                supplyChecker.addSupply(s);
            }
        }
        BurnDetermination supplyResult = supplyChecker.checkRequiredSupplies(burnPlan.getAcres());
        if (supplyResult == BurnDetermination.NOT_RECOMMENDED_OTHER) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }


        Location location = burnPlan.getLocation();
        CheckLand landChecker = new CheckLand(
                location.getLatitude(),
                location.getLongitude(),
                burnPlan.getFuelType(),
                burnPlan.getAcres()
        );
        CheckWeather weatherChecker = new CheckWeather(landChecker);

        if (burnPlan.getBurnDate() != null) {
            LocalDate coldFrontLocalDate = null;
            if (burnPlan.getColdFrontDate() != null) {
                coldFrontLocalDate = burnPlan.getColdFrontDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } else {
                coldFrontLocalDate = burnPlan.getBurnLocalDate().plusDays(10);
            }

            BurnDetermination weatherResult = weatherChecker.checkForecast(
                    burnPlan.getRainProbability(),
                    burnPlan.getRainAmount(),
                    burnPlan.getBurnLocalDate(),
                    coldFrontLocalDate,
                    burnPlan.getPriorDayRainProbability()
            );

            if (weatherResult == BurnDetermination.NOT_RECOMMENDED_OTHER) {
                return BurnDetermination.NOT_RECOMMENDED_OTHER;
            }
        }


        TempWindHumidity twhChecker = new TempWindHumidity();
        BurnDetermination twhResult = twhChecker.checkTempWindHumidity(
                burnPlan.getTemperature(),
                burnPlan.getWindSpeed(),
                burnPlan.getHumidity()
        );

        if (twhResult != BurnDetermination.ACCEPTABLE) {
            return twhResult;
        }


        BurnDetermination envResult = EnvironmentalConditions.checkEnvironmentalConditions(
                burnPlan.getFireType(),
                burnPlan.getTemperature(),
                burnPlan.getHumidity(),
                burnPlan.getWindSpeed(),
                burnPlan.getWindDirection(),
                burnPlan.getBlacklineWidth(),
                burnPlan.getBlacklineFuelCondition(),
                burnPlan.getBurnTime()
        );

        if (envResult != BurnDetermination.ACCEPTABLE) {
            return envResult;
        }

        return BurnDetermination.DESIRED;
    }
}