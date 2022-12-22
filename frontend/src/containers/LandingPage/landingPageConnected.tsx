import {RootState} from "../../store";
import {connect} from "react-redux";
import LandingPage from "./landingPage";

const mapStateToProps = (state: RootState) => {
    return {
        isAuthenticated: state.auth.user?.token !== undefined
    }
};

export default connect(mapStateToProps)(LandingPage);