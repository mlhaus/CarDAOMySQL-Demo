/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.taskHandler;

import com.hauschildt.car.data.CarDAO;

/**
 *
 * @author marchauschildt
 */
public interface CarDataHandler {
    void handleTask(CarDAO dao);
}
