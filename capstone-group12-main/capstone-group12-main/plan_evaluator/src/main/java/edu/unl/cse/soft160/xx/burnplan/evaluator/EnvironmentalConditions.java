package edu.unl.cse.soft160.xx.burnplan.evaluator;

import java.time.LocalTime;

public class EnvironmentalConditions {


    public static BurnDetermination checkEnvironmentalConditions(
            FireTypes fireType,
            double temperature,
            double humidity,
            double windSpeed,
            String windDirection,
            Double blacklineWidth,
            FuelCondition blacklineFuelCondition,
            LocalTime burnTime) {

        if (fireType == null) {
            return BurnDetermination.INDETERMINATE;
        }

        switch (fireType) {
            case BLACKLINES:
                return checkBlacklineConditions(temperature, humidity, windSpeed,
                        blacklineWidth, blacklineFuelCondition, burnTime);

            case HEADFIRES:
                return checkHeadfireConditions(temperature, humidity, windSpeed,
                        windDirection, burnTime);

            case BACKFIRES:
                return BurnDetermination.ACCEPTABLE;

            default:
                return BurnDetermination.INDETERMINATE;
        }
    }


    private static BurnDetermination checkBlacklineConditions(
            double temperature,
            double humidity,
            double windSpeed,
            Double blacklineWidth,
            FuelCondition blacklineFuelCondition,
            LocalTime burnTime) {

        System.out.println("\n=== Checking Blackline Conditions ===");
        System.out.println("Temperature: " + temperature + "°F");
        System.out.println("Humidity: " + (humidity * 100) + "%");
        System.out.println("Wind Speed: " + windSpeed + " mph");
        System.out.println("Blackline Width: " + blacklineWidth + " feet");
        System.out.println("Fuel Condition: " + blacklineFuelCondition);
        System.out.println("Burn Time: " + burnTime);

        if (burnTime != null) {
            LocalTime startTime = LocalTime.of(10, 0);
            LocalTime endTime = LocalTime.of(16, 0);

            if (burnTime.isBefore(startTime) || burnTime.isAfter(endTime)) {
                System.out.println("FAILED: Burn time outside acceptable window (10:00-16:00)");
                return BurnDetermination.NOT_RECOMMENDED_OTHER;
            }
            System.out.println("Burn time OK");
        }

        if (temperature < 35.0 || temperature > 65.0) {
            System.out.println("FAILED: Temperature outside acceptable range (35-65°F)");
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        }

        double humidityPercent = humidity * 100.0;
        if (humidityPercent < 30.0 || humidityPercent > 65.0) {
            System.out.println("FAILED: Humidity outside acceptable range (30-65%)");
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (windSpeed < 0.0 || windSpeed > 10.0) {
            System.out.println("FAILED: Wind speed outside acceptable range (0-10 mph)");
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }

        if (blacklineWidth != null && blacklineFuelCondition != null) {
            if (blacklineFuelCondition == FuelCondition.NON_VOLATILE) {
                if (blacklineWidth < 100.0) {
                    System.out.println("FAILED: Blackline width too small for NON_VOLATILE fuel (need 100+ feet)");
                    return BurnDetermination.NOT_RECOMMENDED_OTHER;
                }
            } else if (blacklineFuelCondition == FuelCondition.VOLATILE) {
                if (blacklineWidth < 500.0) {
                    System.out.println("FAILED: Blackline width too small for VOLATILE fuel (need 500+ feet)");
                    return BurnDetermination.NOT_RECOMMENDED_OTHER;
                }
            }
        }

        boolean isDesired = true;


        if (temperature < 40.0 || temperature > 60.0) {
            System.out.println("ℹ Temperature not in desired range (40-60°F), but acceptable");
            isDesired = false;
        }


        if (humidityPercent < 40.0 || humidityPercent > 60.0) {
            System.out.println("Humidity not in desired range (40-60%), but acceptable");
            isDesired = false;
        }

        if (windSpeed > 8.0) {
            System.out.println("Wind speed not in desired range (0-8 mph), but acceptable");
            isDesired = false;
        }

        if (isDesired) {
            System.out.println("All conditions meet DESIRED specifications!");
        } else {
            System.out.println("All conditions are ACCEPTABLE (some not desired)");
        }

        System.out.println("===================================\n");
        if (isDesired) {
            return BurnDetermination.DESIRED;
        } else {
            return BurnDetermination.ACCEPTABLE;
        }
    }


    private static BurnDetermination checkHeadfireConditions(
            double temperature,
            double humidity,
            double windSpeed,
            String windDirection,
            LocalTime burnTime) {

        System.out.println("\n=== Checking Headfire Conditions ===");
        System.out.println("Temperature: " + temperature + "°F");
        System.out.println("Humidity: " + (humidity * 100) + "%");
        System.out.println("Wind Speed: " + windSpeed + " mph");
        System.out.println("Wind Direction: " + windDirection);
        System.out.println("Burn Time: " + burnTime);

        if (burnTime != null) {
            LocalTime startTime = LocalTime.of(12, 0);
            LocalTime endTime = LocalTime.of(16, 0);

            if (burnTime.isBefore(startTime) || burnTime.isAfter(endTime)) {
                System.out.println("FAILED: Burn time outside acceptable window (12:00-16:00)");
                return BurnDetermination.NOT_RECOMMENDED_OTHER;
            }
            System.out.println("Burn time OK");
        }


        if (temperature < 60.0 || temperature > 85.0) {
            System.out.println("FAILED: Temperature outside acceptable range (60-85°F)");
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        }


        double humidityPercent = humidity * 100.0;
        if (humidityPercent < 20.0 || humidityPercent > 45.0) {
            System.out.println("FAILED: Humidity outside acceptable range (20-45%)");
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }


        if (windSpeed < 5.0 || windSpeed > 20.0) {
            System.out.println("FAILED: Wind speed outside acceptable range (5-20 mph)");
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }


        boolean acceptableDirection = true;
        if (windDirection != null && !windDirection.isEmpty()) {
            String direction = windDirection.toUpperCase().trim();
            acceptableDirection = direction.contains("SOUTH") ||
                    direction.contains("WEST") ||
                    direction.contains("SW") ||
                    direction.contains("S") ||
                    direction.contains("W");

            if (!acceptableDirection) {
                System.out.println("FAILED: Wind direction not acceptable (need South to West)");
                return BurnDetermination.NOT_RECOMMENDED_OTHER;
            }
        }

        boolean isDesired = true;


        if (temperature < 70.0 || temperature > 80.0) {
            System.out.println("Temperature not in desired range (70-80°F), but acceptable");
            isDesired = false;
        }


        if (humidityPercent < 25.0 || humidityPercent > 40.0) {
            System.out.println("Humidity not in desired range (25-40%), but acceptable");
            isDesired = false;
        }


        if (windSpeed < 8.0 || windSpeed > 15.0) {
            System.out.println("Wind speed not in desired range (8-15 mph), but acceptable");
            isDesired = false;
        }


        if (windDirection != null && !windDirection.toUpperCase().contains("SW")) {
            System.out.println("Wind direction not desired (prefer Southwest), but acceptable");
            isDesired = false;
        }

        if (isDesired) {
            System.out.println("All conditions meet DESIRED specifications!");
        } else {
            System.out.println("All conditions are ACCEPTABLE (some not desired)");
        }

        System.out.println("===================================\n");
        if (isDesired) {
            return BurnDetermination.DESIRED;
        } else {
            return BurnDetermination.ACCEPTABLE;
        }
    }

    public static String degreesToDirection(long degrees) {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int index = (int) Math.round(((degrees % 360) / 22.5));
        return directions[index % 16];
    }
}