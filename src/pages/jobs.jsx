import { useState } from "react";

import SearchBar from "../components/searchbar";
import Filter from "../components/filter";
import JobCard from "../components/jobcard";

import { useContext } from "react";
import { JobContext } from "../context/jobcontext";


function Jobs(){

  const { jobs } = useContext(JobContext);

  const [search,setSearch] = useState("");
  const [searchValue,setSearchValue] = useState("");


  const handleSearch = () => {

    setSearchValue(search.trim());

  };


  const filteredJobs = jobs.filter((job)=>{

const value = searchValue.toLowerCase().trim();


    if(value === ""){
      return true;
    }

return (
  job.title.toLowerCase().includes(value) ||
  job.company.toLowerCase().includes(value) ||
  job.location.toLowerCase().trim().includes(value)
);

  });



return (

<div className="jobs-page">


<h1>
Available Jobs
</h1>


<SearchBar

searchTerm={search}

setSearchTerm={setSearch}

handleSearch={handleSearch}

/>


<Filter />



<h2>
{
searchValue 
? `Search Results for "${searchValue}"`
: "All Jobs"
}
</h2>



<div className="jobs-container">


{
filteredJobs.length > 0 ? (

filteredJobs.map((job)=>(

<JobCard

key={job.id}

id={job.id}

title={job.title}

company={job.company}

location={job.location}

salary={job.salary}

type={job.type}

experience={job.experience}

/>

))

)

:

(

<h2>
No Jobs Found
</h2>

)

}


</div>


</div>

);

}


export default Jobs;