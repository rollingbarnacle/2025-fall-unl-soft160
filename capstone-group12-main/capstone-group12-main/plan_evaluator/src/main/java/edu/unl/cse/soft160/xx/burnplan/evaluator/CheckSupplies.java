package edu.unl.cse.soft160.xx.burnplan.evaluator;

import java.util.ArrayList;
import java.util.List;

public class CheckSupplies {
    private final List<Supply> supplies;

    public CheckSupplies() {
        this.supplies = new ArrayList<>();
    }

    public void addSupply(Supply supply) {
        supplies.add(supply);
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public BurnDetermination checkRequiredSupplies(double acres) {
        int requiredPumpers = (int) Math.ceil(acres / 80);
        double requiredFuel = acres / 10.0;
        int requiredDripTorches = (int) Math.ceil(requiredFuel / 10.0);
        int requiredTools = (int) Math.ceil(acres / 10.0);

        if (!hasRequiredSupply("pumpers", requiredPumpers)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!hasRequiredSupply("fire_starting_fuel", requiredFuel)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!hasRequiredSupply("drip_torches", requiredDripTorches)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!hasRequiredSupply("tools", requiredTools)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!hasRequiredSupply("backpack_pumps", 1)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!hasRequiredSupply("dozers", 1)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        return BurnDetermination.ACCEPTABLE;
    }

    private boolean hasRequiredSupply(String name, double requiredQuantity) {
        for (Supply supply : supplies) {
            if (supply.getSupplyName().equalsIgnoreCase(name)) {
                return supply.getSupplyQuantity() >= requiredQuantity;
            }
        }
        return false;
    }
}
