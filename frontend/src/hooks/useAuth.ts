import {LOCAL_STORAGE_USER} from "../constants/constants";
import {IUser} from "../store/types";

//TODO Add some actual JWT validation here once the login system has been replaced
const useAuth = (): IUser | null => {
    const localStorageUser = localStorage.getItem(LOCAL_STORAGE_USER);
        if (localStorageUser) {
            return JSON.parse(localStorageUser!);
        }

        return null
}

export default useAuth;