/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author marchauschildt
 */
public class CarTest {
    private int GOOD_MODEL_YEAR = 2020;
    private String GOOD_LICENSE_PLATE = "XXX 999";
    private String GOOD_MAKE = "Chevrolet";
    private String GOOD_MODEL = "Corvette";
    private Car instance;
    
    public CarTest() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new Car(GOOD_LICENSE_PLATE, GOOD_MAKE, GOOD_MODEL, GOOD_MODEL_YEAR);
    }

    /**
     * Test of getLicensePlate method, of class Car.
     */
    @Test
    public void testGetLicensePlate() {
        setUp();
        String expResult = "XXX 999";
        String result = instance.getLicensePlate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLicensePlate method, of class Car.
     */
    @Test
    public void testSetLicensePlate() {
        setUp();
        String licensePlace = "ABC 123";
        instance.setLicensePlate(licensePlace);
        String result = instance.getLicensePlate();
        assertEquals(licensePlace, result);
    }

    /**
     * Test of getMake method, of class Car.
     */
    @Test
    public void testGetMake() {
        setUp();
        String expResult = "Chevrolet";
        String result = instance.getMake();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMake method, of class Car.
     */
    @Test
    public void testSetMake() {
        setUp();
        String make = "Ford";
        instance.setMake(make);
        String result = instance.getMake();
        assertEquals(make, result);
    }

    /**
     * Test of getModel method, of class Car.
     */
    @Test
    public void testGetModel() {
        setUp();
        String expResult = "Corvette";
        String result = instance.getModel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setModel method, of class Car.
     */
    @Test
    public void testSetModel() {
        setUp();
        String model = "F-150";
        instance.setModel(model);
        String result = instance.getModel();
        assertEquals(model, result);
    }

    /**
     * Test of getModelYear method, of class Car.
     */
    @Test
    public void testGetModelYear() {
        setUp();
        int expResult = 2020;
        int result = instance.getModelYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setModelYear method, of class Car.
     */
    @Test
    public void testSetModelYearGoodArbitrary() {
        setUp();
        int modelYear = 1999;
        instance.setModelYear(modelYear);
        int result = instance.getModelYear();
        assertEquals(modelYear, result);
    }
    
    @Test
    public void testSetModelYearGoodMinimum() {
        setUp();
        int modelYear = 1893;
        instance.setModelYear(modelYear);
        int result = instance.getModelYear();
        assertEquals(modelYear, result);
    }
    
    @Test
    public void testSetModelYearBad1892() {
        setUp();
        int modelYear = 1891;
        try {
            instance.setModelYear(modelYear);
            fail("Bad model year");
        } catch(IllegalArgumentException iae) {
            assertTrue(true);
        }
    }

    /**
     * Test of getHighestAllowedModelYear method, of class Car.
     */
    @Test
    public void testGetHighestAllowedModelYear() {
        setUp();
        int expResult = 2021;
        int result = Car.getHighestAllowedModelYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Car.
     */
    @Test
    public void testToString() {
        setUp();
        String expResult = "2020 Chevrolet Corvette";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Car.
     */
    @Test
    public void testCompareToSorted() {
        setUp();
        Car other = new Car("ABC 123", "Dodge", "Viper", 2020); // 2020 Dodge Viper
        int expResult = -1;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCompareToUnsorted() {
        setUp();
        Car other = new Car("ABC 123", "BMW", "X7", 2020); // 2020 BMW X7
        int expResult = 1;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCompareToEqual() {
        setUp();
        Car other = new Car("ABC 123", "Chevrolet", "Corvette", 2020); // 2020 Chevrolet Corvette
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
    }
    
}
