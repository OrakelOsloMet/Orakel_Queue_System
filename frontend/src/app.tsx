import React, {useEffect} from 'react';
import {Route, Switch, withRouter, Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import {checkValidAuth, fetchSubjects} from "./store/actions/actionIndex";
import LandingPage from "./containers/LandingPage/landingPage";
import {ADMIN_ROUTE, INDEX_ROUTE} from "./constants/constants";
import {bindActionCreators, Dispatch} from "redux";
import AdminPageConnected from "./containers/AdminPage/adminPageConnected";
import {RootState} from "./store";

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

const App: React.FC<Props> = (props: Props) => {
    const {autoLogin, getSubjectData} = props;

    //TODO Subject data does not need to be globally fetched here. Should be done locally in components that needs them.
    useEffect(() => {
        autoLogin();
        getSubjectData();
    }, [autoLogin, getSubjectData]);

    let routes = (
        <Switch>
            <Route path={INDEX_ROUTE} exact render={LandingPage}/>
            <Redirect to={INDEX_ROUTE}/>
        </Switch>
    );

    if (props.isAuthenticated) {
        routes = (
            <Switch>
                <Route path={ADMIN_ROUTE} render={() => <AdminPageConnected/>}/>
                <Route path={INDEX_ROUTE} exact render={LandingPage}/>
                <Redirect to={INDEX_ROUTE}/>
            </Switch>
        );
    }

    return (
        <div style={{textAlign: "center"}}>
            {routes}
        </div>
    );
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));


