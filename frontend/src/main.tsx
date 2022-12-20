import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './app'
import './index.css'
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import {applyMiddleware, createStore} from "redux";
import {composeEnhancers, rootReducer} from "./store";
import thunk from "redux-thunk";
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";
import Layout from "./higherOrderedComponents/Layout/layout";

//TODO Replace with Redux Toolkit
const store = createStore(rootReducer, composeEnhancers(applyMiddleware(thunk)));

const app = (
    <React.StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <Layout>
                    <App/>
                </Layout>
            </BrowserRouter>
        </Provider>
    </React.StrictMode>
);

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(app);


