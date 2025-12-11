package edu.unl.cse.soft160.xx.burnplan.evaluator;
/*
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BurnDateTest {

    @Test
    public void testThreeDaysInFuture() {
        BurnDate burnDate = new BurnDate();
        LocalDate threeDaysAhead = LocalDate.now().plusDays(3);
        assertEquals(BurnDetermination.ACCEPTABLE,
                burnDate.checkDate(threeDaysAhead));
    }

    @Test
    public void testFourDaysInFuture() {
        BurnDate burnDate = new BurnDate();
        LocalDate fourDaysAhead = LocalDate.now().plusDays(4);
        assertEquals(BurnDetermination.ACCEPTABLE,
                burnDate.checkDate(fourDaysAhead));
    }

    @Test
    public void testToday() {
        BurnDate burnDate = new BurnDate();
        LocalDate today = LocalDate.now();
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(today));
    }

    @Test
    public void testTomorrow() {
        BurnDate burnDate = new BurnDate();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(tomorrow));
    }

    @Test
    public void testTwoDaysInFuture() {
        BurnDate burnDate = new BurnDate();
        LocalDate twoDaysAhead = LocalDate.now().plusDays(2);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(twoDaysAhead));
    }

    @Test
    public void testYesterday() {
        BurnDate burnDate = new BurnDate();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(yesterday));
    }

    @Test
    public void testOneWeekInPast() {
        BurnDate burnDate = new BurnDate();
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(oneWeekAgo));
    }

    @Test
    public void testFiveDaysInFuture() {
        BurnDate burnDate = new BurnDate();
        LocalDate fiveDaysAhead = LocalDate.now().plusDays(5);
        assertEquals(BurnDetermination.ACCEPTABLE,
                burnDate.checkDate(fiveDaysAhead));
    }

    @Test
    public void testSixDaysInFuture() {
        BurnDate burnDate = new BurnDate();
        LocalDate sixDaysAhead = LocalDate.now().plusDays(6);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                burnDate.checkDate(sixDaysAhead));
    }
}
 */