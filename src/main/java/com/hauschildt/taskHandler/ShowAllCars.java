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
public class ShowAllCars implements CarDataHandler {

    @Override
    public void handleTask(CarDAO dao) {
        UIUtility.showSectionTitle("Show All Cars");

        try {
            List<Car> cars = dao.getAllCars();
            for (Car car : cars) {
                UIUtility.showMessage("\t" + car);
            }
        } catch (CarDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }

        UIUtility.showMessage("\nShow All Cars complete.");
        UIUtility.pressEnterToContinue();

    }

    public List<Car> getAllCars(CarDAO dao) throws CarDataException{
        return dao.getAllCars();
    }
}
