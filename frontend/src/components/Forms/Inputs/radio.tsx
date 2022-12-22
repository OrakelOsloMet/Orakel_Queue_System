import {IRadioConfig} from "../../../models/inputModels"
import {UseFormRegisterReturn} from "react-hook-form";

type Props = {
    inputConfig: IRadioConfig;
    className?: string;
    error?: boolean;
    register: UseFormRegisterReturn<any>
};

const Radio = (props: Props) => {
    let classnames = "form-check form-check-inline " + props.className + " ";

    if (props.error) {
        classnames += "is-invalid ";
    }

    const radioDivs: Array<JSX.Element> = [];
    props.inputConfig.buttons.map((button, index) => {
        radioDivs.push(
            <div key={button.label + index} className={classnames}>
                <input
                    key={`${button.key}radio${index}`}
                    value={button.value}
                    className={"form-check-input "}
                    defaultChecked={button.defaultChecked}
                    {...props.register}
                    {...props.inputConfig}/>
                <label className={"form-check-label"}>{button.label}</label>
            </div>);
    })

    return (
        <>
            {radioDivs}
        </>
    )
}

export default Radio;

