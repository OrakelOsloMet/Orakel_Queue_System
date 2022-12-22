import {RootState} from "../../../store";
import {bindActionCreators, Dispatch} from "redux";
import {deleteFromQueue, doneInQueue, fetchQueue} from "../../../store/actions/queueActions";
import {connect} from "react-redux";
import Queue from "./queue";

const mapStateToProps = (state: RootState) => {
    return {
        isAuthenticated: state.auth.user?.token != null,
        userRoles: state.auth.user ? state.auth.user.roles : [],
        queueData: state.queue.queueData,
        loading: state.queue.loading,
        error: state.queue.error
    }
};

const mapDispatchToProps = (dispatch: Dispatch) => {
    return bindActionCreators({
        deleteQueueEntity: deleteFromQueue,
        confirmDoneEntity: doneInQueue,
        pollingFunction: fetchQueue
    }, dispatch);
};

export default connect(mapStateToProps, mapDispatchToProps)(Queue);