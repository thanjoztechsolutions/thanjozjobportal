import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { JobContext } from "../../context/jobcontext.jsx";

function PostJob() {

  const { addJob } = useContext(JobContext);

  const navigate = useNavigate();

  const [job, setJob] = useState({

    title: "",
    company: "",
    location: "",
    salary: "",
    type: "Full Time",
    experience: "",
    category: "",
    skills: "",
    description: "",
    vacancies: ""

  });

  const handleChange = (e) => {

    setJob({

      ...job,

      [e.target.name]: e.target.value

    });

  };

  const handleSubmit = (e) => {

    e.preventDefault();

    addJob(job);

    alert("Job Posted Successfully");

    navigate("/");

  };

  return (

    <div className="postjob-page">

      <div className="postjob-container">

        <h1>Post New Job</h1>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="title"
            placeholder="Job Title"
            value={job.title}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="company"
            placeholder="Company Name"
            value={job.company}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="location"
            placeholder="Location"
            value={job.location}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="salary"
            placeholder="Salary"
            value={job.salary}
            onChange={handleChange}
            required
          />

          <select
            name="type"
            value={job.type}
            onChange={handleChange}
          >

            <option>Full Time</option>
            <option>Part Time</option>
            <option>Internship</option>

          </select>

          <input
            type="text"
            name="experience"
            placeholder="Experience"
            value={job.experience}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="category"
            placeholder="Category"
            value={job.category}
            onChange={handleChange}
          />

          <input
            type="text"
            name="skills"
            placeholder="Required Skills"
            value={job.skills}
            onChange={handleChange}
          />

          <textarea
            rows="5"
            name="description"
            placeholder="Job Description"
            value={job.description}
            onChange={handleChange}
          ></textarea>

          <input
            type="number"
            name="vacancies"
            placeholder="Vacancies"
            value={job.vacancies}
            onChange={handleChange}
          />

          <button type="submit">

            Post Job

          </button>

        </form>

      </div>

    </div>

  );

}

export default PostJob;