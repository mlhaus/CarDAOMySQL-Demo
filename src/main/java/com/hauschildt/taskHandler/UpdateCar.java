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
import java.util.List;

/**
 *
 * @author marchauschildt
 */
public class UpdateCar implements CarDataHandler {

    @Override
    public void handleTask(CarDAO dao) {

        UIUtility.showSectionTitle("Update a Car");

        try {
            List<Car> cars = dao.getAllCars();
            String[] menuOptions = new String[cars.size()];
            for (int i = 0; i < menuOptions.length; i++) {
                menuOptions[i] = (i + 1) + ") " + cars.get(i).toString();
            }
            String userChoice = UIUtility.showMenuOptions(
                    "Select a Car to Update",
                    "Your Choice:",
                    menuOptions);
            try {
                int actualChoice = Integer.parseInt(userChoice) - 1;
                Car car = cars.get(actualChoice);
                updateCar(dao, car);
                UIUtility.showMessage("Car updated successfully.");
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                UIUtility.showErrorMessage("No valid Car selected.", false);
            }

        } catch (CarDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }

        UIUtility.showMessage("\nUpdate a Car complete.");
        UIUtility.pressEnterToContinue();
    }
    
    private void updateCar(CarDAO dao, Car car) throws CarDataException {
        String msgUpdatingCar = "Updating Car: " + car;
        String msgLicensePlate = "License Plate: " + car.getLicensePlate();
        String msgCurrentMake = "Current Make: " + car.getMake();
        String msgCurrentModel = "Current Model: " + car.getModel();
        String msgCurrentModelYear
                = "Current Model Year: " + car.getModelYear();
        String current = " (Press Enter to keep the current value):";
        String promptEnterMake = "Enter the new make" + current;
        String prompEnterModel = "Enter the new model" + current;
        String promptModelYear = "Enter the new model year:";
        String modelYearError = "That is not a valid model year.";

        Car updated = new Car(car);
        UIUtility.showMessage(msgUpdatingCar);
        UIUtility.showMessage(msgLicensePlate);

        UIUtility.showMessage(msgCurrentMake);
        String updatedMake = UIUtility.getUserString(promptEnterMake);
        if (!updatedMake.isBlank()) {
            updated.setMake(updatedMake);
        }

        UIUtility.showMessage(msgCurrentModel);
        String updatedModel = UIUtility.getUserString(prompEnterModel);
        if (!updatedModel.isBlank()) {
            updated.setModel(updatedModel);
        }

        UIUtility.showMessage(msgCurrentModelYear);
        int updatedModelYear = UIUtility.getUserIntInRange(
                promptModelYear,
                modelYearError,
                Car.MINIMUM_MODEL_YEAR,
                Car.getHighestAllowedModelYear(),
                modelYearError
        );
        updated.setModelYear(updatedModelYear);

        dao.updateCar(car, updated);
    }

}
