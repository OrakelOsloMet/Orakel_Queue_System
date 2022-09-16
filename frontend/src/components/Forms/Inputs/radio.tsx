import React, {forwardRef} from "react";
import {IRadioConfig} from "../../../models/inputModels";

type Props = {
    inputConfig: IRadioConfig;
    onChange?: (event: React.FormEvent<HTMLInputElement>) => void;
    error?: boolean;
};

const Radio = forwardRef((props: Props, ref: React.Ref<any>) => {
    let classnames = "form-check form-check-inline ml-2 mr-2 mt-2 mb-4 ";

    if (props.error) {
        classnames += "is-invalid ";
    }

    const handleOnchange = (event: any) => {
        if (props.onChange) {
            props.onChange(event);
        }
    }

    const radioDivs: Array<JSX.Element> = [];
    props.inputConfig.buttons.forEach(button => {
        radioDivs.push(
            <div key={button.label} className={classnames}>
                <input
                    key={`${button.key}radio${button.value}`}
                    value={button.value}
                    className={"form-check-input "}
                    ref={ref}
                    defaultChecked={button.defaultChecked}
                    onChange={handleOnchange}
                    {...props.inputConfig}/>
                <label className={"form-check-label"}>{button.label}</label>
            </div>);
    })

    return (
        <>
            {radioDivs}
        </>
    )
})

export default Radio;

