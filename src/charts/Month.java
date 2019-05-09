/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

/**
 *
 * @author Edd993Surface
 */


public enum Month {

    JAN("January"),
    FEB("February"),
    MAR("March"),
    APR("April"),
    MAY("May"),
    JUN("June"),
    JUL("July"),
    AUG("August"),
    SEP("September"),
    OCT("October"),
    NOV("November"),
    DEC("December");

    private final String monthName;

    Month(String monthName) {
        this.monthName = monthName;
    }

    public String getName() {
        return monthName;
    }
}
