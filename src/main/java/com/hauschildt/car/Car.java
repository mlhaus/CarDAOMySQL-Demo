/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car;

import java.time.LocalDate;

/**
 *
 * @author marchauschildt
 */
public class Car implements Comparable<Car> {

    public static final int MINIMUM_MODEL_YEAR = 1893;
    private String licensePlate;
    private String make;
    private String model;
    private int modelYear;

    public Car(String licensePlate, String make, String model, int modelYear) {
        validateModelYear(modelYear);
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
    }

    public Car(Car other){
        this.licensePlate = new String(other.getLicensePlate().toCharArray());
        this.make = new String(other.getMake().toCharArray());
        this.model = new String(other.getModel().toCharArray());
        this.modelYear = other.getModelYear();
    }
    
    /**
     * The no-argument constructor.  This constructor uses predefined default
     * values for the attributes.
     */
    public Car() {
        this("XXX-XXX", "UNKNOWN", "UNKNOWN", LocalDate.now().getYear());
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlace) {
        this.licensePlate = licensePlace;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        validateModelYear(modelYear);
        this.modelYear = modelYear;
    }
    
    public static int getHighestAllowedModelYear(){
        return LocalDate.now().getYear() + 1;
    }

    private void validateModelYear(int modelYear){
        if(Car.MINIMUM_MODEL_YEAR > modelYear){
            throw new IllegalArgumentException(
                    "Model Year cannot be earlier than 1893.");
        }
        if(Car.getHighestAllowedModelYear() < modelYear){
            throw new IllegalArgumentException("Model year cannot be later "
                    + "than " + getHighestAllowedModelYear() + ".");
        }
    }
    
    @Override
    public String toString() {
        return licensePlate + " " + modelYear + " " + make + " " + model;
    }

    @Override
    public int compareTo(Car other) {
        return this.toString().compareTo(other.toString());
    }

}
