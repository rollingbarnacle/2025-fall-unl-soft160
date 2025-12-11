package edu.unl.cse.soft160.xx.burnplan.evaluator;

public class Supply {
    private String name;
    private Integer quantity;


    public Supply(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    public Integer getSupplyQuantity() {
        return quantity;
    }

    public String getSupplyName() {
        return name;
    }
}