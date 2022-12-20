import {SubjectActionTypes} from "../../../../../OrakelQueueClient/src/store/actions/actionTypes";
import {updateObject} from "../../../../../OrakelQueueClient/src/utilities/objectUtilities";
import {SubjectAction, SubjectState} from "../../../../../OrakelQueueClient/src/store/types";

const initialState: SubjectState = {
    allSubjectData: [],
    currentSubjectData: [],
    error: null,
    loading: false
};

const initAction = (state: SubjectState, action: SubjectAction): SubjectState => {
    return updateObject(state, {error: null, loading: true});
};

const failedAction = (state: SubjectState, action: SubjectAction): SubjectState => {
    return updateObject(state, {
        error: action.error,
        loading: false
    })
};

/* ----- Fetch Subject Data ----- */

const fetchSubjectsSuccess = (state: SubjectState, action: SubjectAction): SubjectState => {

    if (action.allSubjectData) {
        return updateObject(state, {
            allSubjectData: action.allSubjectData,
            error: null,
            loading: false
        });
    }

    return updateObject(state, {
        currentSubjectData: action.currentSubjectData,
        error: null,
        loading: false
    })
};

/* ----- Add, Update and Delete subjects ----- */

const addRemoveSuccess = (state: SubjectState, action: SubjectAction): SubjectState => {
    return updateObject(state, {
        error: null,
        loading: false
    })
};


const reducer = (state: SubjectState = initialState, action: SubjectAction): SubjectState => {
    switch (action.type) {

        //Start cases
        case SubjectActionTypes.FETCH_SUBJECTS_START:
        case SubjectActionTypes.ADD_SUBJECT_START:
        case SubjectActionTypes.DELETE_SUBJECT_START:
            return initAction(state, action);

        //Fail cases
        case SubjectActionTypes.FETCH_SUBJECTS_FAIL:
        case SubjectActionTypes.ADD_SUBJECT_FAIL:
        case SubjectActionTypes.DELETE_SUBJECT_FAIL:
            return failedAction(state, action);

        //Success cases
        case SubjectActionTypes.FETCH_SUBJECTS_SUCCESS:
            return fetchSubjectsSuccess(state, action);

        case SubjectActionTypes.ADD_SUBJECT_SUCCESS:
        case SubjectActionTypes.DELETE_SUBJECT_SUCCESS:
            return addRemoveSuccess(state, action);

        default:
            return state;
    }
};

export default reducer;
