package com.breugel.manu.projectfd.common;

/**
 * Created by MBR on 3/16/2015.
 */
public class UnknownFoodException extends ProjectFDRuntimeException {
    public UnknownFoodException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public UnknownFoodException() {
    }
}
