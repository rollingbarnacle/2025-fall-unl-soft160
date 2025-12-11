package edu.unl.cse.soft160.xx.burnplan.evaluator;


public class CheckLand {
    private LandTypes land;

    public CheckLand(double latitude, double longitude, LandTypes landType, double acres) {
        this.land = landType;
    }

    public LandTypes getLand() {
        return land;
    }
}
