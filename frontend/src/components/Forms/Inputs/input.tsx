import {ITextConfig, IValidatedTextConfig} from "../../../models/inputModels"
import {UseFormRegisterReturn} from "react-hook-form";

type Props = {
    inputConfig: ITextConfig | IValidatedTextConfig;
    error: boolean;
    register: UseFormRegisterReturn<any>;
};

const Input = (props: Props) => {
    let classnames = "form-control ";

    if (props.error) {
        classnames += "is-invalid ";
    }

    return (
        <input {...props.register} className={classnames} key={props.inputConfig.key} {...props.inputConfig}/>
    )
}

export default Input;