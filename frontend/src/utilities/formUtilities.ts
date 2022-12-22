import {InputConfig} from "../models/inputModels";
import {DeepMap, FieldError, UseFormRegister} from "react-hook-form";
import {FormElementType} from "../constants/constants";


//Add cases to this function if more input types in need of validation are added.
export const configureRegister = (inputConfig: InputConfig, register: UseFormRegister<any>, onChange?: (event: any) => void) => {
    switch (inputConfig.type) {
        case FormElementType.VALIDATED_TEXT:
            return  register(
                inputConfig.name, {
                onChange: onChange,
                required: inputConfig.validation.errorMessage,
                minLength: {
                    value: inputConfig.validation.minLength,
                    message: inputConfig.validation.errorMessage
                }
            })

        default:
            return register(inputConfig.name, {
                onChange: onChange
            });
    }
}

export const inputHasError = (errors: DeepMap<Record<string, any>, FieldError>, inputConfig: InputConfig) => {
    let errorInInput = false;
    for (const key of Object.entries(errors)) {
        if (key[0] === inputConfig.name) {
            errorInInput = true;
        }
    }

    return errorInInput;
}