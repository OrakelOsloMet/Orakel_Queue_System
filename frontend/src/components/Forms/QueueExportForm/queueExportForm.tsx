import {FILE_DOWNLOAD_CONFIG, FILE_INSTANCE as axios} from "../../../axiosApi";
import {SubmitButton} from "../../UI/Buttons/buttons";
import {QUEUE_EXPORT_PATH} from "../../../constants/constants";
import authHeader from "../../../httpHeaders/authHeader"

const QueueExportForm = () => {

    const getQueueExport = () => {
        axios.get(QUEUE_EXPORT_PATH, {headers: authHeader()})
            .then(response => {
                FILE_DOWNLOAD_CONFIG("queueData", "csv", response.data);
            })
    }

    return (
        <div style={{margin: "auto"}}>
            <SubmitButton onClick={getQueueExport}>Export</SubmitButton>
        </div>);
};

export default QueueExportForm;