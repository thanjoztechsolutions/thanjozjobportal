import { Link } from "react-router-dom";


function JobCard(props) {

  return (

    <div className="job-card">


      <h2>
        {props.title}
      </h2>



      <h4>
        🏢 {props.company}
      </h4>



      <p>
        📍 {props.location}
      </p>



      <p>
        💼 {props.type}
      </p>



      <p>
        ⭐ {props.experience}
      </p>



      <h3>
        {props.salary}
      </h3>



      <Link to={`/job/${props.id}`}>

        <button className="view-btn">

          View Details

        </button>

      </Link>


    </div>

  );

}


export default JobCard;