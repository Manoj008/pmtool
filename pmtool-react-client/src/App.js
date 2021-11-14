import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";

import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import AddProject from './components/project/AddProject';

import { provider } from 'react-redux';
import { Provider } from 'react-redux';

import store from './Store';
import UpdateProject from './components/project/UpdateProject'
import ProjectBoard from './components/projectBoard/ProjectBoard';
import AddProjectTask from './components/projectBoard/projectTasks/AddProjectTask';
import UpdateProjectTasks from './components/projectBoard/projectTasks/UpdateProjectTasks';
import Landing from './components/layout/Landing';
import Register from './components/userManagement/Register';
import Login from './components/userManagement/Login';
import jwt_decode from 'jwt-decode';
import setJwtToken from './securityUtils/SetJwtToken';
import { SET_CURRENT_USER } from './actions/types';
import { logout } from './actions/SecurityActions';
import SecuredRoute from './securityUtils/SecuredRoute';
import { useEffect, useState } from 'react';

const jwtToken = localStorage.jwtToken;

// if (jwtToken) {
//   setJwtToken(jwtToken);
//   const decoded_jwtToken = jwt_decode(jwtToken);
//   store.dispatch({
//     type: SET_CURRENT_USER,
//     payload: decoded_jwtToken
//   });

//   const currentTime = Date.now().valueOf() / 1000;
//   if (typeof decoded_jwtToken.exp !== 'undefined' && decoded_jwtToken.exp < currentTime) {
//     store.dispatch(logout());
//     useHistory().push("/login");
//   }
//   if (typeof decoded_jwtToken.nbf !== 'undefined' && decoded_jwtToken.nbf > currentTime) {
//     store.dispatch(logout());
//     useHistory().push("/login");
//   }
// }

function App() {

  const [token, setToken] = useState(localStorage.jwtToken);

  useEffect(() => {
    if (token) {
      setJwtToken(token);
      const decoded_jwtToken = jwt_decode(token);
      store.dispatch({
        type: SET_CURRENT_USER,
        payload: decoded_jwtToken
      });

      const currentTime = Date.now().valueOf() / 1000;
      if (typeof decoded_jwtToken.exp !== 'undefined' && decoded_jwtToken.exp < currentTime) {
        store.dispatch(logout());
        window.location.href = "/";
      }
      if (typeof decoded_jwtToken.nbf !== 'undefined' && decoded_jwtToken.nbf > currentTime) {
        store.dispatch(logout());
        window.location.href = "/";
      }
    }
    return () => {
      setToken(null);
    }
  }, [])

  return (
    < Provider store={store} >
      <Router>
        <div className="App">
          <Header />

          {
            // Public routes
          }

          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />

          {
            //private routes
          }
          <Switch>
            <SecuredRoute exact path="/dashboard" component={Dashboard} />
            <SecuredRoute exact path="/addProject" component={AddProject} />
            <SecuredRoute exact path="/updateProject/:id" component={UpdateProject} />
            <SecuredRoute exact path="/projectBoard/:id" component={ProjectBoard} />
            <SecuredRoute exact path="/addProjectTask/:id" component={AddProjectTask} />
            <SecuredRoute exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTasks} />
          </Switch>
        </div>
      </Router>
    </Provider >
  );
}

export default App;
