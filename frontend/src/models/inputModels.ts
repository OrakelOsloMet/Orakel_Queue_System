import {FormElementType} from "../../../../OrakelQueueClient/src/constants/constants";

export type InputConfig = ITextConfig | IValidatedTextConfig | IRadioConfig | ISelectConfig;

/* ----- Base ----- */

interface IInputConfig {
    name: string;
    key?: string;
}

/* ----- Text Input ----- */

export interface ITextConfig extends IInputConfig {
    type: FormElementType.TEXT;
    placeholder?: string;
    defaultValue?: string;
}

export interface IValidatedTextConfig extends Omit<ITextConfig, 'type'> {
    type: FormElementType.VALIDATED_TEXT
    validation: IInputValidation
}

interface IInputValidation {
    minLength: number;
    errorMessage: string;
}

/* ----- Checkbox Input ----- */

export interface IRadioConfig extends IInputConfig {
    type: FormElementType.RADIO;
    buttons: Array<IRadioButton>;
}

export interface IRadioButton {
    label: string;
    value: number;
    key: string;
    defaultChecked: boolean;
}

/* ----- Select ----- */

export interface ISelectConfig extends IInputConfig {
    placeholderdisplayvalue?: string;
    type: FormElementType.SELECT;
    options: Array<ISelectOptions>;
}

interface ISelectOptions {
    value: number | string | boolean | object;
    displayValue: string;
}