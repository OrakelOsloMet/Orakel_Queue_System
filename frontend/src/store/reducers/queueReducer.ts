import {QueueActionTypes} from "../actions/actionTypes";
import {updateObject} from "../../utilities/objectUtilities";
import {QueueAction, QueueState} from "../types";

const initialState: QueueState = {
    queueData: [],
    error: null,
    loading: false
};

const initAction = (state: QueueState, action: QueueAction): QueueState => {
    return updateObject(state, {error: null, loading: true});
};

const failedAction = (state: QueueState, action: QueueAction): QueueState => {
    return updateObject(state, {
        error: action.error,
        loading: false
    })
};

/* ----- Fetch LandingPage Data ----- */

const fetchQueueSuccess = (state: QueueState, action: QueueAction): QueueState => {
    return updateObject(state, {
        queueData: action.queueData,
        error: null,
        loading: false
    });
};


/* ----- Add, Delete and Remove in LandingPage ----- */

const addRemoveSuccess = (state: QueueState, action: QueueAction): QueueState => {
    return updateObject(state, {
        error: null,
        loading: false
    })
};

const reducer = (state: QueueState = initialState, action: QueueAction): QueueState => {
    switch (action.type) {

        //Start cases
        case QueueActionTypes.FETCH_QUEUE_START:
        case QueueActionTypes.ADD_TO_QUEUE_START:
        case QueueActionTypes.DELETE_FROM_QUEUE_START:
        case QueueActionTypes.DONE_IN_QUEUE_START:
            return initAction(state, action);

        //Fail cases
        case QueueActionTypes.FETCH_QUEUE_FAIL:
        case QueueActionTypes.ADD_TO_QUEUE_FAIL:
        case QueueActionTypes.DELETE_FROM_QUEUE_FAIL:
        case QueueActionTypes.DONE_IN_QUEUE_FAIL:
            return failedAction(state, action);

        //Success cases
        case QueueActionTypes.FETCH_QUEUE_SUCCESS:
            return fetchQueueSuccess(state, action);

        case QueueActionTypes.ADD_TO_QUEUE_SUCCESS:
        case QueueActionTypes.DELETE_FROM_QUEUE_SUCCESS:
        case QueueActionTypes.DONE_IN_QUEUE_SUCCESS:
            return addRemoveSuccess(state, action);

        default:
            return state;
    }
};

export default reducer;
