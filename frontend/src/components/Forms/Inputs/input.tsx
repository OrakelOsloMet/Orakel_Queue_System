import {FormEvent, forwardRef, Ref} from "react";
import {ITextConfig, IValidatedTextConfig} from "../../../models/inputModels"

type Props = {
    inputConfig: ITextConfig | IValidatedTextConfig;
    onChange?: (event: FormEvent<HTMLInputElement>) => void;
    error: boolean;
};

const Input = forwardRef((props: Props, ref: Ref<any>) => {
    let classnames = "form-control";

    if (props.error) {
        classnames += "is-invalid ";
    }

    const handleOnchange = (event: any) => {
        if (props.onChange) {
            props.onChange(event);
        }
    }

    return (
        <input ref={ref} className={classnames} onChange={handleOnchange} key={props.inputConfig.key} {...props.inputConfig}/>
    )
})

export default Input;