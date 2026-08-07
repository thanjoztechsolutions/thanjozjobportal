import { useContext, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { JobContext } from "../../context/jobcontext";

function EditJob() {

    const { id } = useParams();

    const navigate = useNavigate();

    const { jobs, updateJob } = useContext(JobContext);

    const existingJob = jobs.find(

        (job) => job.id === Number(id)

    );

    const [job, setJob] = useState(

        existingJob || {

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

        }

    );

    const handleChange = (e) => {

        setJob({

            ...job,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        updateJob(job);

        alert("Job Updated Successfully");

        navigate("/contractor/myjobs");

    };

    if (!existingJob) {

        return <h2>Job Not Found</h2>;

    }

    return (

        <div className="postjob-page">

            <div className="postjob-container">

                <h1>Edit Job</h1>

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
                    />

                    <input
                        type="number"
                        name="vacancies"
                        placeholder="Vacancies"
                        value={job.vacancies}
                        onChange={handleChange}
                    />

                    <button type="submit">

                        Update Job

                    </button>

                </form>

            </div>

        </div>

    );

}

export default EditJob;