/* ----- Fetch Placements ----- */

import {PlacementActionTypes} from "./actionTypes";
import {IPlacement} from "../../models/types";
import {PlacementDispatch} from "../types";
import {REST_INSTANCE as axios} from "../../axiosAPI";
import authHeader from "../../httpHeaders/authHeader";
import {DELETE_PLACEMENT_PATH, EDIT_PLACEMENT_PATH, PLACEMENTS_PATH} from "../../constants/constants";

//TODO Placements does not need to be handled in Redux, only in forms where it is used.
const fetchPlacementsStart = () => {
    return {
        type: PlacementActionTypes.FETCH_PLACEMENTS_START
    }
};

const fetchPlacementsSuccess = (placementData: Array<IPlacement>) => {
    return {
        type: PlacementActionTypes.FETCH_PLACEMENTS_SUCCESS,
        placementData: placementData
    }
};

const fetchPlacementsFail = (error: string) => {
    return {
        type: PlacementActionTypes.FETCH_PLACEMENTS_FAIL,
        error: error
    }
};

export const fetchPlacements = () => {
    return (dispatch: PlacementDispatch) => {
        dispatch(fetchPlacementsStart());

        axios.get(PLACEMENTS_PATH)
            .then(response => {
                dispatch(fetchPlacementsSuccess(response.data));
            })
            .catch(error => {
                dispatch(fetchPlacementsFail(error.response));
            });
    }
};

/* ----- Add Placement ------ */

const addEditPlacementStart = () => {
    return {
        type: PlacementActionTypes.ADD_PLACEMENT_START
    }
};

const addEditPlacementSuccess = () => {
    return {
        type: PlacementActionTypes.ADD_PLACEMENT_SUCCESS,
    }
};

const addEditPlacementFail = (error: string) => {
    return {
        type: PlacementActionTypes.ADD_PLACEMENT_FAIL,
        error: error
    }
};

//TODO Add real URL
export const addEditPlacement = (placement: IPlacement, edit: boolean = false) => {
    return (dispatch: PlacementDispatch) => {
        dispatch(addEditPlacementStart());


        let apiCall;
        if (edit) {
            apiCall = axios.put(EDIT_PLACEMENT_PATH + placement.id, placement, {headers: authHeader()});
        } else {
            apiCall = axios.post(PLACEMENTS_PATH, placement, {headers: authHeader()});
        }

        apiCall.then(() => {
            dispatch(addEditPlacementSuccess());
            dispatch(fetchPlacements());
        })
            .catch(error => {
                dispatch(addEditPlacementFail(error.response));
            })
    }
}

/* ----- Delete Placement ------ */

const deletePlacementStart = () => {
    return {
        type: PlacementActionTypes.DELETE_PLACEMENT_START
    }
};

const deletePlacementSuccess = () => {
    return {
        type: PlacementActionTypes.DELETE_PLACEMENT_SUCCESS
    }
};

const deletePlacementFail = (error: string) => {
    return {
        type: PlacementActionTypes.DELETE_PLACEMENT_FAIL,
        error: error
    }
};

export const deletePlacement = (id: number) => {
    return (dispatch: PlacementDispatch) => {
        dispatch(deletePlacementStart());

        axios.delete(DELETE_PLACEMENT_PATH + id, {headers: authHeader()})
            .then(() => {
                dispatch(deletePlacementSuccess());
                dispatch(fetchPlacements());
            })
            .catch(error => {
                dispatch(deletePlacementFail(error.response));
            });
    }
}
