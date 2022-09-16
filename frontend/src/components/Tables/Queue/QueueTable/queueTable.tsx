import React from "react";
import {Table} from "react-bootstrap";
import TableHead from "../../TableHead/tableHead";
import {ConfirmButton, DeleteButton} from "../../../UI/Buttons/buttons";
import {IQueueEntity} from "../../../../models/types";

type Props = {
    queueData: Array<IQueueEntity>,
    confirmDoneEntity: (id: number) => void,
    deleteQueueEntity: (id: number) => void,
    isAuthenticated: boolean,
    userRoles: Array<string>,
    defaultColumns: Array<string>,
    loggedInColumns: Array<string>
}

const queueTable = (props: Props) => {

    /* ----- Create Table Body ----- */
    const {queueData, confirmDoneEntity, deleteQueueEntity, isAuthenticated, userRoles, defaultColumns, loggedInColumns} = props;
    let rows = [];

    for (let i = 0; i < queueData.length; i++) {
        const rowId = "row" + i;
        const cells = [];

        const queueEntity = queueData[i];
        const entityPlacement = queueEntity.placement;
        const placement = entityPlacement?.name === "Discord" ? "Discord" : entityPlacement?.name + " " + entityPlacement?.number;
        const comment = queueEntity.comment === null ? "<ingen kommentar>" : queueEntity.comment

        cells.push(<td key={"entry" + i} id={"entry" + i}># {i + 1}</td>);
        cells.push(<td key={"name" + i} id={"name" + i}>{queueEntity.name}</td>);
        cells.push(<td key={"subject" + i} id={"subject" + i}>{queueEntity.subject.name}</td>);
        cells.push(<td key={"placement" + i} id={"placement" + i}>{placement}</td>);
        cells.push(<td key={"comment" + i} id={"comment" + i}>{comment}</td>);

        if (isAuthenticated && userRoles.includes("ROLE_ADMIN")) {
                cells.push(<td key={"actions" + i} id={"action" + i}>{
                    <>
                        <ConfirmButton onClick={() => confirmDoneEntity(queueData[i].id)}>Ferdig</ConfirmButton>
                        <DeleteButton className="ml-2" onClick={() => deleteQueueEntity(queueData[i].id)}>Slett</DeleteButton>
                    </>
                }</td>);
        }
        rows.push(<tr key={i} id={rowId}>{cells}</tr>);
    }

    const tableHead = <TableHead defaultColumns={defaultColumns} loggedInColumns={loggedInColumns} isAuthenticated={isAuthenticated}/>;
    const tableBody = <tbody>{rows}</tbody>;

    return (
        <Table striped bordered hover responsive className={"mb-4 bg-white"}>{tableHead}{tableBody}</Table>
    );
};

export default queueTable;