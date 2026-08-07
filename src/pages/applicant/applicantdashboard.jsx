import { Link } from "react-router-dom";

function ApplicantDashboard() {

    return (

        <div className="dashboard-page">

            <div className="dashboard-container">

                <h1>Applicant Dashboard</h1>

                <div className="dashboard-cards">

                    <Link to="/applicant/myapplications">

                        <div className="dashboard-card">

                            <h2>📄</h2>

                            <h3>My Applications</h3>

                            <p>
                                View all jobs you have applied for.
                            </p>

                        </div>

                    </Link>

                    <Link to="/">

                        <div className="dashboard-card">

                            <h2>🏠</h2>

                            <h3>Home</h3>

                            <p>
                                Return to the home page.
                            </p>

                        </div>

                    </Link>

                </div>

            </div>

        </div>

    );

}

export default ApplicantDashboard;