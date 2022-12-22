import {SubjectActionTypes} from "./actionTypes";
import {SubjectDispatch} from "../types";
import {REST_INSTANCE as axios} from "../../axiosApi";
import {CURRENT_SUBJECTS_PATH, DELETE_SUBJECT_PATH, EDIT_SUBJECT_PATH, SUBJECTS_PATH} from "../../constants/constants";
import {ISubject} from "../../models/types";
import authHeader from "../../httpHeaders/authHeader";

/* ----- Fetch Subjects ----- */

const fetchSubjectsStart = () => {
    return {
        type: SubjectActionTypes.FETCH_SUBJECTS_START
    }
};

const fetchAllSubjectsSuccess = (subjectData: Array<ISubject>) => {
    return {
        type: SubjectActionTypes.FETCH_SUBJECTS_SUCCESS,
        allSubjectData: subjectData
    }
};

const fetchCurrentSubjectsSuccess = (subjectData: Array<ISubject>) => {
    return {
        type: SubjectActionTypes.FETCH_SUBJECTS_SUCCESS,
        currentSubjectData: subjectData
    }
}

const fetchSubjectsFail = (error: string) => {
    return {
        type: SubjectActionTypes.FETCH_SUBJECTS_FAIL,
        error: error
    }
};

export const fetchSubjects = (allSubjects: boolean = false) => {
    return (dispatch: SubjectDispatch) => {
        dispatch(fetchSubjectsStart());
        const path = allSubjects ? SUBJECTS_PATH : CURRENT_SUBJECTS_PATH;

        axios.get(path)
            .then(response => {
                allSubjects ? dispatch(fetchAllSubjectsSuccess(response.data)) : dispatch(fetchCurrentSubjectsSuccess(response.data));
            }).catch(error => {
            dispatch(fetchSubjectsFail(error.response));
        });
    }
};

/* ----- Add Subject ----- */

const addEditSubjectStart = () => {
    return {
        type: SubjectActionTypes.ADD_SUBJECT_START
    }
};

const addEditSubjectSuccess = () => {
    return {
        type: SubjectActionTypes.ADD_SUBJECT_SUCCESS,
    }
};

const addEditSubjectFail = (error: string) => {
    return {
        type: SubjectActionTypes.ADD_SUBJECT_FAIL,
        error: error
    }
};

export const addEditSubject = (subject: ISubject, edit: boolean = false) => {
    return (dispatch: SubjectDispatch) => {
        dispatch(addEditSubjectStart());


        let apiCall;
        if (edit) {
            apiCall = axios.put(EDIT_SUBJECT_PATH + subject.id, subject, {headers: authHeader()});
        } else {
            apiCall = axios.post(SUBJECTS_PATH, subject, {headers: authHeader()});
        }

        apiCall.then(() => {
            dispatch(addEditSubjectSuccess());
            dispatch(fetchSubjects(true));
        })
            .catch(error => {
                dispatch(addEditSubjectFail(error.response));
            })
    }
}

/* ----- Delete Subject ----- */

const deleteSubjectStart = () => {
    return {
        type: SubjectActionTypes.DELETE_SUBJECT_START
    }
};

const deleteSubjectSuccess = () => {
    return {
        type: SubjectActionTypes.DELETE_SUBJECT_SUCCESS
    }
};

const deleteSubjectFail = (error: string) => {
    return {
        type: SubjectActionTypes.DELETE_SUBJECT_FAIL,
        error: error
    }
};

export const deleteSubject = (id: number) => {
    return (dispatch: SubjectDispatch) => {
        dispatch(deleteSubjectStart());

        axios.delete(DELETE_SUBJECT_PATH + id, {headers: authHeader()})
            .then(() => {
                dispatch(deleteSubjectSuccess());
                dispatch(fetchSubjects(true));
            })
            .catch(error => {
                dispatch(deleteSubjectFail(error.response));
            });
    }
};