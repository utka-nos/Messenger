import React, {Component} from "react";
import MainPage from "./MainPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "../login-page/LoginPage";
import ProfilePage from "../profile-page/ProfilePage";

class MyComponent extends Component {

    componentDidMount() {
    }

    render() {
        return <div>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/profile" element={<ProfilePage/>}/>
                    <Route path="*" element={<MainPage/>}/>
                </Routes>
            </BrowserRouter>
        </div>;
    }
}
export default MyComponent;