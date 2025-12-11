package edu.unl.cse.soft160.xx.burnplan.evaluator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.List;

public class BurnPlanEvaluationApp {
    private static List<BurnPlan> burnPlans = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> missingInfo = new ArrayList<>();


    private static String readLine() {
        String input = scanner.nextLine();
        if (input != null && input.trim().equalsIgnoreCase("exit")) {
            System.out.println("exiting... goodbye!");
            System.exit(1);
        }
        return input;
    }

    private static String formatDouble1(Double value) {
        if (value == null) {
            return "unknown";
        }
        return String.format("%.2f", value);
    }

    private static String formatPercentage1(Double value) {
        if (value == null) {
            return "unknown";
        }
        return String.format("%.1f%%", value * 100);
    }


    private static Double getValidLatitude() {
        while (true) {
            System.out.print("  Latitude (e.g., 40.8206 for Lincoln, NE, or leave blank for unknown): ");
            String input = readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                double latitude = Double.parseDouble(input);
                if (latitude >= -90 && latitude <= 90) {
                    return latitude;
                } else {
                    System.out.println("Invalid latitude. Must be between -90 and 90. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid decimal number.");
            }
        }
    }


    private static Double getValidLongitude() {
        while (true) {
            System.out.print("  Longitude (e.g., -96.6928 for Lincoln, NE, or leave blank for unknown): ");
            String input = readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                double longitude = Double.parseDouble(input);
                if (longitude >= -180 && longitude <= 180) {
                    return longitude;
                } else {
                    System.out.println("Invalid longitude. Must be between -180 and 180. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid decimal number.");
            }
        }
    }


