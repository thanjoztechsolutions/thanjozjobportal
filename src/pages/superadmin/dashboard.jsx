import { Link } from "react-router-dom";
import { useContext } from "react";

import { JobContext } from "../../context/jobcontext";
import { ApplicationContext } from "../../context/applicationcontext";

function AdminDashboard() {

    const { jobs } = useContext(JobContext);

    const { applications } = useContext(ApplicationContext);

    const contractors = 5;

    const reports = 0;

    return (

        <div className="admin-dashboard">

            <div className="admin-container">

                <h1>

                    Super Admin Dashboard

                </h1>

                <div className="admin-cards">

                    <div className="admin-card">

                        <h2>Total Jobs</h2>

                        <h1>{jobs.length}</h1>

                    </div>

                    <div className="admin-card">

                        <h2>Total Contractors</h2>

                        <h1>{contractors}</h1>

                    </div>

                    <div className="admin-card">

                        <h2>Total Applicants</h2>

                        <h1>{applications.length}</h1>

                    </div>

                    <div className="admin-card">

                        <h2>Reports</h2>

                        <h1>{reports}</h1>

                    </div>

                </div>

                <div className="admin-actions">

                    <Link to="/superadmin/jobs">

                        <button>

                            Manage Jobs

                        </button>

                    </Link>

                    <Link to="/superadmin/contractors">

                        <button>

                            Manage Contractors

                        </button>

                    </Link>

                    <Link to="/superadmin/applicants">

                        <button>

                            Manage Applicants

                        </button>

                    </Link>

                    <Link to="/superadmin/reports">

                        <button>

                            Reports

                        </button>

                    </Link>

                </div>

            </div>

        </div>

    );

}

export default AdminDashboard;