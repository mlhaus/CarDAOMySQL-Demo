/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car.data;

import com.hauschildt.car.Car;
import java.util.ArrayList;

/**
 *
 * @author marchauschildt
 */
public interface CarDAO {

    /**
     * Creates a new Car record based on the values in the supplied Car.
     *
     * @param car the Car object with the record values
     * @throws CarDataException
     */
    void createCarRecord(Car car) throws CarDataException;

    /**
     * Returns the Car record associated with the licensePlate or null if there
     * is no such car.
     *
     * @param licensePlate the identifier of the desired Car record
     * @return the associated Car or null if not found
     * @throws CarDataException
     */
    Car getCarByLicensePlate(String licensePlate) throws CarDataException;

    /**
     * Returns a list of all the current Car records.
     *
     * @return list of Car records
     * @throws CarDataException
     */
    ArrayList<Car> getAllCars() throws CarDataException;

    /**
     * Looks for the original Car and updates it to match the updated Car.  If
     * the original Car cannot be found, the method will throw an Exception.
     *
     * @param original The Car record to be updated
     * @param updated The Car containing the updated values
     * @throws CarDataException
     */
    void updateCar(Car original, Car updated) throws CarDataException;

    /**
     * Permanently deletes the supplied Car record
     *
     * @param car the Car to delete
     * @throws CarDataException
     */
    void deleteCar(Car car) throws CarDataException;

    /**
     * Permanently deletes the Car record with the supplied license plate value.
     *
     * @param licensePlate the unique identifier for the Car to be deleted
     * @throws CarDataException
     */
    void deleteCar(String licensePlate) throws CarDataException;


}
