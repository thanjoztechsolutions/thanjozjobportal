import { useContext } from "react";
import { Link } from "react-router-dom";

import { JobContext } from "../../context/jobcontext.jsx";
import { ApplicationContext } from "../../context/applicationcontext.jsx";

function ContractorDashboard() {

  const { jobs } = useContext(JobContext);

  const { applications } = useContext(ApplicationContext);

  const contractor = {
    id: "CTR1001",
    name: "Rahul Kumar",
    company: "ABC Constructions"
  };

  // Dashboard Statistics

  const totalJobs = jobs.length;

  const totalApplicants = applications.length;

  const pendingApplicants = applications.filter(

    (application) => application.status === "Pending"

  ).length;

  const acceptedApplicants = applications.filter(

    (application) => application.status === "Accepted"

  ).length;

  const rejectedApplicants = applications.filter(

    (application) => application.status === "Rejected"

  ).length;

  return (

    <div className="contractor-dashboard">

      {/* Header */}

      <div className="contractor-header">

        <div>

          <h1>
            Contractor Dashboard
          </h1>

          <h3>
            Welcome, {contractor.name}
          </h3>

          <p>
            Contractor ID :
            <strong> {contractor.id}</strong>
          </p>

          <p>
            Company :
            <strong> {contractor.company}</strong>
          </p>

        </div>

        <button>

          Logout

        </button>

      </div>

      {/* Statistics */}

      <div className="dashboard-cards">

        <div className="dashboard-card">

          <h2>
            {totalJobs}
          </h2>

          <p>
            Jobs Posted
          </p>

        </div>

        <div className="dashboard-card">

          <h2>
            {totalApplicants}
          </h2>

          <p>
            Total Applicants
          </p>

        </div>

        <div className="dashboard-card">

          <h2>
            {pendingApplicants}
          </h2>

          <p>
            Pending
          </p>

        </div>

        <div className="dashboard-card">

          <h2>
            {acceptedApplicants}
          </h2>

          <p>
            Accepted
          </p>

        </div>

        <div className="dashboard-card">

          <h2>
            {rejectedApplicants}
          </h2>

          <p>
            Rejected
          </p>

        </div>

      </div>

      {/* Navigation */}

      <div className="dashboard-cards">

        <Link
          to="/contractor/postjob"
          className="dashboard-link"
        >

          <div className="dashboard-card">

            <h2>
              ➕
            </h2>

            <p>
              Post New Job
            </p>

          </div>

        </Link>

        <Link
          to="/contractor/myjobs"
          className="dashboard-link"
        >

          <div className="dashboard-card">

            <h2>
              📄
            </h2>

            <p>
              My Jobs
            </p>

          </div>

        </Link>

        <Link
          to="/contractor/allapplicants"
          className="dashboard-link"
        >

          <div className="dashboard-card">

            <h2>
              👥
            </h2>

            <p>
              Applicants
            </p>

          </div>

        </Link>

        <Link
          to="/contractor/profile"
          className="dashboard-link"
        >

          <div className="dashboard-card">

            <h2>
              👤
            </h2>

            <p>
              My Profile
            </p>

          </div>

        </Link>

      </div>

    </div>

  );

}

export default ContractorDashboard;