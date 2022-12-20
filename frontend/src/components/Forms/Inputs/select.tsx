import {FormEvent, forwardRef, Ref} from "react";
import {ISelectConfig} from "../../../models/inputModels"
import {UNSELECTED} from "../../../constants/constants";

type Props = {
    inputConfig: ISelectConfig;
    onChange?: (event: FormEvent<HTMLInputElement>) => void;
    error?: boolean;
};

const Select = forwardRef((props: Props, ref: Ref<any>) => {
    const {onChange, inputConfig} = props;
    const placeholderDisplay = inputConfig.placeholderdisplayvalue;
    let classnames = "form-select";

    if (props.error) {
        classnames += "is-invalid ";
    }

    const handleOnchange = (event: any) => {
        if (onChange) {
            onChange(event);
        }
    }

    return (
        <>
            <select ref={ref} className={classnames} onChange={handleOnchange} {...inputConfig}>
                {placeholderDisplay ? <option value={UNSELECTED}>{placeholderDisplay}</option> : null}
                {inputConfig.options.map(option => {

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