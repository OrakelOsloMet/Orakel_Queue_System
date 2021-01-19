package com.fredrikpedersen.orakelqueuesystem.utilities.exceptions;

/**
 *
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 17:24
 */

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(final String message) {super(message);}
}
