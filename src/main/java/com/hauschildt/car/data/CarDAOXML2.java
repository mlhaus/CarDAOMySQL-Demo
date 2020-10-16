/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car.data;

import com.hauschildt.car.Car;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author marchauschildt
 */
public class CarDAOXML2 implements CarDAO {
    private static final String FILE_NAME = "cars.xml";
    private static ArrayList<Car> cars;
    public final static String CAR_NAMESPACE = "http://www.hauschildt.com/car/v1";

    private void readFromFile() throws CarDataException {
        try (InputStream inputStream = new FileInputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);
            
            NodeList carNodeList = document.getElementsByTagName("car");
            cars = new ArrayList<>();
            for(int i = 0; i < carNodeList.getLength(); i++) {
                Node currentCarNode = carNodeList.item(i);
                cars.add(buildCarFromNode(currentCarNode));
            }
            
        }
        catch (IOException | SAXException | ParserConfigurationException ex) {
            throw new CarDataException(ex);
        }
    }
    
    private static Car buildCarFromNode(Node carNode) {
        Car newCar = new Car();
        NamedNodeMap carAttributeMap = carNode.getAttributes();
        Attr attr = (Attr)carAttributeMap.getNamedItem("license-plate");
        newCar.setLicensePlate(attr.getValue());
        
        NodeList carDataNodeList = carNode.getChildNodes();
        for(int i = 0; i < carDataNodeList.getLength(); i++) {
            Node dataNode = carDataNodeList.item(i);
            if(dataNode instanceof Element) {
                Element dataElement = (Element)dataNode;
                switch(dataElement.getTagName()) {
                    case "make":
                        String makeValue = dataElement.getTextContent();
                        newCar.setMake(makeValue);
                        break;
                    case "model":
                        String modelValue = dataElement.getTextContent();
                        newCar.setModel(modelValue);
                        break;
                    case "modelYear":
                        int modelYearValue = Integer.parseInt(dataElement.getTextContent());
                        newCar.setModelYear(modelYearValue);
                        break;
                    default:
                        break;
                }
            }
        }
        return newCar;
    }

    private void saveToFile() throws CarDataException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element rootElement = document.createElement("cars");
            document.appendChild(rootElement);
            
            for(Car currentCar: cars) {
                DocumentFragment carFragment = buildCarFragment(document, currentCar);
                rootElement.appendChild(carFragment);
            }
            
            DOMSource source = new DOMSource(document);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
//            transformer.transform(source, new StreamResult(System.out));
            transformer.transform(source, new StreamResult(fos));
            
        } catch(ParserConfigurationException | TransformerException | IOException ex) {
            throw new CarDataException(ex);
        }
    }
    
    private static DocumentFragment buildCarFragment(Document document, Car car) {
        DocumentFragment carFragment = document.createDocumentFragment();
        
        Element carElement = document.createElement("car");
        carElement.setAttribute("license-plate", car.getLicensePlate().toString());
        
        Element makeElement = createTextElement(document, "make", car.getMake());
        carElement.appendChild(makeElement);
        
        Element modelElement = createTextElement(document, "model", car.getModel());
        carElement.appendChild(modelElement);
        
        Element modelYearElement = createTextElement(document, "modelYear", Integer.toString(car.getModelYear()));
        carElement.appendChild(modelYearElement);
        
        carFragment.appendChild(carElement);
        return carFragment;
    }
    
    private static Element createTextElement(Document document, String localName, String value) {
        Element element = document.createElement(localName);
        element.setTextContent(value);
        return element;
    }
    
    private void verifyCarList() throws CarDataException {
        if(null == cars){
            readFromFile();
        }
    }

    @Override
    public void createCarRecord(Car car) throws CarDataException {
        verifyCarList();
        Car checkCar = getCarByLicensePlate(car.getLicensePlate());
        if(null != checkCar){
            throw new CarDataException("License Plates must be unique.");
        }
        cars.add(car);
        saveToFile();
    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) throws CarDataException {
        verifyCarList();
        Car car = null;
        for (Car car1 : cars) {
            if(car1.getLicensePlate().equals(licensePlate)){
                car = car1;
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
