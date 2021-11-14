
import { GET_ERRORS, SET_CURRENT_USER } from "../actions/types";

const initialState = {};

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_ERRORS:
            const { username, password } = action.payload;
            if (username === "Invalid username" && password === 'Invalid Password') {
                window.alert("Session has been closed, Redirecting to login Page.");
                // setTimeout(() => {
                //     window.location.href = "/";
                // }, 3000);
                window.location.href = "/login";
            }
            return action.payload;

        default:
            return state;
    }
}