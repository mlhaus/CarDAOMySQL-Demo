/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.car.data;

/**
 *
 * @author marchauschildt
 */
public class CarDataException extends Exception {

    /**
     * Creates a new instance of <code>CarDataException</code> without detail
     * message.
     */
    public CarDataException() {
    }

    /**
     * Constructs an instance of <code>CarDataException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CarDataException(String msg) {
        super(msg);
    }

    public CarDataException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CarDataException(Throwable cause) {
        super(cause);
    }

    public CarDataException(String msg, Throwable cause
            , boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

}
