import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import jwt_decode from 'jwt-decode';
import setJwtToken from "../securityUtils/SetJwtToken";

export const createNewUser = (newUser, history) => async dispatch => {

    try {
        await axios.post("api/users/register", newUser);

        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
}

export const login = LoginRequest => async dispatch => {
    // post => login requests
    try {
        const res = await axios.post("api/users/login", LoginRequest);
        //extract data from response
        const { token } = res.data;
        //store token in local storage
        localStorage.setItem("jwtToken", token);
        //**set our token in header
        setJwtToken(token);
        //decode token on react
        const decoded = jwt_decode(token);
        // dispatch to our securityReducer
        dispatch({
            type: SET_CURRENT_USER,
            payload: decoded
        })
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })

    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
}

export const logout = () => dispatch => {
    localStorage.removeItem("jwtToken");
    setJwtToken(false);
    dispatch({
        type: SET_CURRENT_USER,
        payload: {}
    });

};