package com.sb.services.calculation;

/**
 * Created by Kingsley Kumar on 02/11/2016.
 */
public class ResultNodeBudget extends ResultNode {

    private double allocatedAmount = 0;
    private double remainingAmount = 0;

    public ResultNodeBudget(String name) {
        super(name);
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }


    protected ResultNodeBudget createResultNode(String name) {

        return new ResultNodeBudget(name);
    }
}
