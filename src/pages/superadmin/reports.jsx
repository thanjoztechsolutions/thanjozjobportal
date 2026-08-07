import { useContext } from "react";

import { JobContext } from "../../context/jobcontext";
import { ApplicationContext } from "../../context/applicationcontext";

function Reports() {

    const { jobs } = useContext(JobContext);

    const { applications } = useContext(ApplicationContext);

    const totalJobs = jobs.length;

    const totalApplications = applications.length;

    const pendingApplications = applications.filter(

        (app) => app.status === "Pending"

    ).length;

    const acceptedApplications = applications.filter(

        (app) => app.status === "Accepted"

    ).length;

    const rejectedApplications = applications.filter(

        (app) => app.status === "Rejected"

    ).length;

    return (

        <div className="reports-page">

            <div className="reports-container">

                <h1>System Reports</h1>

                <div className="reports-grid">

                    <div className="report-card">

                        <h2>{totalJobs}</h2>

                        <p>Total Jobs</p>

                    </div>

                    <div className="report-card">

                        <h2>{totalApplications}</h2>

                        <p>Total Applications</p>

                    </div>

                    <div className="report-card">

                        <h2>{pendingApplications}</h2>

                        <p>Pending</p>

                    </div>

                    <div className="report-card">

                        <h2>{acceptedApplications}</h2>

                        <p>Accepted</p>

                    </div>

                    <div className="report-card">

                        <h2>{rejectedApplications}</h2>

                        <p>Rejected</p>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Reports;