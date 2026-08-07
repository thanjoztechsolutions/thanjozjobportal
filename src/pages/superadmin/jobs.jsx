import { useContext } from "react";

import { JobContext } from "../../context/jobcontext";

function SuperAdminJobs() {

    const { jobs, deleteJob } = useContext(JobContext);

    return (

        <div className="admin-jobs-page">

            <div className="admin-jobs-container">

                <h1>Manage Jobs</h1>

                {

                    jobs.length === 0 ? (

                        <h2>No Jobs Available</h2>

                    ) : (

                        <table className="admin-jobs-table">

                            <thead>

                                <tr>

                                    <th>Job Title</th>
                                    <th>Company</th>
                                    <th>Location</th>
                                    <th>Salary</th>
                                    <th>Type</th>
                                    <th>Applicants</th>
                                    <th>Action</th>

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

                                            <td>{job.type}</td>

                                            <td>{job.applicants}</td>

                                            <td>

                                                <button

                                                    className="delete-btn"

                                                    onClick={() => {

                                                        if (

                                                            window.confirm(

                                                                "Delete this job?"

                                                            )

                                                        ) {

                                                            deleteJob(job.id);

                                                        }

                                                    }}

                                                >

                                                    Delete

                                                </button>

                                            </td>

                                        </tr>

                                    ))

                                }

                            </tbody>

                        </table>

                    )

                }

            </div>

        </div>

    );

}

export default SuperAdminJobs;