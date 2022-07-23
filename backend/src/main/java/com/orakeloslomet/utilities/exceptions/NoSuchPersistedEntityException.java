package com.orakeloslomet.utilities.exceptions;

import java.util.NoSuchElementException;

public class NoSuchPersistedEntityException extends RuntimeException {

    public NoSuchPersistedEntityException(final String message) {
        super(message);
    }
}
