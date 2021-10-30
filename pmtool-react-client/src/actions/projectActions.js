import axios from "axios";
import { DELETE_PROJECT, GET_ERRORS, GET_PROJECT, GET_PROJECTS } from "./types";

export const createProject = (project, history) => async dispatch => {
    try {
        const res = await axios.post("/api/project", project, {
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            }
        })
        history.push("/dashboard");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })

    } catch (err) {
        console.log(err.response);
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })

    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all", {
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        }
    })

    console.log("res data", res.data);
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    })
};


export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/project/${id}`, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })

        console.log("res data", res.data);
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        })
    } catch (err) {
        history.push("/dashboard");
    }

}

export const deleteProject = id => async dispatch => {
    try {
        if (window.confirm("Are you sure?, this project will be dropped.")) {
            const res = await axios.delete(`/api/project/${id}`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })

            console.log("res data", res.data);
            dispatch({
                type: DELETE_PROJECT,
                payload: id
            })
        }
    } catch (err) {

    }

}
