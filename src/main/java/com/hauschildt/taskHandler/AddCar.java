/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.taskHandler;

import com.hauschildt.car.Car;
import com.hauschildt.car.data.CarDAO;
import com.hauschildt.car.data.CarDataException;
import com.hauschildt.ui.UIUtility;

/**
 *
 * @author marchauschildt
 */
public class AddCar implements CarDataHandler {

    @Override
    public void handleTask(CarDAO dao) {
        UIUtility.showSectionTitle("Add a Car");
        Car car = new Car();
        car.setLicensePlate(
                UIUtility.getUserString("Enter the license plate:"));
        car.setMake(UIUtility.getUserString("Enter the make:"));
        car.setModel(UIUtility.getUserString("Enter the model:"));
        String prompt = "Enter the model year:";
        String notAnIntMessage = "That was not a whole number.";
        int modelYear;
        boolean needed = true;
        while(needed){
            try{
                car.setModelYear(UIUtility.getUserInt(prompt, notAnIntMessage));
                needed = false;
            } catch(IllegalArgumentException iae){
                UIUtility.showErrorMessage(iae.getMessage(), true);
            }
        }
        
        try{
            dao.createCarRecord(car);
            UIUtility.showMessage("Car added: " + car);
        } catch (CarDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), false);
        }
        UIUtility.showMessage("Add Car Complete");
        UIUtility.pressEnterToContinue();
    }

}
