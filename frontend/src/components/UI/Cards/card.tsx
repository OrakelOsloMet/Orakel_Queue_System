import React, {FC} from "react";

type Props = {
    shadow: boolean;
    widthPercent: number;
    header: string;
}

const Card: FC<Props> = (props) => {

    let styleClasses = "card bg-white mb-4 mt-4 ml-4 mr-4 ";
    const width = `${String(props.widthPercent)}%`

    if (props.shadow) {
        styleClasses += "shadow rounded ";
    }

    return(
        <div className={styleClasses} style={{width: width}}>
            <div className={"card-header bg-warning"}><h2><b>{props.header}</b></h2></div>
            {props.children}
        </div>
    );

};

export default Card;