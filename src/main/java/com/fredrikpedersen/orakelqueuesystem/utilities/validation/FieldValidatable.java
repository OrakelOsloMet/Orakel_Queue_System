package com.fredrikpedersen.orakelqueuesystem.utilities.validation;

import com.fredrikpedersen.orakelqueuesystem.utilities.annotations.Required;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 17:55
 */

//TODO Replace with Javax Validations
public interface FieldValidatable {

    default List<Field> requiredFields() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        ArrayList<Field> annotatedFields = new ArrayList<>();

        for(Field field : declaredFields) {
            Required annotation = field.getAnnotation(Required.class);
            if (annotation != null) {
                if (annotation.value()) {
                    field.setAccessible(true);
                    annotatedFields.add(field);
                }
            }
        }

        return annotatedFields;
    }
}
