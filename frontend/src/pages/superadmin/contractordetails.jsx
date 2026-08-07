import { useParams, Link } from "react-router-dom";

import contractors from "../../data/contractordata.js";

function ContractorDetails() {

    const { id } = useParams();

    const contractor = contractors.find(

        (item) => item.id === Number(id)

    );

    if (!contractor) {

        return (

            <h2>

                Contractor Not Found

            </h2>

        );

    }

    return (

        <div className="contractor-details-page">

            <div className="contractor-details-card">

                <h1>Contractor Details</h1>

                <p><strong>Name:</strong> {contractor.name}</p>

                <p><strong>Company:</strong> {contractor.company}</p>

                <p><strong>Email:</strong> {contractor.email}</p>

                <p><strong>Phone:</strong> {contractor.phone}</p>

                <p><strong>Location:</strong> {contractor.location}</p>

                <p><strong>Jobs Posted:</strong> {contractor.jobsPosted}</p>

                <p><strong>Joined On:</strong> {contractor.joined}</p>

                <p><strong>Status:</strong> {contractor.status}</p>

                <br />

                <Link to="/superadmin/contractors">

                    <button className="post-btn">

                        Back

                    </button>

                </Link>

            </div>

        </div>

    );

}

export default ContractorDetails;