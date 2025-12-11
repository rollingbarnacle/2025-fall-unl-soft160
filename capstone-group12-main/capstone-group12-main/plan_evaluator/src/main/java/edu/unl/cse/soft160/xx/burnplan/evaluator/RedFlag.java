package edu.unl.cse.soft160.xx.burnplan.evaluator;


public class RedFlag {
    private final double RED_FLAG_WIND_THRESHOLD = 20.0;
    private final double RED_FLAG_HUMIDITY_THRESHOLD = 20.0;
    private final double RED_FLAG_TEMP_THRESHOLD = 80.0;
    private final double RED_FLAG_COLD_FRONT_THRESHOLD = 12.0;

    public BurnDetermination checkRedFlags(double wind, double humidityPercent, double airTemp,
                                           double coldFrontTime, String weatherForecast) {

        if (weatherForecast != null &&
                (weatherForecast.toLowerCase().contains("burn ban") ||
                        weatherForecast.toLowerCase().contains("burning ban") ||
                        weatherForecast.toLowerCase().contains("fire ban"))) {
            return BurnDetermination.BURNING_PROHIBITED;
        }

        boolean condition1 = wind > RED_FLAG_WIND_THRESHOLD;
        boolean condition2 = humidityPercent < RED_FLAG_HUMIDITY_THRESHOLD;
        boolean condition3 = airTemp > RED_FLAG_TEMP_THRESHOLD;
        boolean condition4 = coldFrontTime < RED_FLAG_COLD_FRONT_THRESHOLD;
        boolean condition5 = isUnfavorableFireWeather(weatherForecast);

        if (condition1 && condition2 && condition3 && condition4 && condition5) {
            return BurnDetermination.BURNING_PROHIBITED;
        }
        return BurnDetermination.ACCEPTABLE;
    }


    private boolean isUnfavorableFireWeather(String weatherForecast) {
        if (weatherForecast == null || weatherForecast.isEmpty()) {
            return false;
        }

        String forecast = weatherForecast.toLowerCase();

        return forecast.contains("red flag") ||
                forecast.contains("fire weather warning") ||
                forecast.contains("extreme fire danger") ||
                forecast.contains("critical fire weather") ||
                forecast.contains("unfavorable fire weather");
    }
}
