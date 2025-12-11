package edu.unl.cse.soft160.xx.burnplan.evaluator;
/*
import edu.unl.cse.soft160.xx.burnplan.evaluator.BurnDetermination;
import edu.unl.cse.soft160.xx.burnplan.evaluator.TempWindHumidity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TempWindHumidityTest {

    @Test
    public void testTemperatureTooHigh() {
        TempWindHumidity tempWindHumidity = new TempWindHumidity();

        BurnDetermination result = tempWindHumidity.checkTempWindHumidity(85.0, 15.0, 0.30);

        assertEquals(BurnDetermination.NOT_RECOMMENDED_TEMPERATURE, result);
    }

    @Test
    public void testWindSpeedTooHigh() {
        TempWindHumidity tempWindHumidity = new TempWindHumidity();

        BurnDetermination result = tempWindHumidity.checkTempWindHumidity(75.0, 25.0, 0.30);

        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, result);
    }

    @Test
    public void testHumidityTooLow() {
        TempWindHumidity tempWindHumidity = new TempWindHumidity();

        BurnDetermination result = tempWindHumidity.checkTempWindHumidity(75.0, 15.0, 0.15);

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testAllConditionsAcceptable() {
        TempWindHumidity tempWindHumidity = new TempWindHumidity();

        BurnDetermination result = tempWindHumidity.checkTempWindHumidity(75.0, 15.0, 0.30);

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }
}
 */