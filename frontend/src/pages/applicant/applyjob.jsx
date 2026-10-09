import { useParams, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";

import { JobContext } from "../../context/jobcontext.jsx";

import { ApplicationContext } from "../../context/applicationcontext.jsx";


function ApplyJob() {


    const { id } = useParams();

    const navigate = useNavigate();


    const { addApplication } = useContext(ApplicationContext);



    const job = jobs.find(

        (item) => item.id === Number(id)

    );



   const [application, setApplication] = useState({

    jobId: Number(id),

    jobTitle: job.title,

    company: job.company,

    location: job.location,

    name: "",

    email: "",

    phone: "",

    qualification: "",

    experience: "",

    coverLetter: "",

    resume: ""

});



    const handleChange = (e)=>{


        setApplication({

            ...application,

            [e.target.name]:e.target.value

        });


    };



    function handleSubmit(e){


        e.preventDefault();



        addApplication(application);



        alert("Application Submitted Successfully!");



        navigate("/success");


    }



    return (


        <div className="apply-page">


            <div className="apply-container">


                <h1>
                    Apply for {job.title}
                </h1>



                <form onSubmit={handleSubmit}>


                    <input
                        type="text"
                        name="name"
                        placeholder="Full Name"
                        value={application.name}
                        onChange={handleChange}
                        required
                    />



                    <input
                        type="email"
                        name="email"
                        placeholder="Email Address"
                        value={application.email}
                        onChange={handleChange}
                        required
                    />



                    <input
                        type="tel"
                        name="phone"
                        placeholder="Mobile Number"
                        value={application.phone}
                        onChange={handleChange}
                        required
                    />



                    <input
                        type="text"
                        name="qualification"
                        placeholder="Highest Qualification"
                        value={application.qualification}
                        onChange={handleChange}
                    />



                    <input
                        type="text"
                        name="experience"
                        placeholder="Years of Experience"
                        value={application.experience}
                        onChange={handleChange}
                    />



                    <textarea

                        name="coverLetter"

                        rows="5"

                        placeholder="Cover Letter"

                        value={application.coverLetter}

                        onChange={handleChange}

                    ></textarea>



                    <input

                        type="file"

                        name="resume"
                        accept=".pdf,.doc,.docx"

                    />



                    <button type="submit">

                        Submit Application

                    </button>


                </form>


            </div>


        </div>


    );

}


export default ApplyJob;