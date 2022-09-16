import React, { FC, useEffect } from "react";

import Table from "./QueueTable/queueTable";
import { jsonArrayEqual } from "../../../utilities/arrayUtilities";
import LoadingSpinner from "../../UI/LoadingSpinner/loadingSpinner";
import useInterval from "../../../hooks/useInterval";
import usePreviousState from "../../../hooks/usePreviousState";
import useSound from "use-sound";
import { IQueueEntity } from "../../../models/types";

const notificationSound = require("../../../assets/sounds/hellothere.mp3");

type Props = {
    isAuthenticated: boolean;
    userRoles: Array<string>;
    queueData: Array<IQueueEntity>;
    loading: boolean;
    error: string | null;
    deleteQueueEntity: (id: number) => void;
    confirmDoneEntity: (id: number) => void;
    pollingFunction: () => void;
}

//TODO Replace polling function with websockets.
//TODO Queue data is not necessary to have in Redux, is only used in this one component.
const Queue: FC<Props> = (props) => {
    const { isAuthenticated, userRoles, queueData, deleteQueueEntity, confirmDoneEntity, pollingFunction } = props;
    const [play] = useSound(notificationSound)
    const previousQueue = usePreviousState(queueData) as unknown as Array<IQueueEntity>

    //Use effect only to be triggered when the component is first rendered.
    useEffect(() => {
        pollingFunction();
    }, [pollingFunction])

    //Make the LandingPage update a 5 second interval
    useInterval(() => {
        pollingFunction()
    }, 5000);

    //Play a notification sound if a new person has been added to the queue
    useEffect(() => {

        //Due to the API taking a few ms to respond, previousQueue will be undefined in the first render cycle.
        if (previousQueue && isAuthenticated) {
            if (queueData.length >= previousQueue.length) {
                if (!jsonArrayEqual(queueData, previousQueue)) {
                    play();
                }
            }
        }
    }, [queueData, isAuthenticated, play, previousQueue])

    /* ----- Create Table ----- */
    let table = queueData === undefined ? <LoadingSpinner /> : <Table
        defaultColumns={["# i kø", "Navn", "Emne", "Plassering", "Kommentar"]}
        loggedInColumns={["Handlinger"]}
        queueData={queueData}
        isAuthenticated={isAuthenticated}
        userRoles={userRoles}
        confirmDoneEntity={confirmDoneEntity}
        deleteQueueEntity={deleteQueueEntity}
    />;

    return (
        <>
            {table}
        </>
    );

}

export default Queue