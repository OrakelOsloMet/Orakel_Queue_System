import {REST_INSTANCE as axios} from "../../axiosApi";
import {AuthActionTypes} from "./actionTypes";
import {LOGIN_PATH, CHECK_TOKEN_PATH, LOCAL_STORAGE_USER} from "../../constants/constants";
import {AuthDispatch, IUser} from "../types";

const authStart = () => {
    return {
        type: AuthActionTypes.AUTH_START
    }
};

const authSuccess = (user: IUser) => {
    return {
        type: AuthActionTypes.AUTH_SUCCESS,
        user: user
    }
};

const authFail = (error: string) => {
    return {
        type: AuthActionTypes.AUTH_FAIL,
        error: error
    }
};

const getCurrentUser = (): IUser | null => {
    if (localStorage.getItem(LOCAL_STORAGE_USER)) {
        return JSON.parse(localStorage.getItem(LOCAL_STORAGE_USER)!);
    }
    return null;
};

export const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_USER);

    return {
        type: AuthActionTypes.AUTH_LOGOUT
    }
};

export const checkValidAuth = () => {
    return (dispatch: AuthDispatch) => {
        const user = getCurrentUser();

        if (user && user.token) {
            axios.post(CHECK_TOKEN_PATH, user.token).then(response => {
                if (response.data === true) {
                    dispatch(authSuccess(user))
                } else {
                    dispatch(logout())
                }
            });
        } else {
            dispatch(logout())
        }
    }
};

export const auth = (username: string, password: string) => {
    return (dispatch: AuthDispatch) => {
        dispatch(authStart());

        return axios.post(LOGIN_PATH, {username, password})
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem(LOCAL_STORAGE_USER, JSON.stringify(response.data));
                    dispatch(authSuccess(response.data));
                    return true
                }
            })
            .catch(error => {
                dispatch(authFail(error.response.data.message))
                return false;
            });
    }
};

export const clearError = () => {
    return {
        type: AuthActionTypes.CLEAR_ERROR
    }
}