import {ReactNode} from "react";
import {Navigate} from "react-router-dom";
import useAuth from "./hooks/useAuth";

type Props = {
    children: ReactNode
}

const PrivateRoute = (props: Props) => {
    const user = useAuth();
    return user ?
        <>
            {props.children}
        </>
        : <Navigate to="/" />
}

export default PrivateRoute;