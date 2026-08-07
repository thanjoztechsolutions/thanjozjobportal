import { Link } from "react-router-dom";

import contractors from "../../data/contractordata";

function Contractors() {

    return (

        <div className="admin-contractors-page">

            <div className="admin-contractors-container">

                <h1>Manage Contractors</h1>

                <table className="admin-contractors-table">

                    <thead>

                        <tr>

                            <th>Name</th>

                            <th>Company</th>

                            <th>Email</th>

                            <th>Status</th>

                            <th>Action</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            contractors.map((contractor) => (

                                <tr key={contractor.id}>

                                    <td>{contractor.name}</td>

                                    <td>{contractor.company}</td>

                                    <td>{contractor.email}</td>

                                    <td>

                                        <span
                                            className={
                                                contractor.status === "Active"
                                                    ? "status active"
                                                    : "status blocked"
                                            }
                                        >
                                            {contractor.status}
                                        </span>

                                    </td>

                                    <td>

                                        <div className="contractor-actions">

                                            <Link
                                                to={`/superadmin/contractor/${contractor.id}`}
                                            >

                                                <button className="view-btn">

                                                    View

                                                </button>

                                            </Link>

                                            <button className="delete-btn">

                                                Remove

                                            </button>

                                        </div>

                                    </td>

                                </tr>

                            ))

                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default Contractors;