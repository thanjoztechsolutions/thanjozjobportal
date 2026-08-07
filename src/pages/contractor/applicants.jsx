import { useParams } from "react-router-dom";
import { useContext } from "react";

import { ApplicationContext } from "../../context/applicationcontext";
import { JobContext } from "../../context/jobcontext";


function Applicants() {


    const { jobId } = useParams();


   const { applications, updateApplicationStatus } = useContext(ApplicationContext);

    const { jobs } = useContext(JobContext);



    const job = jobs.find(

        (job) => job.id === Number(jobId)

    );



    const jobApplications = applications.filter(

        (application) => application.jobId === Number(jobId)

    );



    return (

        <div className="applicants-page">


            <div className="applicants-container">


                <h1>
                    Applicants
                </h1>


                <h2>
                    {job ? job.title : "Job"}
                </h2>



                {

                    jobApplications.length === 0 ? (

                        <h3>
                            No Applications Yet
                        </h3>

                    )


                    :


                    (

                        jobApplications.map((app)=>(


                            <div 
                                className="applicant-card"
                                key={app.id}
                            >


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
                                    💼 Experience: {app.experience}
                                </p>


                                <p>
                                    📝 Status: {app.status}
                                </p>



                                <div className="applicant-actions">


    <button

className="accept-btn"

disabled={app.status !== "Pending"}

onClick={() => {

    updateApplicationStatus(
        app.id,
        "Accepted"
    );

}}

>

Accept

</button>


<button

className="reject-btn"

disabled={app.status !== "Pending"}

onClick={() => {

    updateApplicationStatus(
        app.id,
        "Rejected"
    );

}}

>

Reject

</button>


</div>


                            </div>


                        ))

                    )

                }


            </div>


        </div>

    );


}


export default Applicants;