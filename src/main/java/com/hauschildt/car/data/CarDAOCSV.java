/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car.data;

import com.hauschildt.car.Car;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author marchauschildt
     */
public class CarDAOCSV implements CarDAO {

    private static final String FILE_NAME = "cars.csv";
    private static ArrayList<Car> cars;

    private void readFromFile() throws CarDataException {
        try(Scanner in = new Scanner(new File(FILE_NAME))){
            cars = new ArrayList<>();
            int lineCount = 0;
            String line;
            String[] fields;
            String licensePlate;
            String make;
            String model;
            int modelYear;
            while(in.hasNextLine()){
                lineCount++;
                line = in.nextLine();
                fields = line.split(",");
                licensePlate = fields[0];
                make = fields[1];
                model = fields[2];
                try{
                    modelYear = Integer.parseInt(fields[3]);
                } catch(NumberFormatException nfe){
                    throw new CarDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                cars.add(new Car(licensePlate, make, model, modelYear));
            }
        } catch(FileNotFoundException fnfe){
            throw new CarDataException(fnfe);
        }
    }

    private void saveToFile() throws CarDataException {
        try(PrintWriter writer = new PrintWriter(new File(FILE_NAME))){
            String line;
            for (Car car : cars) {
                line = car.getLicensePlate() + ","
                        + car.getMake() + ","
                        + car.getModel() + ","
                        + car.getModelYear();
                writer.println(line);
            }
        } catch (FileNotFoundException ex) {
            throw new CarDataException(ex);
        }
    }
    
    private void verifyCarList() throws CarDataException {
        if(null == cars){
            readFromFile();
        }
    }

    @Override
    public void createCarRecord(Car car) throws CarDataException {
        verifyCarList();
        // Look to see if there is already a car with the same license plate
        // value
        Car checkCar = getCarByLicensePlate(car.getLicensePlate());
        // If there was a matching car, throw an exception.  The license plate
        // is used as a unique identifier in this example.
        if(null != checkCar){
            throw new CarDataException("License Plates must be unique.");
        }
        // No other car has the same license plate, so we can add this Car to
        // the data store.
        cars.add(car);
        saveToFile();
    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) throws CarDataException {
        verifyCarList();
        Car car = null;
        for (Car car1 : cars) {
            // See if the car has a matching license plate
            if(car1.getLicensePlate().equals(licensePlate)){
                // found a match, so it is the car we want
                car = car1;
                // leave the loop
                break;
            }
        }
        return car;
    }

    @Override
    public ArrayList<Car> getAllCars() throws CarDataException {
        verifyCarList();
        return cars;
    }

    @Override
    public void updateCar(Car original, Car updated) throws CarDataException {
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.equals(original)){
                // found a match!
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            // did not find a match to the original!
            throw new CarDataException("Original record not found for update.");
        }
        // If no error, update all but the unique identifier
        foundCar.setMake(updated.getMake());
        foundCar.setModel(updated.getModel());
        foundCar.setModelYear(updated.getModelYear());
        saveToFile();
    }

    @Override
    public void deleteCar(Car car) throws CarDataException {
        deleteCar(car.getLicensePlate());
    }

    @Override
    public void deleteCar(String licensePlate) throws CarDataException {
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.getLicensePlate().equals(licensePlate)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            // did not find a match to the original!
            throw new CarDataException("Record not found for delete.");
        }
        // If no error, update all but the unique identifier
        cars.remove(foundCar);
        saveToFile();
    }

}
