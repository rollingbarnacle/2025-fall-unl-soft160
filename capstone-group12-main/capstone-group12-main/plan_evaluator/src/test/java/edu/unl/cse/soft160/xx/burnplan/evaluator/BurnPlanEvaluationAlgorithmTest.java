package edu.unl.cse.soft160.xx.burnplan.evaluator;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BurnPlanEvaluationAlgorithmTest {

    private Location loc() {
        return new Location(40.8, -96.7);
    }

    private Supply pumper(int qty) {
        return new Supply("pumpers", qty);
    }

    private Supply fuel(int qty) {
        return new Supply("fire_starting_fuel", qty);
    }

    private Supply dripTorch(int qty) {
        return new Supply("drip_torches", qty);
    }

    private Supply rake(int qty) {
        return new Supply("tools", qty);
    }

    private Supply backpackPump(int qty) {
        return new Supply("backpack_pumps", qty);
    }

    private Supply dozer(int qty) {
        return new Supply("dozers", qty);
    }


    private LocalTime burnTime() {
        return LocalTime.of(10, 0);
    }


    private BurnPlan basePlan() {

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        Date burnDate = Date.from(localBurnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localColdFrontDate = localBurnDate.plusDays(2);
        Date coldFrontDate = Date.from(localColdFrontDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        BurnPlan plan = new BurnPlan(
                LandTypes.LIGHT,
                FireTypes.BLACKLINES,
                loc(),
                false,
                80.0,
                burnDate,
                burnTime(),
                Arrays.asList(
                        pumper(1),
                        fuel(10),
                        dripTorch(1),
                        rake(8),
                        backpackPump(1),
                        dozer(1)
                ),
                false,
                "",
                0.2,
                FuelCondition.NON_VOLATILE

        );

        plan.setColdFrontDate(coldFrontDate);
        plan.setPriorDayRainProbability(0.1);
        plan.setTemperature(70.0);
        plan.setWindSpeed(10.0);
        plan.setHumidity(0.40);
        plan.setWeatherForecast("normal conditions");
        plan.setWindDirection("30.0");

        return plan;
    }


    @Test
    public void indeterminate_whenPlanNullOrMissingData() {
        BurnDetermination nullResult = BurnPlanEvaluationAlgorithm.evaluate(null);
        assertEquals(BurnDetermination.INDETERMINATE, nullResult);

        BurnPlan plan = basePlan();
        plan.setLocation(null);
        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.INDETERMINATE, result);
    }

    @Test
    public void burningProhibited_whenColdFrontBooleanTrue() {
        BurnPlan plan = basePlan();
        plan.setColdFront(true);
        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }

    @Test
    public void burningProhibited_whenRedFlagTriggered() {
        BurnPlan plan = basePlan();

        LocalDate localBurnDate = LocalDate.now().plusDays(3);
        LocalDate localColdFront = localBurnDate.minusDays(1);

        Date burnDate = Date.from(localBurnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date coldFrontDate = Date.from(localColdFront.atStartOfDay(ZoneId.systemDefault()).toInstant());

        plan.setBurnDate(burnDate);
        plan.setColdFrontDate(coldFrontDate);
        plan.setWindSpeed(30.0);
        plan.setHumidity(0.10);
        plan.setTemperature(95.0);
        plan.setWeatherForecast("Red flag warning in effect");

        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }

    @Test
    public void notRecommended_whenBurnDateOutsideAllowedWindow() {
        BurnPlan plan = basePlan();


        LocalDate localBurnDate = LocalDate.now().plusDays(10);
        Date burnDate = Date.from(localBurnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        plan.setBurnDate(burnDate);

        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void notRecommended_whenSuppliesInsufficient() {
        BurnPlan plan = basePlan();

        plan.setSupplyAvailable(Collections.<Supply>emptyList());

        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void notRecommended_whenWeatherBadOrTempWindHumidityBad() {

        BurnPlan planWeather = basePlan();
        planWeather.setFuelType(LandTypes.HEAVY);
        planWeather.setPriorDayRainProbability(0.9);

        BurnDetermination weatherResult = BurnPlanEvaluationAlgorithm.evaluate(planWeather);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, weatherResult);

        BurnPlan planTemp = basePlan();
        planTemp.setTemperature(90.0);

        BurnDetermination tempResult = BurnPlanEvaluationAlgorithm.evaluate(planTemp);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_TEMPERATURE, tempResult);

        BurnPlan planWind = basePlan();
        planWind.setTemperature(60.0);
        planWind.setWindSpeed(25.0);

        BurnDetermination windResult = BurnPlanEvaluationAlgorithm.evaluate(planWind);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, windResult);

        BurnPlan planHumidity = basePlan();
        planHumidity.setTemperature(60.0);
        planHumidity.setWindSpeed(10.0);
        planHumidity.setHumidity(0.10);

        BurnDetermination humidityResult = BurnPlanEvaluationAlgorithm.evaluate(planHumidity);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, humidityResult);

        System.out.println("weatherResult = " + weatherResult);
        System.out.println("tempResult = " + tempResult);
        System.out.println("windResult = " + windResult);
        System.out.println("humidityResult = " + humidityResult);
    }


    @Test
    public void desired_whenAllConditionsGood() {
        BurnPlan plan = basePlan();
        plan.setColdFrontDate(null);
        plan.setPriorDayRainProbability(0.0);
        plan.setTemperature(50.0);
        plan.setWindSpeed(9.0);
        plan.setBlacklineWidth(101.0);

        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.DESIRED, result);
    }

    @Test
    public void desired_whenEnvironmentalConditionsIdeal() {
        BurnPlan plan = basePlan();
        plan.setColdFrontDate(null);


        plan.setTemperature(55.0);
        plan.setHumidity(0.50);
        plan.setWindSpeed(7.0);
        plan.setBlacklineWidth(101.0);

        BurnDetermination result = BurnPlanEvaluationAlgorithm.evaluate(plan);
        assertEquals(BurnDetermination.DESIRED, result);
    }


}