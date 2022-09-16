import React from "react";
import QueueFormConnected from "../../components/Forms/QueueForm/queueFormConnected";
import QueueConnected from "../../components/Tables/Queue/queueConnected";

const LandingPage = () => {
    return (
        <>
            <QueueFormConnected/>
            <QueueConnected/>
        </>
    )
}

export default LandingPage;