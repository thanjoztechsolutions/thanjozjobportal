import { createContext, useState } from "react";

import initialJobs from "../data/jobs";

export const JobContext = createContext();

function JobProvider({ children }) {

  const [jobs, setJobs] = useState(initialJobs);

  // Add New Job
  const addJob = (newJob) => {

    setJobs((previousJobs) => [

      ...previousJobs,

      {
        id: Date.now(),
        applicants: 0,
        ...newJob,
      },

    ]);

  };

  // Delete Job
  const deleteJob = (id) => {

    setJobs((previousJobs) =>

      previousJobs.filter((job) => job.id !== id)

    );

  };

  // Update Job
  const updateJob = (updatedJob) => {

    setJobs((previousJobs) =>

      previousJobs.map((job) =>

        job.id === updatedJob.id

          ? updatedJob

          : job

      )

    );

  };

  return (

    <JobContext.Provider

      value={{

        jobs,

        addJob,

        deleteJob,

        updateJob,

      }}

    >

      {children}

    </JobContext.Provider>

  );

}

export default JobProvider;