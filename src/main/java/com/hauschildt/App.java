/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt;

import com.hauschildt.car.data.CarDAO;
import com.hauschildt.car.data.CarDAOFactory;
import com.hauschildt.car.data.CarDataException;
import com.hauschildt.taskHandler.AddCar;
import com.hauschildt.taskHandler.DeleteCar;
import com.hauschildt.taskHandler.FindACar;
import com.hauschildt.taskHandler.ShowAllCars;
import com.hauschildt.taskHandler.UpdateCar;
import com.hauschildt.ui.UIUtility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marchauschildt
 */
public class App {

    public static void main(String[] args) throws SQLException {
        UIUtility.showMessage("Program starting...");

        String menuTitle = "Main Menu";
        String[] menuOptions = {
            "1) Add a Car",
            "2) Find a Car",
            "3) Show All Cars",
            "4) Update a Car",
            "5) Delete a Car",
            "6) Exit"
        };
        String prompt = "Your choice:";
        String errorMessage = "Invalid option.  Please try again.";
        String userChoice;
        CarDAO dao = CarDAOFactory.getCarDAO();

        // Start the primary program logic
        boolean working = true;
        while (working) {
            userChoice = UIUtility.showMenuOptions(menuTitle,
                    prompt, menuOptions);
            switch (userChoice) {
                case "1":
                    new AddCar().handleTask(dao);
                    break;
                case "2":
                    new FindACar().handleTask(dao);
                    break;
                case "3":
                    new ShowAllCars().handleTask(dao);
                    break;
                case "4":
                    new UpdateCar().handleTask(dao);
                    break;
                case "5":
                    new DeleteCar().handleTask(dao);
                    break;
                case "6":
                    working = false;
                    break;
                default:
                    UIUtility.showErrorMessage(errorMessage, true);
            }
        }
        UIUtility.showMessage("\nProgram complete.");
    }

}
