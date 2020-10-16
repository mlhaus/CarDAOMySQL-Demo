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
public class DeleteCar implements CarDataHandler {

    @Override
    public void handleTask(CarDAO dao) {
        UIUtility.showSectionTitle("Delete a Car");
        try {
            List<Car> cars = dao.getAllCars();
            String[] menuOptions = new String[cars.size()];
            for (int i = 0; i < menuOptions.length; i++) {
                menuOptions[i] = (i + 1) + ") " + cars.get(i).toString();
            }
            String userChoice = UIUtility.showMenuOptions(
                    "Select a Car to Delete",
                     "Your Choice:",
                     menuOptions);
            try {
                int actualChoice = Integer.parseInt(userChoice) - 1;
                Car car = cars.get(actualChoice);
                dao.deleteCar(car);
                UIUtility.showMessage("Car deleted successfully.");
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                UIUtility.showErrorMessage("No valid Car selected.", false);
            }

        } catch (CarDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }
        UIUtility.showMessage("\nDelete a Car complete.");
        UIUtility.pressEnterToContinue();

    }

}
