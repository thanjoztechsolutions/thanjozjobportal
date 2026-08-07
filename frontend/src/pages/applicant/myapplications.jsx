import { useContext } from "react";
import { Link } from "react-router-dom";

import { ApplicationContext } from "../../context/applicationcontext.jsx";
import { JobContext } from "../../context/jobcontext.jsx";

function MyApplications() {

    const { applications } = useContext(ApplicationContext);

    const { jobs } = useContext(JobContext);

    return (

        <div className="myapplications-page">

            <div className="myapplications-container">

                <h1>My Applications</h1>

                {

                    applications.length === 0 ? (

                        <h3>You haven't applied for any jobs yet.</h3>

                    ) : (

                        applications.map((application) => {

                            const job = jobs.find(

                                (job) => job.id === application.jobId

                            );

                            return (

                                <div
                                    className="application-card"
                                    key={application.id}
                                >

                                    <h2>

                                        {job ? job.title : "Job Deleted"}

                                    </h2>

                                    <p>

                                        🏢 {job ? job.company : "-"}

                                    </p>

                                    <p>

                                        📍 {job ? job.location : "-"}

                                    </p>

                                    <p>

                                        💰 {job ? job.salary : "-"}

                                    </p>

                                    <p>

                                        📧 {application.email}

                                    </p>

                                    <p>

                                        📱 {application.phone}

                                    </p>

                                <span className={`status ${application.status.toLowerCase()}`}>

    {application.status}

</span>

                                    {

                                        job && (

                                            <Link to={`/job/${job.id}`}>

                                                <button className="view-btn">

                                                    View Job

                                                </button>

                                            </Link>

                                        )

                                    }

                                </div>

                            );

                        })

                    )

                }

            </div>

        </div>

    );

}

export default MyApplications;