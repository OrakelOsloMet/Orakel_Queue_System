import {ISelectConfig} from "../../../models/inputModels"
import {UNSELECTED} from "../../../constants/constants";
import {UseFormRegisterReturn} from "react-hook-form";

type Props = {
    inputConfig: ISelectConfig;
    error?: boolean;
    register: UseFormRegisterReturn<any>;
};

const Select = (props: Props) => {
    const {inputConfig} = props;
    const placeholderDisplay = inputConfig.placeholderdisplayvalue;
    let classnames = "form-select";

    if (props.error) {
        classnames += "is-invalid ";
    }

    return (
        <>
            <select
                className={classnames}
                {...props.register}
                {...inputConfig}>
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
}

export default Select;