import styles from "./adminPage.module.css";
import Card from "../../components/UI/Cards/card";
import SubjectForm from "../../components/Forms/SubjectForm/subjectFormConnected";
import QueueExportForm from "../../components/Forms/QueueExportForm/queueExportForm";
import useAuth from "../../hooks/useAuth";

const AdminPage = () => {
    const user = useAuth();

    return (
        <>
            {!user ? <h1 style={{color: "red"}}>UNAUTHORIZED</h1> :
                <div className={"d-flex flex-row " + styles.adminPage}>
                    <Card widthPercent={25} shadow={true} header={"Subjects"}><SubjectForm/></Card>
                    <Card widthPercent={25} shadow={true} header={"Export Queue Data"}><QueueExportForm/></Card>
                </div>
            }
        </>
    )
}

export default AdminPage;