    private static LocalDate getValidDate() {
        while (true) {
            System.out.print("Please enter the date you would like to burn (year-month-day, or leave blank for unknown): ");
            String input = readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use year-month-day format (e.g., 2025-12-15).");
            }
        }
    }


    private static LocalTime getValidTime() {
        while (true) {
            System.out.print("Please enter the time you would like to start the burn (HH:MM in 24-hour format, e.g., 14:00): ");
            String input = readLine().trim();

            if (input.isEmpty()) {
                return LocalTime.of(12, 0);
            }

            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM format (e.g., 14:00).");
            }
        }
    }


    private static Boolean getValidBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readLine().trim().toLowerCase();

            if (input.isEmpty()) {
                return null;
            }

            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Invalid input. Please enter 'true' or 'false'.");
            }
        }
    }


    private static Double getValidPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Must be a number >= 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number.");
            }
        }
    }


    private static Integer getValidPositiveInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Must be a number >= 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid integer.");
            }
        }
    }


    private static FireTypes getValidFireType() {
        while (true) {
            System.out.print("Please enter the fire type (BLACKLINES, HEADFIRES, BACKFIRES): ");
            String input = readLine().trim().toUpperCase();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return FireTypes.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid fire type. Please enter BLACKLINES, HEADFIRES, or BACKFIRES.");
            }
        }
    }


    private static LandTypes getValidLandFuelType() {
        while (true) {
            System.out.print("Please enter the land's fuel type (LIGHT, HEAVY): ");
            String input = readLine().trim().toUpperCase();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return LandTypes.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid fuel type. Please enter LIGHT or HEAVY.");
            }
        }
    }


    private static FuelCondition getValidFuelCondition() {
        while (true) {
            System.out.print("Enter blackline fuel condition (VOLATILE, NON_VOLATILE): ");
            String input = readLine().trim().toUpperCase();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return FuelCondition.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid fuel condition. Please enter VOLATILE or NON_VOLATILE.");
            }
        }
    }


    private static void printDetailedReport(BurnPlan burnPlan, BurnDetermination result,
                                            Double latitude, Double longitude,
                                            LocalDate dateOfBurn, LocalTime burnTime,
                                            Boolean coldFront, Double acres,
                                            FireTypes fireType, LandTypes landFuelType,
                                            Integer pumpers, Integer fireStartingFuel,
                                            Integer dripTorches, Integer tools,
                                            Integer backpackPumps, Integer dozers,
                                            Double blacklineWidth, FuelCondition blacklineFuelCondition) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss a");

        System.out.println("\n" + "=".repeat(70));
        System.out.println("BURN PLAN EVALUATION REPORT");
        System.out.println("=".repeat(70));


        LocalDateTime now = LocalDateTime.now();
        System.out.println("\nReport Generated: " + now.format(dateTimeFormatter));


        String burnDateStr = (dateOfBurn == null) ? "unknown" : dateOfBurn.format(dateFormatter);
        String burnTimeStr = (burnTime == null) ? "unknown" : burnTime.toString();
        System.out.println("Proposed Burn Date: " + burnDateStr);
        System.out.println("Proposed Burn Time: " + burnTimeStr);


        System.out.println("\n--- BURN SITE INFORMATION ---");
        System.out.println("Location (Latitude): " + (latitude == null ? "unknown" : latitude));
        System.out.println("Location (Longitude): " + (longitude == null ? "unknown" : longitude));
        System.out.println("Size of Land: " + (acres == null ? "unknown" : acres + " acres"));


        System.out.println("Land Fuel Type: " + (landFuelType == null ? "unknown" : landFuelType.toString().toLowerCase()));
        System.out.println("Fire Type: " + (fireType == null ? "unknown" : fireType.toString().toLowerCase()));


        if (fireType == FireTypes.BLACKLINES) {
            Double width = burnPlan.getBlacklineWidth();
            FuelCondition condition = burnPlan.getBlacklineFuelCondition();
            System.out.println("Blackline Width: " + (blacklineWidth == null ? "unknown" : blacklineWidth + " feet"));
            System.out.println("Blackline Fuel Condition: " +
                    (blacklineFuelCondition == null ? "unknown" : blacklineFuelCondition.toString().toLowerCase()));
        }


        System.out.println("\n--- SUPPLIES AVAILABLE ---");


        if (pumpers == null) {
            System.out.println("Pumpers: unknown");
        } else if (acres == null || pumpers >= Math.ceil(acres / 80.0)) {
            System.out.println("Pumpers: " + pumpers + " (meets minimum requirement of 1 per 80 acres)");
        } else {
            System.out.println("Pumpers: " + pumpers + " (DOES NOT meet minimum requirement of " +
                    Math.ceil(acres / 80.0) + " for " + acres + " acres)");
        }


        if (fireStartingFuel == null) {
            System.out.println("Fire Starting Fuel: unknown");
        } else if (acres == null || fireStartingFuel >= Math.ceil(acres / 10.0)) {
            System.out.println("Fire Starting Fuel: " + fireStartingFuel + " gallons (meets minimum requirement of 1 gallon per 10 acres)");
        } else {
            System.out.println("Fire Starting Fuel: " + fireStartingFuel + " gallons (DOES NOT meet minimum requirement of " +
                    Math.ceil(acres / 10.0) + " gallons for " + acres + " acres)");
        }


        if (dripTorches == null) {
            System.out.println("Drip Torches: unknown");
        } else if (fireStartingFuel == null || dripTorches >= Math.ceil(fireStartingFuel / 10.0)) {
            System.out.println("Drip Torches: " + dripTorches + " (meets minimum requirement of 1 per 10 gallons of fuel)");
        } else {
            System.out.println("Drip Torches: " + dripTorches + " (DOES NOT meet minimum requirement of " +
                    Math.ceil(fireStartingFuel / 10.0) + " for " + fireStartingFuel + " gallons of fuel)");
        }


        if (tools == null) {
            System.out.println("Tools: unknown");
        } else if (acres == null || tools >= Math.ceil(acres / 10.0)) {
            System.out.println("Tools: " + tools + " (meets minimum requirement of 1 per 10 acres)");
        } else {
            System.out.println("Tools: " + tools + " (DOES NOT meet minimum requirement of " +
                    Math.ceil(acres / 10.0) + " for " + acres + " acres)");
        }


        if (backpackPumps == null) {
            System.out.println("Backpack Pumps: unknown");
        } else if (backpackPumps >= 1) {
            System.out.println("Backpack Pumps: " + backpackPumps + " (meets minimum requirement of at least 1)");
        } else {
            System.out.println("Backpack Pumps: " + backpackPumps + " (DOES NOT meet minimum requirement of at least 1)");
        }


        if (dozers == null) {
            System.out.println("Dozers: unknown");
        } else if (dozers >= 1) {
            System.out.println("Dozers: " + dozers + " (meets minimum requirement of at least 1)");
        } else {
            System.out.println("Dozers: " + dozers + " (DOES NOT meet minimum requirement of at least 1)");
        }


        System.out.println("\n--- WEATHER FORECAST ---");
        if (burnPlan.getLocation() != null && burnPlan.getBurnDate() != null) {
            String tempRange = "unknown";
            String humidityRange = "unknown";
            String windSpeedRange = "unknown";
            String windDirRange = "unknown";
            String timeWindow = "unknown";

            if (fireType == FireTypes.BLACKLINES) {
                tempRange = "35-65°F";
                humidityRange = "30-65%";
                windSpeedRange = "0-10 mph";
                windDirRange = "Any";
                timeWindow = "10:00 AM - 4:00 PM";
            } else if (fireType == FireTypes.HEADFIRES || fireType == FireTypes.BACKFIRES) {
                tempRange = "60-85°F";
                humidityRange = "20-45%";
                windSpeedRange = "5-20 mph";
                windDirRange = "South to West";
                timeWindow = "12:00 PM (Noon) - 4:00 PM";
            }

            System.out.println("Temperature: " + formatDouble1(burnPlan.getTemperature()) + "°F (Acceptable range: " + tempRange + ")");
            System.out.println("Wind Speed: " + formatDouble1(burnPlan.getWindSpeed()) + " mph (Acceptable range: " + windSpeedRange + ")");
            System.out.println("Wind Direction: " + (burnPlan.getWindDirection() == null ? "unknown" : burnPlan.getWindDirection()) +
                    " (Acceptable: " + windDirRange + ")");
            System.out.println("Humidity: " + formatPercentage1(burnPlan.getHumidity()) + " (Acceptable range: " + humidityRange + ")");
            System.out.println("Rain Probability: " + formatPercentage1(burnPlan.getRainProbability()) + " (Acceptable range: 0-50%)");
            System.out.println("Rainfall Amount: " + formatDouble1(burnPlan.getRainAmount()) + " mm (Acceptable range: 0-10 mm)");
            System.out.println("Prior Day Rain Probability: " + formatPercentage1(burnPlan.getPriorDayRainProbability()) +
                    " (Acceptable range: " + (landFuelType == LandTypes.HEAVY ? "0-50%" : "0-100%") + ")");
            System.out.println("Acceptable Burn Time Window: " + timeWindow);
        } else {
            System.out.println("Weather data: Not available (location or burn date not specified)");
        }


        System.out.println("\n--- DETERMINATION ---");
        System.out.println("Burn Plan Determination: " + result);


        System.out.println("\n--- SUPPORTING EVIDENCE ---");
        printEvidence(burnPlan, result, latitude, longitude, dateOfBurn, acres, fireType, landFuelType,
                pumpers, fireStartingFuel, dripTorches, tools, backpackPumps, dozers,
                blacklineWidth, blacklineFuelCondition, coldFront, burnTime);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("END OF REPORT");
        System.out.println("=".repeat(70) + "\n");
    }

    private static String formatDouble(double value) {
        if (value == 0.0) {
            return "unknown";
        }
        return String.format("%.2f", value);
    }

    private static String formatPercentage(double value) {
        if (value == 0.0) {
            return "unknown";
        }
        return String.format("%.1f%%", value * 100);
    }

    private static void printEvidence(BurnPlan burnPlan, BurnDetermination determination,
                                      Double latitude, Double longitude, LocalDate dateOfBurn,
                                      Double acres, FireTypes fireType, LandTypes landFuelType,
                                      Integer pumpers, Integer fireStartingFuel, Integer dripTorches,
                                      Integer tools, Integer backpackPumps, Integer dozers,
                                      Double blacklineWidth, FuelCondition blacklineFuelCondition,
                                      Boolean coldFront, LocalTime burnTime) {


        List<String> issues = new ArrayList<>();

        missingInfo.clear();


        if (burnPlan.getFireType() == FireTypes.BLACKLINES) {
            Double width = burnPlan.getBlacklineWidth();
            FuelCondition condition = burnPlan.getBlacklineFuelCondition();

            if (condition == FuelCondition.NON_VOLATILE && width != null && width < 100.0) {
                issues.add("Blackline width " + width + " feet is below minimum (100 feet for non-volatile fuel)");
            }
            if (condition == FuelCondition.VOLATILE && width != null && width < 500.0) {
                issues.add("Blackline width " + width + " feet is below minimum (500 feet for volatile fuel)");
            }
        }


        if (determination == BurnDetermination.INDETERMINATE) {
            System.out.println("Status: INDETERMINATE - Missing required information or data issues");

            if (latitude == null || longitude == null) {
                missingInfo.add("Location coordinates (latitude and/or longitude)");
            }
            if (landFuelType == null) {
                missingInfo.add("Land fuel type");
            }
            if (fireType == null) {
                missingInfo.add("Fire type");
            }
            if (dateOfBurn == null) {
                missingInfo.add("Burn date");
            }
            if (acres == null || acres <= 0) {
                missingInfo.add("Valid acreage (must be > 0)");
            }
            if (pumpers == null) {
                missingInfo.add("Number of pumpers");
            }
            if (fireStartingFuel == null) {
                missingInfo.add("Amount of fire starting fuel (gallons)");
            }
            if (dripTorches == null) {
                missingInfo.add("Number of drip torches");
            }
            if (tools == null) {
                missingInfo.add("Number of tools");
            }
            if (backpackPumps == null) {
                missingInfo.add("Number of backpack pumps");
            }
            if (dozers == null) {
                missingInfo.add("Number of dozers");
            }

            if (fireType == FireTypes.BLACKLINES) {
                if (blacklineWidth == null || blacklineWidth <= 0) {
                    missingInfo.add("Blackline width (must be > 0)");
                }
                if (blacklineFuelCondition == null) {
                    missingInfo.add("Blackline fuel condition");
                }
            }


            if (burnPlan.getTemperature() == 0.0 &&
                    burnPlan.getWindSpeed() == 0.0 &&
                    burnPlan.getHumidity() == 0.0) {
                missingInfo.add("Weather forecast data (unable to fetch from API)");
            }

            if (!missingInfo.isEmpty()) {
                System.out.println("\nMissing Information:");
                for (String info : missingInfo) {
                    System.out.println("  - " + info);
                }
            }


        } else if (determination == BurnDetermination.BURNING_PROHIBITED) {
            System.out.println("Status: BURNING PROHIBITED - Red flag conditions detected");
            if (coldFront != null && coldFront) {
                System.out.println("\nReason: Cold front is approaching within the next 12 hours.");
                System.out.println("  - Burning is strictly prohibited when a cold front is imminent");
                System.out.println("  - Cold fronts create dangerous and unpredictable fire conditions");
            } else {
                System.out.println("\nReason: Extreme weather conditions create severe fire danger.");
                System.out.println("  - High winds, very low humidity, or approaching weather system detected");
                System.out.println("  - These conditions pose unacceptable risk for controlled burning");
            }

        } else if (determination == BurnDetermination.NOT_RECOMMENDED_OTHER) {
            System.out.println("Status: NOT RECOMMENDED");
            System.out.println("\nOne or more conditions are outside acceptable ranges:");

            if (fireType == FireTypes.BLACKLINES) {
                Double width = burnPlan.getBlacklineWidth();
                FuelCondition condition = burnPlan.getBlacklineFuelCondition();

                if (burnPlan.getTemperature() < 60.0 || burnPlan.getTemperature() > 85.0) {
                    issues.add("Temperature " + String.format("%.2f", burnPlan.getTemperature())
                            + "°F is outside acceptable range (60-85°F)");
                }
                if (burnPlan.getHumidity() * 100 < 20.0 || burnPlan.getHumidity() * 100 > 45.0) {
                    issues.add("Humidity " + String.format("%.1f%%", burnPlan.getHumidity() * 100)
                            + " is outside acceptable range (20-45%)");
                }

                if (burnPlan.getWindSpeed() > 10.0) {
                    issues.add("Wind speed " + burnPlan.getWindSpeed()
                            + " mph exceeds maximum (10 mph)");
                }
                if (burnTime != null && (burnTime.isBefore(LocalTime.of(10, 0)) || burnTime.isAfter(LocalTime.of(16, 0)))) {
                    issues.add("Burn time " + burnTime
                            + " is outside acceptable window (10:00 AM - 4:00 PM)");
                }
                if (condition == FuelCondition.NON_VOLATILE && width != null && width < 100.0) {
                    issues.add("Blackline width " + width
                            + " feet is below minimum (100 feet for non-volatile fuel)");
                }
                if (condition == FuelCondition.VOLATILE && width != null && width < 500.0) {
                    issues.add("Blackline width " + width
                            + " feet is below minimum (500 feet for volatile fuel)");
                }


            } else if (fireType == FireTypes.HEADFIRES || fireType == FireTypes.BACKFIRES) {
                if (burnPlan.getTemperature() < 60.0 || burnPlan.getTemperature() > 85.0) {
                    issues.add("Temperature " + burnPlan.getTemperature() + "°F is outside acceptable range (60-85°F)");
                }
                if (burnPlan.getHumidity() * 100 < 20.0 || burnPlan.getHumidity() * 100 > 45.0) {
                    issues.add("Humidity " + String.format("%.1f%%", burnPlan.getHumidity() * 100) +
                            " is outside acceptable range (20-45%)");
                }
                if (burnPlan.getWindSpeed() < 5.0 || burnPlan.getWindSpeed() > 20.0) {
                    issues.add("Wind speed " + burnPlan.getWindSpeed() + " mph is outside acceptable range (5-20 mph)");
                }
                if (burnTime != null && (burnTime.isBefore(LocalTime.of(12, 0)) || burnTime.isAfter(LocalTime.of(16, 0)))) {
                    issues.add("Burn time " + burnTime + " is outside acceptable window (12:00 PM - 4:00 PM)");
                }
            }


            if (burnPlan.getRainProbability() > 0.50) {
                issues.add("Rain probability " + String.format("%.1f%%", burnPlan.getRainProbability() * 100) +
                        " exceeds maximum (50%)");
            }
            if (burnPlan.getRainAmount() > 10.0) {
                issues.add("Rainfall amount " + burnPlan.getRainAmount() + " mm exceeds maximum (10 mm)");
            }
            if (landFuelType == LandTypes.HEAVY && burnPlan.getPriorDayRainProbability() > 0.50) {
                issues.add("Prior day rain probability " + String.format("%.1f%%", burnPlan.getPriorDayRainProbability() * 100) +
                        " exceeds maximum for heavy fuel (50%)");
            }


            if (pumpers != null && acres != null && pumpers < Math.ceil(acres / 80.0)) {
                issues.add("Insufficient pumpers: " + pumpers + " available, " + Math.ceil(acres / 80.0) + " required");
            }
            if (fireStartingFuel != null && acres != null && fireStartingFuel < Math.ceil(acres / 10.0)) {
                issues.add("Insufficient fire starting fuel: " + fireStartingFuel + " gallons available, " +
                        Math.ceil(acres / 10.0) + " gallons required");
            }
            if (dripTorches != null && fireStartingFuel != null && dripTorches < Math.ceil(fireStartingFuel / 10.0)) {
                issues.add("Insufficient drip torches: " + dripTorches + " available, " +
                        Math.ceil(fireStartingFuel / 10.0) + " required");
            }
            if (tools != null && acres != null && tools < Math.ceil(acres / 10.0)) {
                issues.add("Insufficient tools: " + tools + " available, " + Math.ceil(acres / 10.0) + " required");
            }
            if (backpackPumps != null && backpackPumps < 1) {
                issues.add("No backpack pumps available (minimum 1 required)");
            }
            if (dozers != null && dozers < 1) {
                issues.add("No dozers available (minimum 1 required)");
            }


        } else if (determination == BurnDetermination.NOT_RECOMMENDED_TEMPERATURE) {
            System.out.println("Status: NOT RECOMMENDED - Temperature Issues");
            System.out.println("\nReason: Temperature is outside the acceptable range for safe burning.");
            System.out.println("  - Current temperature: " + burnPlan.getTemperature() + "°F");
            if (fireType == FireTypes.BLACKLINES) {
                System.out.println("  - Acceptable range for blacklines: 35-65°F");
            } else {
                System.out.println("  - Acceptable range for headfires/backfires: 60-85°F");
            }


        } else if (determination == BurnDetermination.NOT_RECOMMENDED_WIND) {
            System.out.println("Status: NOT RECOMMENDED - Wind Conditions");
            System.out.println("\nReason: Wind conditions are outside the acceptable range for safe burning.");
            System.out.println("  - Current wind speed: " + burnPlan.getWindSpeed() + " mph");
            if (fireType == FireTypes.BLACKLINES) {
                System.out.println("  - Acceptable range for blacklines: 0-10 mph");
            } else {
                System.out.println("  - Acceptable range for headfires/backfires: 5-20 mph");
            }

        } else if (determination == BurnDetermination.ACCEPTABLE) {
            System.out.println("Status: ACCEPTABLE - All conditions meet safety requirements");
            System.out.println("\nAll safety criteria have been satisfied:");
            System.out.println("  - Weather conditions are within acceptable ranges");
            System.out.println("  - Required supplies are available in sufficient quantities");
            System.out.println("  - No red flag warnings or prohibitive conditions");
            System.out.println("  - Burn timing is appropriate for the selected fire type");
            System.out.println("\nThe burn may proceed as planned, subject to final on-site assessment.");
        }
        if (!issues.isEmpty()) {
            System.out.println("\nIssues Identified");
        }
        for (String issue : issues) {
            System.out.println("  - " + issue);

        }

    }

    public static BurnDetermination evaluateBurnPlan(boolean isTrue) {
        try {
            String jsonFileName = "";
            if (isTrue) {
                System.out.print("Enter name of JSON file: ");
                jsonFileName = scanner.nextLine();
            }
            System.out.println("\nEnter the burn location coordinates:");
            Double latitude = getValidLatitude();
            Double longitude = getValidLongitude();

            Location location = (latitude != null && longitude != null)
                    ? new Location(latitude, longitude)
                    : null;

            LocalDate dateOfBurn = getValidDate();
            LocalTime burnTime = getValidTime();
            Boolean coldFront = getValidBoolean("Will there be a cold front in the next 12 hours (true/false): ");
            Double acres = getValidPositiveDouble("Please enter the amount of acres of land to burn: ");
            FireTypes fireType = getValidFireType();
            LandTypes landFuelType = getValidLandFuelType();


            List<Supply> supplies = new ArrayList<>();
            Integer pumpers = getValidPositiveInteger("How many pumpers: ");
            supplies.add(new Supply("pumpers", pumpers));

            Integer fireStartingFuel = getValidPositiveInteger("How much fire starting fuel: ");
            supplies.add(new Supply("fire_starting_fuel", fireStartingFuel));

            Integer dripTorches = getValidPositiveInteger("How many drip torches: ");
            supplies.add(new Supply("drip_torches", dripTorches));

            Integer tools = getValidPositiveInteger("How many tools: ");
            supplies.add(new Supply("tools", tools));

            Integer backpackPumps = getValidPositiveInteger("How many backpack pumps: ");
            supplies.add(new Supply("backpack_pumps", backpackPumps));

            Integer dozers = getValidPositiveInteger("How many dozers: ");
            supplies.add(new Supply("dozers", dozers));


            Double blacklineWidth = null;
            FuelCondition blacklineFuelCondition = null;

            if (fireType == FireTypes.BLACKLINES) {
                blacklineWidth = getValidPositiveDouble("Enter blackline width (in feet): ");
                blacklineFuelCondition = getValidFuelCondition();
            }


            Date burnDate = (dateOfBurn == null)
                    ? null
                    : Date.from(dateOfBurn.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (location != null) {
                System.out.println("\nFetching weather data from OpenWeatherMap API for location (" + latitude + ", " + longitude + ")...");
            }

            BurnPlan burnPlan = new BurnPlan(
                    landFuelType,
                    fireType,
                    location,
                    coldFront,
                    acres,
                    burnDate,
                    burnTime,
                    supplies,
                    isTrue,
                    jsonFileName,
                    blacklineWidth,
                    blacklineFuelCondition
            );

            burnPlans.add(burnPlan);
            BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(burnPlan);

            printDetailedReport(burnPlan, result,
                    latitude, longitude,
                    dateOfBurn, burnTime,
                    coldFront, acres,
                    fireType, landFuelType,
                    pumpers, fireStartingFuel,
                    dripTorches, tools,
                    backpackPumps, dozers, blacklineWidth, blacklineFuelCondition);

            return result;

        } catch (Exception e) {
            System.err.println("Error evaluating burn plan: " + e.getMessage());
            e.printStackTrace();
            return BurnDetermination.INDETERMINATE;
        }
    }

    public static void evaluateMultipleBurnPlans() {
        System.out.print("How many burn plans would you like to evaluate? ");

        int numPlans = 0;
        while (true) {
            try {
                numPlans = Integer.parseInt(scanner.nextLine().trim());
                if (numPlans > 0) {
                    break;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }

        List<BurnDetermination> results = new ArrayList<>();

        for (int i = 1; i <= numPlans; i++) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EVALUATING BURN PLAN #" + i + " OF " + numPlans);
            System.out.println("=".repeat(60));
            BurnDetermination result = evaluateBurnPlan(false);
            results.add(result);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SUMMARY OF ALL BURN PLANS");
        System.out.println("=".repeat(60));
        for (int i = 0; i < results.size(); i++) {
            System.out.println("Burn Plan #" + (i + 1) + ": " + results.get(i));
        }
        System.out.println("=".repeat(60) + "\n");
    }

    public static void main(String[] args) {

        System.out.println("\n" + "=".repeat(60));
        System.out.println("BURN PLAN EVALUATION SYSTEM");
        System.out.println("=".repeat(60) + "\n");


        System.out.println("1. Evaluate My Burn Plan");
        System.out.println("2. Evaluate Multiple Burn Plans");
        System.out.println("3. Evaluate Test Burn Plan");
        System.out.println("4. Exit or enter \"exit\" at any time");
        System.out.println("=".repeat(60));
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                evaluateBurnPlan(false);
                break;
            case "2":
                evaluateMultipleBurnPlans();
                break;
            case "3":
                evaluateBurnPlan(true);
                break;
            case "4":
                System.out.println("\n" + "=".repeat(60));
                System.out.println("Thank you for using the Burn Plan Evaluation System!");
                System.out.println("=".repeat(60) + "\n");
                break;
            default:
                System.out.println("\nInvalid choice. Please select 1, 2, 3, or 4.\n");

        }

        scanner.close();
    }
}