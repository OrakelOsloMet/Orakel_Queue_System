import {LOCAL_STORAGE_USER} from "../constants/constants";

//TODO Create of find a type for Authorization headers.
const authHeader = () => {

    const USER = JSON.parse(localStorage.getItem(LOCAL_STORAGE_USER)!);

    if (USER && USER.token) {
        return { Authorization: "Bearer " + USER.token };
    } else {
        return {};
    }
}

export default authHeader;
