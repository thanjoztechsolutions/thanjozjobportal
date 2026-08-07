import { useContext } from "react";

import { ApplicationContext } from "../../context/applicationcontext.jsx";
import { JobContext } from "../../context/jobcontext.jsx";

function AllApplicants() {

    const { applications } = useContext(ApplicationContext);

    const { jobs } = useContext(JobContext);

    return (

        <div className="applicants-page">

            <div className="applicants-container">

                <h1>All Applicants</h1>

                {

                    applications.length === 0 ? (

                        <h3>No Applications Yet</h3>

                    ) : (

                        applications.map((app) => {

                            const job = jobs.find(

                                (job) => job.id === app.jobId

                            );

                            return (

                                <div
                                    className="applicant-card"
                                    key={app.id}
                                >

                                    <h2>

                                        {job ? job.title : "Job Deleted"}

                                    </h2>

                                    <h3>

                                        👤 {app.name}

                                    </h3>

                                    <p>

                                        📧 {app.email}

                                    </p>

                                    <p>

                                        📱 {app.phone}

                                    </p>

                                    <p>

                                        🎓 {app.qualification}

                                    </p>

                                    <p>

                                        💼 {app.experience}

                                    </p>

                                    <p>

                                        Status :
                                        <strong>

                                            {" "}

                                            {app.status}

                                        </strong>

                                    </p>

                                </div>

                            );

                        })

                    )

                }

            </div>

        </div>

    );

}

export default AllApplicants;