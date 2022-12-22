import {FC, ReactNode} from "react";

type Props = {
    shadow: boolean;
    widthPercent: number;
    header: string;
    children: ReactNode
}

const Card: FC<Props> = (props) => {

    let styleClasses = "card bg-white mb-4 mt-4 ms-4 me-4 ";
    const width = `${String(props.widthPercent)}%`

    if (props.shadow) {
        styleClasses += "shadow rounded ";
    }

    return (
        <div className={styleClasses} style={{width: width}}>
            <div className={"card-header bg-warning"}>
                <h2><b>{props.header}</b></h2>
            </div>
            <div className={"card-body"}>
                {props.children}
            </div>
        </div>
    );

};

export default Card;