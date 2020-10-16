/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car.data;

import com.hauschildt.car.Car;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author marchauschildt
 */
public class CarDAOMySQL implements CarDAO {
    private static ArrayList<Car> cars;
    
    private Connection buildConnection() throws SQLException {
        String databaseUrl = "";
        String databasePort = "";
        String databaseName = "";
        String userName ="";
        String password = "";
        
        String connectionString = "jdbc:mysql://" + databaseUrl + ":" 
                        + databasePort + "/" + databaseName + "?"
                        + "user=" + userName + "&"
                        + "password=" + password + "&"
                        + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }

    private void readFromDatabase() throws CarDataException {
        try (Connection connection = buildConnection()) {
            if (connection.isValid(2)) {
                cars = new ArrayList<>();
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM Car;");
                CallableStatement callableStatement = 
                   connection.prepareCall("CALL sp_get_all_Car();");
                ResultSet resultSet = callableStatement.executeQuery();
                String licensePlate;
                String make;
                String model;
                int modelYear;
                while(resultSet.next()){
                    licensePlate =resultSet.getString("License_Plate");
                    make = resultSet.getString("Make");
                    model = resultSet.getString("Model");
                    modelYear = resultSet.getInt("Model_Year");
                    cars.add(new Car(licensePlate, make, model, modelYear));
                }
                resultSet.close();
                callableStatement.close();
//                statement.close();
            }
        } catch(Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                System.out.println("Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
            }
        }

    }
    
    private void verifyCarList() throws CarDataException {
        if(null == cars){
            readFromDatabase();
        }
    }

    @Override
    public void createCarRecord(Car car) throws CarDataException {
        // Verifies that the car isn't in the ArrayList before adding it
        verifyCarList();
        Car checkCar = getCarByLicensePlate(car.getLicensePlate());
        if(null != checkCar){
            throw new CarDataException("License Plates must be unique.");
        }
        cars.add(car);
        // Creates new car record in the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_add_Car(?,?,?,?);");
            callableStatement.setString(1, car.getLicensePlate());
            callableStatement.setString(2, car.getMake());
            callableStatement.setString(3, car.getModel());
            callableStatement.setInt(4, car.getModelYear());

            callableStatement.execute();

        } catch(SQLException ex){
            throw new CarDataException(ex);
        }
    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) throws CarDataException {
        Car car = null;
        // Use this code to look up the car from the ArrayList
        verifyCarList();
        for (Car car1 : cars) {
            if(car1.getLicensePlate().equals(licensePlate)){
                car = car1;
                break;
            }
        }
        // Use this code if you want to look the car up from a database query
//        try{
//            Connection conn = buildConnection();
//            CallableStatement callableStatement
//                    = conn.prepareCall("CALL sp_get_Car_by_License_Plate(?);");
//            callableStatement.setString(1, licensePlate);
//
//            ResultSet resultSet = callableStatement.executeQuery();
//            String make;
//            String model;
//            int modelYear;
//            if(resultSet.next()){
//                make = resultSet.getString("Make");
//                model = resultSet.getString("Model");
//                modelYear = resultSet.getInt("Model_Year");
//                car = new Car(licensePlate, make, model, modelYear);
//            }
//
//
//        } catch(SQLException ex){
//            throw new CarDataException(ex);
//        }
        return car;
    }

    @Override
    public ArrayList<Car> getAllCars() throws CarDataException {
        verifyCarList();
        return cars;
    }

    @Override
    public void updateCar(Car original, Car updated) throws CarDataException {
        // Verify that the original car is in the ArrayList before updating it
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.equals(original)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            throw new CarDataException("Original record not found for update.");
        }
        foundCar.setMake(updated.getMake());
        foundCar.setModel(updated.getModel());
        foundCar.setModelYear(updated.getModelYear());
        // Update the car in the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_update_Car(?,?,?,?,?);");
            callableStatement.setString(1, original.getLicensePlate());
            callableStatement.setString(2, updated.getLicensePlate());
            callableStatement.setString(3, updated.getMake());
            callableStatement.setString(4, updated.getModel());
            callableStatement.setInt(5, updated.getModelYear());

            callableStatement.execute();

        } catch(SQLException ex){
            throw new CarDataException(ex);
        }
    }

    @Override
    public void deleteCar(Car car) throws CarDataException {
        deleteCar(car.getLicensePlate());
    }

    @Override
    public void deleteCar(String licensePlate) throws CarDataException {
        // Verify that the car is in the ArrayList before removing it
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.getLicensePlate().equals(licensePlate)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            throw new CarDataException("Record not found for delete.");
        }
        String licensePlateToDelete = foundCar.getLicensePlate();
        cars.remove(foundCar);
        // Delete the car from the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_delete_from_Car2(?);");
            callableStatement.setString(1, licensePlateToDelete);
            callableStatement.execute();

        } catch(SQLException ex){
            throw new CarDataException(ex);
        }
    }
    
}