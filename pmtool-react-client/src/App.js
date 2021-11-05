import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";

import { BrowserRouter as Router, Route } from 'react-router-dom';
import AddProject from './components/project/AddProject';

import { provider } from 'react-redux';
import { Provider } from 'react-redux';

import store from './Store';
import UpdateProject from './components/project/UpdateProject'
import ProjectBoard from './components/projectBoard/ProjectBoard';
import AddProjectTask from './components/projectBoard/projectTasks/AddProjectTask';
import UpdateProjectTasks from './components/projectBoard/projectTasks/UpdateProjectTasks';


function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/addProject" component={AddProject} />
          <Route exact path="/updateProject/:id" component={UpdateProject} />
          <Route exact path="/projectBoard/:id" component={ProjectBoard} />
          <Route exact path="/addProjectTask/:id" component={AddProjectTask} />
          <Route exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTasks} />

        </div>
      </Router>
    </Provider>
  );
}

export default App;
