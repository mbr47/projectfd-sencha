package com.breugel.manu.projectfd.common;

/**
 * Created by MBR on 3/16/2015.
 */
public class ProjectFDRuntimeException extends RuntimeException {

    public ProjectFDRuntimeException(String message) {
        super(message);
    }

    public ProjectFDRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectFDRuntimeException() {
    }
}
