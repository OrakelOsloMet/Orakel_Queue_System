import {FC, useEffect} from 'react';
import {Route, Routes} from 'react-router-dom';
import {connect} from 'react-redux';
import {checkValidAuth, fetchSubjects} from "./store/actions/actionIndex";
import {ADMIN_ROUTE} from "./constants/constants";
import {bindActionCreators, Dispatch} from "redux";
import {RootState} from "./store";
import PrivateRoute from "./privateRoute";
import LandingPageConnected from "./containers/LandingPage/landingPageConnected";
import AdminPageConnected from "./containers/AdminPage/adminPageConnected";

const mapDispatchToProps = (dispatch: Dispatch) => {
    return bindActionCreators({
        autoLogin: checkValidAuth,
        getSubjectData: fetchSubjects
    }, dispatch);
};

const mapStateToProps = (state: RootState) => {
    return {
        isAuthenticated: state.auth.user?.token != null
    }
};

type Props = ReturnType<typeof mapDispatchToProps> & ReturnType<typeof mapStateToProps>;

const App: FC<Props> = (props: Props) => {
    const {autoLogin} = props;

    useEffect(() => {
        autoLogin();
        //getSubjectData(); //TODO Add this back on the admin page
    }, [autoLogin]);

    return (
        <div style={{textAlign: "center"}}>
            <Routes>
                <Route path="/" element={<LandingPageConnected/>}/>
                <Route path={ADMIN_ROUTE} element={<PrivateRoute>{<AdminPageConnected/>}</PrivateRoute>}/>
                <Route path="*" element={<LandingPageConnected/>}/>
            </Routes>
        </div>
    );
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
