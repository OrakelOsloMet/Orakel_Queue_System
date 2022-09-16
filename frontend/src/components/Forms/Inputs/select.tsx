import React, {forwardRef} from "react";
import {ISelectConfig} from "../../../models/inputModels";

type Props = {
    inputConfig: ISelectConfig;
    onChange?: (event: React.FormEvent<HTMLInputElement>) => void;
    error?: boolean;
};

const Select = forwardRef((props: Props, ref: React.Ref<any>) => {
    let classnames = "form-control ml-1 mr-1 mt-3 mb-3 ";

    if (props.error) {
        classnames += "is-invalid ";
    }

    const handleOnchange = (event: any) => {
        if (props.onChange) {
            props.onChange(event);
        }
    }

    return (
        <>
            <select ref={ref} className={classnames} onChange={handleOnchange} {...props.inputConfig}>
                {props.inputConfig.options.map(option => {

                    //Enables use of serialized objects as values
                    const value = typeof option.value === "object" ? JSON.stringify(option.value) : String(option.value);

                    return (
                        <option key={option.displayValue} value={value}>
                            {option.displayValue}
                        </option>
                    )
                })}
            </select>
        </>
    )
})

export default Select;