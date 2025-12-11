package edu.unl.cse.soft160.xx.burnplan.evaluator;
/*

class MockOpenWeatherConnector extends edu.unl.cse.soft160.json_connections.connector.OpenWeatherConnector {

    public MockOpenWeatherConnector() {
        super("forecast");
    }

    @Override
    public double getProbabilityOfPrecipitation(Date date) {
        return 0.30;

    @Override
    public double getDailyRainfall(Date date) {
        return 5.0;
    }

    @Override
    public double getWindSpeed(Date timestamp) {
        return 8.0;

    @Override
    public long getHumidity(Date timestamp) {
        return 50;
    }

    @Override
    public double getTemperature(Date timestamp) {
        return 55.0;
    }

    @Override
    public long getWindDirection(Date timestamp) {
        return 225;
    }

    @Override
    public List<Date> getTimestamps() {
        return List.of(new Date());
    }
}

public class CheckWeatherTest {

    private Date toDateAtTime(LocalDate localDate, int hour, int minute) {
        return Date.from(localDate.atTime(hour, minute).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date toDate(LocalDate localDate) {
        return toDateAtTime(localDate, 0, 0);
    }

    @BeforeClass
    public static void setUpStaticConnector() {
        try {
            Field connectorField = BurnPlan.class.getDeclaredField("connector");

            connectorField.setAccessible(true);

            connectorField.set(null, new MockOpenWeatherConnector());

            System.out.println("Mock connector successfully injected into BurnPlan.connector");

        } catch (Exception e) {
            System.err.println("FATAL: Failed to set static BurnPlan.connector for testing.");
            e.printStackTrace();
            throw new RuntimeException("Test setup failed due to static field injection error.", e);
        }
    }


    @Test
    public void testAcceptableWeather_LightFuel() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.LIGHT, 100);
        CheckWeather checkWeather = new CheckWeather(checkLand);

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        LocalDate coldFront = localBurnDate.plusDays(2);
        Date burnDate = toDate(localBurnDate);

        BurnDetermination result = checkWeather.checkForecast(
                burnDate,
                coldFront,
                0.20
        );

        assertEquals("Light fuel with acceptable weather should return ACCEPTABLE",
                BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testAcceptableWeather_HeavyFuel_LowPriorDayRain() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.HEAVY, 100);
        CheckWeather checkWeather = new CheckWeather(checkLand);

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        LocalDate coldFront = localBurnDate.plusDays(2);
        Date burnDate = toDate(localBurnDate);

        BurnDetermination result = checkWeather.checkForecast(
                burnDate,
                coldFront,
                0.50
        );

        assertEquals("Heavy fuel with prior day rain at threshold (0.50) should be ACCEPTABLE",
                BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testHeavyFuel_HighPriorDayRain() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.HEAVY, 100);
        CheckWeather checkWeather = new CheckWeather(checkLand);

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        LocalDate coldFront = localBurnDate.plusDays(2);
        Date burnDate = toDate(localBurnDate);

        BurnDetermination result = checkWeather.checkForecast(
                burnDate,
                coldFront,
                0.60
        );

        assertEquals("Heavy fuel with high prior day rain (0.60 > 0.50) should be NOT_RECOMMENDED",
                BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testColdFront_SameDayAsBurn() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.LIGHT, 100);
        CheckWeather checkWeather = new CheckWeather(checkLand);

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        LocalDate coldFront = localBurnDate;
        Date burnDate = toDate(localBurnDate);

        BurnDetermination result = checkWeather.checkForecast(
                burnDate,
                coldFront,
                0.20
        );


        assertEquals("Cold front on same day (0 hours away) should be NOT_RECOMMENDED",
                BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testColdFront_WithinFewHours() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.LIGHT, 100);
        CheckWeather checkWeather = new CheckWeather(checkLand);

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        Date burnDate = toDateAtTime(localBurnDate, 14, 0);

        LocalDate coldFrontDate = localBurnDate.plusDays(1);


        BurnDetermination result = checkWeather.checkForecast(
                burnDate,
                coldFrontDate,
                0.20
        );

        assertEquals("Cold front within 10 hours should be NOT_RECOMMENDED",
                BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testHighRainProbability() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.LIGHT, 100);
        try {
            Field connectorField = BurnPlan.class.getDeclaredField("connector");
            connectorField.setAccessible(true);

            MockOpenWeatherConnector highRainConnector = new MockOpenWeatherConnector() {
                @Override
                public double getProbabilityOfPrecipitation(Date date) {
                    return 0.60;
                }
            };

            connectorField.set(null, highRainConnector);

            CheckWeather checkWeather = new CheckWeather(checkLand);

            LocalDate localBurnDate = LocalDate.now().plusDays(3);
            LocalDate coldFront = localBurnDate.plusDays(2);
            Date burnDate = toDate(localBurnDate);

            BurnDetermination result = checkWeather.checkForecast(
                    burnDate,
                    coldFront,
                    0.20
            );

            assertEquals("High rain probability (60% > 50%) should be NOT_RECOMMENDED",
                    BurnDetermination.NOT_RECOMMENDED_OTHER, result);

            connectorField.set(null, new MockOpenWeatherConnector());

        } catch (Exception e) {
            throw new RuntimeException("Failed to test high rain probability", e);
        }
    }

    @Test
    public void testHighRainAmount() {
        CheckLand checkLand = new CheckLand(40.8136, -96.7026, LandTypes.LIGHT, 100);

        try {
            Field connectorField = BurnPlan.class.getDeclaredField("connector");
            connectorField.setAccessible(true);

            MockOpenWeatherConnector highRainAmountConnector = new MockOpenWeatherConnector() {
                @Override
                public double getDailyRainfall(Date date) {
                    return 15.0;
                }
            };

            connectorField.set(null, highRainAmountConnector);

            CheckWeather checkWeather = new CheckWeather(checkLand);

            LocalDate localBurnDate = LocalDate.now().plusDays(3);
            LocalDate coldFront = localBurnDate.plusDays(2);
            Date burnDate = toDate(localBurnDate);

            BurnDetermination result = checkWeather.checkForecast(
                    burnDate,
                    coldFront,
                    0.20
            );

            assertEquals("High rain amount (15.0 > 10.0) should be NOT_RECOMMENDED",
                    BurnDetermination.NOT_RECOMMENDED_OTHER, result);

            connectorField.set(null, new MockOpenWeatherConnector());

        } catch (Exception e) {
            throw new RuntimeException("Failed to test high rain amount", e);
        }
    }
}
*/