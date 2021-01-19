package com.fredrikpedersen.orakelqueuesystem.utilities.validation;

import com.fredrikpedersen.orakelqueuesystem.utilities.annotations.Required;
import com.fredrikpedersen.orakelqueuesystem.utilities.exceptions.RequiredFieldException;

import java.lang.reflect.Field;

/**
 * Class for validating fields in our DTOs. Uses reflection, so performance wise isn't the most optimal way to do this.
 *
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 17:23
 */

public class FieldValidator {

    /**
     * Method for parsing through all declared fields in an object, checking if they have any null values on
     * required fields. This is done by annotating fields with @Required.
     *
     * @param object to have it's fields be validated
     * @throws RequiredFieldException if there are any null values
     * @see Required
     */
    public static boolean validateForNulls(Object object) {

        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {

            Required annotation = field.getAnnotation(Required.class);
            if (annotation != null) {

                if (annotation.value()) {
                    field.setAccessible(true);

                    try {
                        if (field.get(object) == null) {
                            throw new RequiredFieldException(object.getClass().getName() + "." + field.getName());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RequiredFieldException(object.getClass().getName() + "'s " + field.getName() + "-field is not accessible");
                    }
                }
            }
        }
        return true;
    }
}
