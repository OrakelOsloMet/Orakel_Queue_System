import {RootState} from "../../store";
import {connect} from "react-redux";
import AdminPage from "./adminPage";

const mapStateToProps = (state: RootState) => {
    return {
        isAuthenticated: state.auth.user?.token !== undefined
    }
};

export default connect(mapStateToProps)(AdminPage);