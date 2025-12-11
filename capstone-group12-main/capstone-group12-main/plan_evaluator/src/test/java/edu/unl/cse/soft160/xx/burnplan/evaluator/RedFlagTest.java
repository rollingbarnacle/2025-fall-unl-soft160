package edu.unl.cse.soft160.xx.burnplan.evaluator;
/*
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedFlagTest {

    @Test
    public void testDeclaredBurnBan() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 70.0, 48.0, "burn ban in effect");

        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }
    @Test
    public void testDeclaredBurningBan() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 70.0, 48.0, "burning ban declared");

        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }

    @Test
    public void testDeclaredFireBan() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 70.0, 48.0, "fire ban active");

        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }

    @Test
    public void testAllFiveRedFlagConditionsTrue() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(30.0, 10.0, 95.0, 11.0, "red flag warning");

        assertEquals(BurnDetermination.BURNING_PROHIBITED, result);
    }

    @Test
    public void testOnlyCondition1True() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(30.0, 50.0, 70.0, 48.0, "Clear skies");

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testOnlyCondition2True() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 10.0, 70.0, 48.0, "Clear skies");

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testOnlyCondition3True() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 95.0, 48.0, "Clear skies");

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testOnlyCondition4True() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 70.0, 12.0, "Clear skies");

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testOnlyCondition5True() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(10.0, 50.0, 70.0, 48.0, "red flag warning");

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testNullForecast() {
        RedFlag redFlag = new RedFlag();

        BurnDetermination result = redFlag.checkRedFlags(30.0, 10.0, 95.0, 12.0, null);

        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

}
 */