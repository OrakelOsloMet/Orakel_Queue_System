package com.fredrikpedersen.orakelqueuesystem.utilities.validation;

import com.fredrikpedersen.orakelqueuesystem.utilities.annotations.Required;
import com.fredrikpedersen.orakelqueuesystem.utilities.exceptions.RequiredFieldException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 17:23
 */

public class FieldValidator {

    /**
     * Method for parsing through all fields annotated with @Required in a Fieldvalidatable object, throwing a
     * RequiredFieldException if any annotated fields contains null values.
     *
     * @param object to have it's fields be validated
     * @throws RequiredFieldException if there are any null values
     * @see Required
     */
    public static boolean validateForNulls(FieldValidatable object) {
        List<Field> annotatedFields = object.requiredFields();

        annotatedFields.forEach(field -> {
            try {
                if (field.get(object) == null) {
                    throw new RequiredFieldException(object.getClass().getName() + "." + field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RequiredFieldException(object.getClass().getName() + "'s " + field.getName() + "-field is not accessible");
            }
        });

        return true;
    }
}
