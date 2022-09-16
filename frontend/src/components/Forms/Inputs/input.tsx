import React, {forwardRef} from "react";
import {ITextConfig, IValidatedTextConfig} from "../../../models/inputModels";

type Props = {
    inputConfig: ITextConfig | IValidatedTextConfig;
    onChange?: (event: React.FormEvent<HTMLInputElement>) => void;
    error: boolean;
};

const Input = forwardRef((props: Props, ref: React.Ref<any>) => {
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
            <input
                ref={ref} className={classnames} onChange={handleOnchange} key={props.inputConfig.key} {...props.inputConfig}
            />
        </>
    )
})

export default Input;