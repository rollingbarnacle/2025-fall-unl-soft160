package edu.unl.cse.soft160.xx.burnplan.evaluator;
/*
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckSupplyTest {



    @Test
    public void testAddSupply() {
        CheckSupplies checkSupplies = new CheckSupplies();
        Supply pumper = new Supply("Pumper", 3);
        checkSupplies.addSupply(pumper);

        assertEquals(1, checkSupplies.getSupplies().size());
        assertEquals("Pumper", checkSupplies.getSupplies().get(0).getSupplyName());
    }

    @Test
    public void testAddMultipleSupplies() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 3));
        checkSupplies.addSupply(new Supply("Rake", 10));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(3, checkSupplies.getSupplies().size());
    }

    @Test
    public void testGetSuppliesReturnsEmptyList() {
        CheckSupplies checkSupplies = new CheckSupplies();
        assertEquals(0, checkSupplies.getSupplies().size());
    }


    @Test
    public void testAllRequiredSuppliesPresent_SmallBurn() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.ACCEPTABLE,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testAllRequiredSuppliesPresent_LargeBurn() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 3));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 4));
        checkSupplies.addSupply(new Supply("Drip torch", 2));
        checkSupplies.addSupply(new Supply("Rake", 20));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.ACCEPTABLE,
                checkSupplies.checkRequiredSupplies(200));
    }

    @Test
    public void testFireSwattersInsteadOfRakes() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Fire swatter", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.ACCEPTABLE,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testCaseInsensitiveSupplyNames() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("pumper", 1));
        checkSupplies.addSupply(new Supply("FIRE-STARTING FUEL", 2));
        checkSupplies.addSupply(new Supply("Drip Torch", 1));
        checkSupplies.addSupply(new Supply("rake", 8));
        checkSupplies.addSupply(new Supply("BACKPACK PUMP", 1));
        checkSupplies.addSupply(new Supply("dozer", 1));

        assertEquals(BurnDetermination.ACCEPTABLE,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testMissingPumper() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testInsufficientPumpers() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 2));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 4));
        checkSupplies.addSupply(new Supply("Drip torch", 2));
        checkSupplies.addSupply(new Supply("Rake", 20));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(200));
    }

    @Test
    public void testMissingFireStartingFuel() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }



    @Test
    public void testMissingDripTorches() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testInsufficientDripTorches() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 3));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 4));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 20));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(200));
    }

    @Test
    public void testMissingRakesAndFireSwatters() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testInsufficientRakes() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 3));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 4));
        checkSupplies.addSupply(new Supply("Drip torch", 2));
        checkSupplies.addSupply(new Supply("Rake", 15));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(200));
    }

    @Test
    public void testMissingBackpackPump() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Dozer", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }

    @Test
    public void testMissingDozer() {
        CheckSupplies checkSupplies = new CheckSupplies();
        checkSupplies.addSupply(new Supply("Pumper", 1));
        checkSupplies.addSupply(new Supply("Fire-starting fuel", 2));
        checkSupplies.addSupply(new Supply("Drip torch", 1));
        checkSupplies.addSupply(new Supply("Rake", 8));
        checkSupplies.addSupply(new Supply("Backpack pump", 1));

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,
                checkSupplies.checkRequiredSupplies(80));
    }


}
 */