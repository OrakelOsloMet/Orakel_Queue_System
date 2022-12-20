import QueueFormConnected from "../../components/Forms/QueueForm/queueFormConnected";
import QueueConnected from "../../components/Tables/Queue/queueConnected";

type Props = {
    isAuthenticated: boolean
}

const LandingPage = (props: Props) => {

    //Don't bother displaying the queue registration form for logged in Orakles
    return (
        <>
            {props.isAuthenticated ? null : <QueueFormConnected/>}
            <QueueConnected/>
        </>
    )
}

export default LandingPage;