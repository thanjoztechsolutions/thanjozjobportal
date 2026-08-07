import { useContext } from "react";
import { Link } from "react-router-dom";

import { JobContext } from "../../context/jobcontext.jsx";

function MyJobs() {

  const { jobs, deleteJob } = useContext(JobContext);

  return (

    <div className="myjobs-page">

      <div className="myjobs-container">

        <h1>My Posted Jobs</h1>

        {

          jobs.length === 0 ? (

            <h3>No Jobs Posted Yet</h3>

          ) : (

            <table className="myjobs-table">

              <thead>

                <tr>

                  <th>Job Title</th>

                  <th>Company</th>

                  <th>Location</th>

                  <th>Salary</th>

                  <th>Applicants</th>

                  <th>Actions</th>

                </tr>

              </thead>

              <tbody>

                {

                  jobs.map((job) => (

                    <tr key={job.id}>

                      <td>{job.title}</td>

                      <td>{job.company}</td>

                      <td>{job.location}</td>

                      <td>{job.salary}</td>

                      <td>{job.applicants}</td>

                      <td>

                        <div className="action-buttons">

                          <Link to={`/contractor/applicants/${job.id}`}>

                            <button className="view-btn">

                              View Applicants

                            </button>

                          </Link>

                          <Link to={`/contractor/editjob/${job.id}`}>

                            <button className="edit-btn">

                              Edit Job

                            </button>

                          </Link>

                          <button

                            className="delete-btn"

                            onClick={() => {

                              const confirmDelete = window.confirm(
                                "Are you sure you want to delete this job?"
                              );

                              if (confirmDelete) {

                                deleteJob(job.id);

                              }

                            }}

                          >

                            Delete Job

                          </button>

                        </div>

                      </td>

                    </tr>

                  ))

                }

              </tbody>

            </table>

          )

        }

        <br />

        <Link to="/contractor/postjob">

          <button className="post-btn">

            + Post Another Job

          </button>

        </Link>

      </div>

    </div>

  );

}

export default MyJobs;