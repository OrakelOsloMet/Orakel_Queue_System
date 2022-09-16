import {AuthActionTypes} from "../actions/actionTypes";
import {updateObject} from "../../utilities/objectUtilities";
import {AuthAction, AuthState} from "../types";

const initialState: AuthState = {
    user: null,
    error: null,
    loading: false
};

const authStart = (state: AuthState, action: AuthAction): AuthState => {
    return updateObject(state, {error: null, loading: true});
};

const authSuccess = (state: AuthState, action: AuthAction): AuthState => {
    return updateObject(state, {
        user: action.user,
        error: null,
        loading: false
    });
};

const authFail = (state: AuthState, action: AuthAction): AuthState => {
    return updateObject(state, {
        error: action.error,
        loading: false
    });
};

const authLogout = (state: AuthState, action: AuthAction): AuthState => {
    return updateObject(state, {user: null});
};

const clearError = (state: AuthState, action: AuthAction): AuthState => {
    return updateObject(state, {error: null})
}

const reducer = (state: AuthState = initialState, action: AuthAction) => {
    switch(action.type) {
        case AuthActionTypes.AUTH_START: return authStart(state, action);
        case AuthActionTypes.AUTH_SUCCESS: return authSuccess(state, action);
        case AuthActionTypes.AUTH_FAIL: return authFail(state, action);
        case AuthActionTypes.AUTH_LOGOUT: return authLogout(state, action);
        case AuthActionTypes.CLEAR_ERROR: return clearError(state, action);
        default: return state;
    }
};

export default reducer;