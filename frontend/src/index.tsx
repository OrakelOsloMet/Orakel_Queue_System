import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter} from "react-router-dom";
import {createStore, applyMiddleware} from "redux";
import {Provider} from "react-redux";
import thunk from "redux-thunk";
import {rootReducer, composeEnhancers} from "./store";

import App from './app';
import './index.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Layout from "./higherOrderedComponents/Layout/layout";

const store = createStore(rootReducer, composeEnhancers(applyMiddleware(thunk)));

const app = (
    <Provider store={store}>
        <BrowserRouter>
            <Layout>
                <App/>
            </Layout>
        </BrowserRouter>
    </Provider>
);

ReactDOM.render(app, document.getElementById("root"));
serviceWorker.unregister();