import { createContext, useState } from "react";


export const ApplicationContext = createContext();



function ApplicationProvider({ children }) {


    const [applications, setApplications] = useState(() => {

        const savedApplications = localStorage.getItem("applications");

        return savedApplications
            ? JSON.parse(savedApplications)
            : [];

    });



    // Add New Application

    const addApplication = (newApplication) => {


        const updatedApplications = [

            ...applications,

            {
                id: Date.now(),
                status: "Pending",
                ...newApplication,
            }

        ];


        console.log("Saving Applications:", updatedApplications);



        setApplications(updatedApplications);



        localStorage.setItem(

            "applications",

            JSON.stringify(updatedApplications)

        );


    };



    // Get Applications For A Particular Job

    const getJobApplications = (jobId) => {


        return applications.filter(

            (application) => application.jobId === jobId

        );


    };



    // Update Application Status

    const updateApplicationStatus = (id, status) => {


        const updatedApplications = applications.map(

            (application) => {


                if(application.id === id){


                    return {

                        ...application,

                        status: status

                    };


                }


                return application;


            }

        );



        setApplications(updatedApplications);



        localStorage.setItem(

            "applications",

            JSON.stringify(updatedApplications)

        );


    };



    return (

        <ApplicationContext.Provider

            value={{

                applications,

                addApplication,

                getJobApplications,

                updateApplicationStatus,

            }}

        >

            {children}

        </ApplicationContext.Provider>

    );

}


export default ApplicationProvider;