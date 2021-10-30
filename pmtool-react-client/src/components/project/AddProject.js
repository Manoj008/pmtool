import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createProject } from '../../actions/projectActions';
import classnames from 'classnames';

class AddProject extends Component {

    constructor() {
        super();

        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            startDate: "",
            endDate: "",
            projectStatus: "Planned",
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors })
        }
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();
        const newProject = {

            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate,
            projectStatus: this.state.projectStatus
        }
        this.props.createProject(newProject, this.props.history);

    }


    render() {
        return (
            <div>
                <div className="project" >
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5 className="display-4 text-center">Create Project form</h5>
                                <hr />
                                <form onSubmit={this.onSubmit}>
                                    {this.state.errors.projectName && (
                                        <div className="text-danger">{this.state.errors.projectName}</div>
                                    )}

                                    <div className="form-group">
                                        <input
                                            type="text"
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": this.state.errors.projectName
                                            })}
                                            placeholder="Project Name"
                                            name="projectName"
                                            value={this.state.projectName}
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    {this.state.errors.projectIdentifier && (
                                        <div className="text-danger">{this.state.errors.projectIdentifier}</div>
                                    )}                                    <div className="form-group">
                                        <input
                                            type="text"
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": this.state.errors.projectIdentifier
                                            })}
                                            placeholder="Unique Project ID"
                                            name="projectIdentifier"
                                            value={this.state.projectIdentifier}
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    {this.state.errors.description && (
                                        <div className="text-danger">{this.state.errors.description}</div>
                                    )}
                                    <div className="form-group">
                                        <textarea
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": this.state.errors.description
                                            })}
                                            placeholder="Project Description"
                                            name="description"
                                            value={this.state.description}
                                            onChange={this.onChange}
                                        />
                                    </div>

                                    <h6>Project status</h6>

                                    <div className="form-group">
                                        <select
                                            className="form-control form-control-lg"
                                            placeholder="Project Status"
                                            name="projectStatus"
                                            value={this.state.projectStatus}
                                            onChange={this.onChange}
                                        >
                                            <option value="Planned">Planned</option>
                                            <option value="In Progress">In Progress</option>
                                            <option value="Completed">Completed</option>
                                            <option value="Dropped">Dropped</option>

                                        </select>
                                    </div>
                                    <h6>Start Date</h6>
                                    <div className="form-group">
                                        <input
                                            type="date"
                                            className="form-control form-control-lg"
                                            name="startDate"
                                            value={this.state.startDate}
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    <h6>Estimated End Date</h6>
                                    <div className="form-group">
                                        <input
                                            type="date"
                                            className="form-control form-control-lg"
                                            defaultValue={Date().toLocaleString}
                                            name="endDate"
                                            value={this.state.endDate}
                                            onChange={this.onChange}
                                        />
                                    </div>

                                    <input
                                        type="submit"
                                        className="btn btn-primary btn-block mt-4"
                                    />
                                </form>
                            </div>
                        </div>
                    </div>
                </div >
            </div >
        );
    }
}

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps,
    { createProject }
)(AddProject);