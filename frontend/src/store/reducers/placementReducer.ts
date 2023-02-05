import {updateObject} from "../../utilities/objectUtilities";
import {PlacementAction, PlacementState} from "../types";
import {PlacementActionTypes} from "../actions/actionTypes";

const initialState: PlacementState = {
    placementData: [],
    error: null,
    loading: false
}

const initAction = (state: PlacementState, action: PlacementAction): PlacementState => {
    return updateObject(state, {error: null, loading: true});
};

const failedAction = (state: PlacementState, action: PlacementAction): PlacementState => {
    return updateObject(state, {
        error: action.error,
        loading: false
    });
};

/* ----- Fetch Placement Data ----- */

const fetchPlacementsSuccess = (state: PlacementState, action: PlacementAction): PlacementState => {
    return updateObject(state, {
        placementData: action.placementData,
        error: null,
        loading: false
    });
};

/* ----- Add, Update and Delete Placements ----- */

const addRemoveSuccess = (state: PlacementState, action: PlacementAction): PlacementState => {
    return updateObject(state, {
        error: null,
        loading: false
    });
};

const reducer = (state: PlacementState = initialState, action: PlacementAction): PlacementState => {
    switch (action.type) {

        //Start cases
        case PlacementActionTypes.FETCH_PLACEMENTS_START:
        case PlacementActionTypes.ADD_PLACEMENT_START:
        case PlacementActionTypes.DELETE_PLACEMENT_START:
            return initAction(state, action);

        //Fail cases
        case PlacementActionTypes.FETCH_PLACEMENTS_FAIL:
        case PlacementActionTypes.ADD_PLACEMENT_FAIL:
        case PlacementActionTypes.DELETE_PLACEMENT_FAIL:
            return failedAction(state, action);

        //Success cases
        case PlacementActionTypes.FETCH_PLACEMENTS_SUCCESS:
            return fetchPlacementsSuccess(state, action);

        case PlacementActionTypes.ADD_PLACEMENT_SUCCESS:
        case PlacementActionTypes.DELETE_PLACEMENT_SUCCESS:
            return addRemoveSuccess(state, action);

        default:
            return state;
    }
}

export default reducer;