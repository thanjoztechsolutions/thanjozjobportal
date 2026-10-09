import { useContext } from "react";

import { ApplicationContext } from "../../context/applicationcontext.jsx";
import { JobContext } from "../../context/jobcontext.jsx";

function AdminApplicants() {

    const { applications } = useContext(ApplicationContext);

    const { jobs } = useContext(JobContext);

    return (

        <div className="admin-applicants-page">

            <div className="admin-applicants-container">

                <h1>Manage Applicants</h1>

                {

                    applications.length === 0 ? (

                        <h2>No Applications Available</h2>

                    ) : (

                        <table className="admin-applicants-table">

                            <thead>

                                <tr>

                                    <th>Name</th>

                                    <th>Email</th>

                                    <th>Phone</th>

                                    <th>Qualification</th>

                                    <th>Experience</th>

                                    <th>Job</th>

                                    <th>Status</th>

                                    <th>Cover Letter</th>

                                </tr>

                            </thead>

                            <tbody>

                                {

                                    applications.map((application) => {

                                        const job = jobs.find(

                                            (job) => job.id === application.jobId

                                        );

                                        return (

                                            <tr key={application.id}>

                                                <td>{application.name}</td>

                                                <td>{application.email}</td>

                                                <td>{application.phone}</td>

                                                <td>
                                                    {application.qualification}
                                                </td>

                                                <td>
                                                    {application.experience}
                                                </td>

                                                <td>

                                                    {

                                                        job

                                                            ? job.title

                                                            : "Job Deleted"

                                                    }

                                                </td>

                                                <td>

                                                    <span

                                                        className={`status ${application.status.toLowerCase()}`}

                                                    >

                                                        {application.status}

                                                    </span>

                                                </td>

                                                <td>

                                                    <button
                                                        className="view-btn"
                                                        onClick={() =>
                                                            alert(
                                                                application.coverLetter ||
                                                                "No Cover Letter"
                                                            )
                                                        }
                                                    >

                                                        View

                                                    </button>

                                                </td>

                                            </tr>

                                        );

                                    })

                                }

                            </tbody>

                        </table>

                    )

                }

            </div>

        </div>

    );

}

export default AdminApplicants;