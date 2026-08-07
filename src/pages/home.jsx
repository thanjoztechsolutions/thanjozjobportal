import { useState, useContext } from "react";

import Navbar from "../components/navbar";
import Footer from "../components/footer";
import SearchBar from "../components/searchbar";
import Filter from "../components/filter";
import JobCard from "../components/jobcard";

import { JobContext } from "../context/jobcontext";

import infosys from "../assets/images/infosys.png";
import tcs from "../assets/images/tcs.png";
import zoho from "../assets/images/zoho.png";
import wipro from "../assets/images/wipro.png";
import accenture from "../assets/images/accenture.png";
import cognizant from "../assets/images/cognizant.png";


import {
  FaLaptopCode,
  FaCog,
  FaHardHat,
  FaBolt,
  FaChartLine,
  FaMoneyBillWave,
} from "react-icons/fa";



function Home(){


  const [searchTerm,setSearchTerm] = useState("");


  // Getting live jobs from JobContext

  const { jobs } = useContext(JobContext);



  const filteredJobs = jobs.filter((job)=>{


    const value = searchTerm.toLowerCase();

   

    return (

      job.title.toLowerCase().includes(value) ||

      job.company.toLowerCase().includes(value) ||

      job.location.toLowerCase().includes(value)

    );


  });

console.table(jobs);



  return (

    <div className="home-page">



      <Navbar />



      {/* Hero Section */}


      <section className="hero">


        <h1>
          Find Your Dream Job Today
        </h1>


        <p>
          Discover thousands of job opportunities from trusted companies
          across India.
        </p>


      </section>





      {/* Search */}


      <SearchBar

        searchTerm={searchTerm}

        setSearchTerm={setSearchTerm}

      />




      <Filter />







      {
        searchTerm === "" && (

        <>






        {/* Statistics */}


        <section className="stats-section">


          <div className="stat-card">

            <h2>
              10,000+
            </h2>

            <p>
              Jobs Posted
            </p>

          </div>




          <div className="stat-card">

            <h2>
              500+
            </h2>

            <p>
              Companies
            </p>

          </div>




          <div className="stat-card">

            <h2>
              50,000+
            </h2>

            <p>
              Applicants
            </p>

          </div>




          <div className="stat-card">

            <h2>
              98%
            </h2>

            <p>
              Hiring Success
            </p>

          </div>


        </section>









        {/* Categories */}


        <section className="category-section">


          <h2>
            Popular Categories
          </h2>




          <div className="category-container">



            <div className="category-card">

              <FaLaptopCode className="category-icon"/>

              <h3>
                IT Jobs
              </h3>

            </div>




            <div className="category-card">

              <FaCog className="category-icon"/>

              <h3>
                Mechanical
              </h3>

            </div>




            <div className="category-card">

              <FaHardHat className="category-icon"/>

              <h3>
                Civil
              </h3>

            </div>




            <div className="category-card">

              <FaBolt className="category-icon"/>

              <h3>
                Electrical
              </h3>

            </div>




            <div className="category-card">

              <FaChartLine className="category-icon"/>

              <h3>
                Marketing
              </h3>

            </div>




            <div className="category-card">

              <FaMoneyBillWave className="category-icon"/>

              <h3>
                Finance
              </h3>

            </div>



          </div>


        </section>









        {/* Companies */}


        <section className="companies-section">


          <h2>
            Top Hiring Companies
          </h2>




          <div className="companies-container">



            <div className="company-card">

              <img src={infosys} alt="Infosys"/>

            </div>



            <div className="company-card">

              <img src={tcs} alt="TCS"/>

            </div>



            <div className="company-card">

              <img src={zoho} alt="Zoho"/>

            </div>



            <div className="company-card">

              <img src={wipro} alt="Wipro"/>

            </div>



            <div className="company-card">

              <img src={accenture} alt="Accenture"/>

            </div>



            <div className="company-card">

              <img src={cognizant} alt="Cognizant"/>

            </div>



          </div>


        </section>




        </>

        )

      }









      {/* Jobs */}


      <section className="jobs-section">


        <h2>

        {
          searchTerm === "" 
          ? 
          "Latest Jobs"
          :
          "Search Results"
        }

        </h2>





        <div className="jobs-container">



        {


          filteredJobs.length > 0 ?


          (

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

            <h3>
              No Jobs Found
            </h3>

          )


        }



        </div>



      </section>







      <Footer />



    </div>

  );

}



export default Home;