import { useContext } from "react";
import { useParams, Link } from "react-router-dom";

import { JobContext } from "../../context/jobcontext.jsx";

function JobDetails() {

  const { id } = useParams();

  const { jobs } = useContext(JobContext);

  const job = jobs.find(

    (job) => job.id.toString() === id

  );

  if (!job) {

    return (

      <div>

        <h2>
          Job Not Found
        </h2>

        <Link to="/jobs">

          Go Back

        </Link>

      </div>

    );

  }

  return (

    <div className="job-details-page">

      <div className="job-details-card">

        <h1>
          {job.title}
        </h1>

        <h2>
          🏢 {job.company}
        </h2>

        <div className="job-info">

          <p>
            📍 {job.location}
          </p>

          <p>
            💼 {job.type}
          </p>

          <p>
            ⭐ {job.experience}
          </p>

          <p>
            💰 {job.salary}
          </p>

        </div>

        <h3>
          Job Description
        </h3>

        <p>
          {job.description || "No description provided."}
        </p>

        <h3>
          Requirements
        </h3>

        <p>
          {job.skills || "No specific requirements provided."}
        </p>

        <p>
          <strong>Vacancies:</strong> {job.vacancies || "N/A"}
        </p>

        <Link to={`/apply/${job.id}`}>

          <button className="apply-btn">

            Apply Now

          </button>

        </Link>

      </div>

    </div>

  );

}

export default JobDetails;