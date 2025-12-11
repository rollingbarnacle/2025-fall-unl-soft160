package edu.unl.cse.soft160.xx.burnplan.evaluator;

import edu.unl.cse.soft160.json_connections.connector.OpenWeatherConnector;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BurnPlan {


    private Date BurnDate;
    private Location location;
    private LandTypes fuelType;
    private FireTypes fireType;
    private String windDirection;
    private LocalTime burnTime;

    private Double blacklineWidth;
    private FuelCondition blacklineFuelCondition;

    private Double acres;
    private Date burnDate;
    private Boolean coldFront;
    private List<Supply> supplyAvailable;

    private Double rainProbability;
    private Double rainAmount;
    private Date coldFrontDate;
    private double priorDayRainProbability;
    private double temperature;
    private double windSpeed;
    private double humidity;
    private String weatherForecast;
    private boolean isTrue;
    private String jsonFileName;


    public static OpenWeatherConnector connector;
//gay gay gay
    private static final String API_KEY = "0b83903731b129b23c1891b85008ca55";


    public BurnPlan(LandTypes fuelType,
                    FireTypes fireType,
                    Location location,
                    Boolean coldFront,
                    Double acres,
                    Date burnDate,
                    LocalTime burnTime,
                    List<Supply> supplyAvailable,
                    boolean isTrue,
                    String jsonFileName,
                    Double blacklineWidth,
                    FuelCondition blacklineFuelCondition) {

        this.fuelType = fuelType;
        this.fireType = fireType;
        this.location = location;
        this.coldFront = coldFront;
        this.acres = acres;
        this.burnDate = burnDate;
        this.burnTime = burnTime;
        this.supplyAvailable = supplyAvailable;
        this.isTrue = isTrue;
        this.jsonFileName = jsonFileName;
        this.blacklineWidth = blacklineWidth;
        this.blacklineFuelCondition = blacklineFuelCondition;

        this.connector = new OpenWeatherConnector("forecast", "0b83903731b129b23c1891b85008ca55");


        if (location != null && burnDate != null) {
            if (this.isTrue) {
                this.connector = new OpenWeatherConnector("forecast");
            } else {
                this.connector = new OpenWeatherConnector("forecast", API_KEY);
            }
            fetchWeatherData();
        }
    }



    private void fetchWeatherData() {
        try {
            String query = "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=imperial";
            String response = "";
            if (this.isTrue) {
                response = connector.retrieveData(jsonFileName);
            } else {
                response = connector.retrieveData(query);
            }

            List<Date> availableTimestamps = connector.getTimestamps();


            if (availableTimestamps.isEmpty()) {
                System.err.println("No forecast timestamps available");
                setDefaultWeatherValues();
                return;
            }


            LocalDate burnLocalDate = burnDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();


            List<Date> dailyTimestamps = new ArrayList<>();
            for (Date date : availableTimestamps) {
                LocalDate forecastLocalDate = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (forecastLocalDate.isEqual(burnLocalDate)) {
                    dailyTimestamps.add(date);
                }
            }

            List<Date> daytimeTimestamps = new ArrayList<>();
            for (Date date : dailyTimestamps) {
                int hour = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .getHour();


                if (hour >= 6 && hour <= 18) {
                    daytimeTimestamps.add(date);
                }
            }


            Date targetDateTime;
            if (this.burnTime != null) {
                targetDateTime = Date.from(
                        burnLocalDate.atTime(this.burnTime)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                );
            } else {
                targetDateTime = Date.from(
                        burnLocalDate.atTime(12, 0)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                );
            }
            Date closestTimestamp;
            if (isTrue) {
                closestTimestamp = targetDateTime;
            } else {
                closestTimestamp = findClosestTimestamp(daytimeTimestamps, targetDateTime);
            }


            if (closestTimestamp == null) {
                System.err.println("No forecast data available for the burn date and time");
                setDefaultWeatherValues();
                return;
            }


            this.windSpeed = connector.getWindSpeed(closestTimestamp);
            this.humidity = connector.getHumidity(closestTimestamp) / 100.0;
            this.rainProbability = connector.getProbabilityOfPrecipitation(closestTimestamp);
            this.temperature = connector.getTemperature(closestTimestamp);
            this.weatherForecast = connector.getWeatherCategories(closestTimestamp).toString();


            long windDirDegrees = connector.getWindDirection(closestTimestamp);
            this.windDirection = EnvironmentalConditions.degreesToDirection(windDirDegrees);


            this.rainAmount = 0.0;
            for (int i = 0; i < 9; i++) {
                long offsetMilliseconds = 3 * 60 * 60 * 1000 * i;
                Date timestamp = new Date(closestTimestamp.getTime() + offsetMilliseconds);
                rainAmount += connector.getThreeHourRainfall(timestamp);
            }


            Date priorDate = Date.from(burnDate.toInstant().minus(1, java.time.temporal.ChronoUnit.DAYS));
            Date closestPriorTimestamp = findClosestTimestamp(availableTimestamps, priorDate);

            if (closestPriorTimestamp != null) {
                this.priorDayRainProbability = connector.getProbabilityOfPrecipitation(closestPriorTimestamp);
            } else {
                this.priorDayRainProbability = 0.0;
                System.out.println("No prior day data available");
            }

            System.out.println("Weather data fetched successfully!");

        } catch (IOException e) {
            System.err.println("IOException - Error fetching weather data from API");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Cause: " + e.getCause());
            e.printStackTrace();
            setDefaultWeatherValues();
        } catch (Exception e) {
            System.err.println("Exception - Error processing weather data");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            setDefaultWeatherValues();
        }
    }


    private Date findClosestTimestamp(List<Date> timestamps, Date targetDate) {
        if (timestamps == null || timestamps.isEmpty()) {
            return null;
        }

        Date closest = null;
        long minDifference = Long.MAX_VALUE;

        for (Date timestamp : timestamps) {
            long difference = Math.abs(timestamp.getTime() - targetDate.getTime());
            if (difference < minDifference) {
                minDifference = difference;
                closest = timestamp;
            }
        }
        return closest;
    }


    private void setDefaultWeatherValues() {
        this.windSpeed = 0.0;
        this.humidity = 0.0;
        this.rainProbability = 0.0;
        this.rainAmount = 0.0;
        this.temperature = 0.0;
        this.priorDayRainProbability = 0.0;
    }


    public void refreshWeatherData() {
        if (location != null && burnDate != null) {
            if (connector == null) {
                connector = new OpenWeatherConnector("forecast", API_KEY);
            }
            fetchWeatherData();
        }
    }

    public void determineFuelType(String fuelType) {
        if (fuelType.equalsIgnoreCase("Light")) {
            this.fuelType = LandTypes.LIGHT;
        } else if (fuelType.equalsIgnoreCase("Heavy")) {
            this.fuelType = LandTypes.HEAVY;
        }
    }

    public boolean hasRequiredData() {
        if (location == null || fuelType == null || burnDate == null ||
                supplyAvailable == null || acres == null || coldFront == null ||
                fireType == null) {
            return false;
        }

        if (fireType == FireTypes.BLACKLINES) {
            if (blacklineWidth == null || blacklineWidth <= 0) {
                return false;
            }
            if (blacklineFuelCondition == null) {
                return false;
            }
        }

        for (Supply supply : supplyAvailable) {
            if (supply.getSupplyQuantity() == null) {
                return false;
            }
        }


        if (acres <= 0) {
            return false;
        }

        if (rainProbability < 0 || rainProbability > 1 ||
                priorDayRainProbability < 0 || priorDayRainProbability > 1) {
            return false;
        }

        if (humidity < 0 || humidity > 1) {
            return false;
        }

        if (windSpeed < 0) {
            return false;
        }

        return true;
    }


    public boolean getTestStatus() {return isTrue; }

    public String getJsonFileName() {return jsonFileName; }

    public LandTypes getFuelType() {
        return fuelType;
    }

    public Location getLocation() {
        return location;
    }

    public Double getAcres() {
        return acres;
    }

    public Date getBurnDate() {
        return burnDate;
    }

    public LocalDate getBurnLocalDate() {
        return burnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public List<Supply> getSupplyAvailable() {
        return supplyAvailable;
    }

    public double getRainAmount() {
        return rainAmount;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public LocalTime getBurnTime() {
        return burnTime;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public Date getColdFrontDate() {
        return coldFrontDate;
    }

    public double getPriorDayRainProbability() {
        return priorDayRainProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public Boolean getColdFront() {
        return coldFront;
    }

    public String getWeatherForecast() {
        return weatherForecast;
    }

    public FireTypes getFireType() {
        return fireType;
    }

    public Double getBlacklineWidth() {
        return blacklineWidth;
    }

    public FuelCondition getBlacklineFuelCondition() {
        return blacklineFuelCondition;
    }


    public void setTestStatus(boolean isTrue) {this.isTrue = isTrue; }

    public void setJsonFileName(String jsonFileName) {this.jsonFileName = jsonFileName; }

    public void setFuelType(LandTypes fuelType) {
        this.fuelType = fuelType;
    }

    public void setLocation(Location location) {
        this.location = location;
        refreshWeatherData();
    }

    public void setBurnDate(Date burnDate) {
        this.burnDate = burnDate;
        refreshWeatherData();
    }

    public void setSupplyAvailable(List<Supply> supplyAvailable) {
        this.supplyAvailable = supplyAvailable;
    }

    public void setColdFrontDate(Date coldFrontDate) {
        this.coldFrontDate = coldFrontDate;
    }

    public void setPriorDayRainProbability(double priorDayRainProbability) {
        this.priorDayRainProbability = priorDayRainProbability;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWeatherForecast(String weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public void setFireType(FireTypes fireType) {
        this.fireType = fireType;
    }

    public void setBlacklineWidth(Double blacklineWidth) {
        this.blacklineWidth = blacklineWidth;
    }

    public void setBlacklineFuelCondition(FuelCondition blacklineFuelCondition) {
        this.blacklineFuelCondition = blacklineFuelCondition;
    }

    public void setAcres(Double acres) {
        this.acres = acres;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setBurnTime(LocalTime burnTime) {
        this.burnTime = burnTime;
    }

    public void setColdFront(Boolean coldFront) {
        this.coldFront = coldFront;
    }
}