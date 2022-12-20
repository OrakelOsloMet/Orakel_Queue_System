import {RootState} from "../../../store";
import {bindActionCreators, Dispatch} from "redux";
import {addToQueue} from "../../../store/actions/queueActions";
import {connect} from "react-redux";
import QueueForm from "./queueForm";
import {fetchPlacements, fetchSubjects} from "../../../store/actions/actionIndex";

const mapStateToProps = (state: RootState) => {
    return {
        placements: state.placements.placementData,
        subjects: state.subjects.currentSubjectData,
        loading: state.queue.loading,
        error: state.queue.error
    }
};

const mapDispatchToProps = (dispatch: Dispatch) => {
    return bindActionCreators({
        addQueueEntity: addToQueue,
        getPlacementData: fetchPlacements,
        getSubjectData: fetchSubjects
    }, dispatch);
};

export default connect(mapStateToProps, mapDispatchToProps)(QueueForm);