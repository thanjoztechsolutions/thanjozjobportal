import { Link } from "react-router-dom";

function ApplicationSuccess() {

    return (

        <div className="success-page">

            <h1>🎉 Application Submitted!</h1>

            <p>

                Your application has been successfully submitted.

            </p>

            <Link to="/">

                <button>

                    Back to Home

                </button>

            </Link>

        </div>

    );

}

export default ApplicationSuccess;