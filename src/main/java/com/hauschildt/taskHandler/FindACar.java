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
public class FindACar implements CarDataHandler{

    @Override
    public void handleTask(CarDAO dao) {

        UIUtility.showSectionTitle("Find a Car");

        String prompt = "Enter license plate:";
        String licensePlate = UIUtility.getUserString(prompt);
        UIUtility.showMessage("Searching for license plate " + licensePlate
                + "...");
        Car car;
        try{
            car = dao.getCarByLicensePlate(licensePlate);
            if(null == car){
                UIUtility.showMessage("No car found with license plate: "
                        + licensePlate);
            } else {
                UIUtility.showMessage("Retrieved Car: " + car);
            }
        } catch(CarDataException cde){
            UIUtility.showErrorMessage(cde.getMessage(), true);
        }
        UIUtility.showMessage("\nFind a Car complete.");
        UIUtility.pressEnterToContinue();
    }

    public Car getCarByLicensePlate(CarDAO dao, String licensePlate)
            throws CarDataException{
        return dao.getCarByLicensePlate(licensePlate);
    }

}
