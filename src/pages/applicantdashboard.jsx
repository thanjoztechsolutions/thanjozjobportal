import Sidebar from "../components/sidebar";
import DashboardNavbar from "../components/dashboardnavbar";

function ApplicantDashboard() {

  return (

    <div className="dashboard-layout">

      <Sidebar />

      <div className="dashboard-main">

        <DashboardNavbar />

        {/* Dashboard Cards */}

        <div className="dashboard-cards">

          <div className="dashboard-card">

            <h3>

              Applied Jobs

            </h3>

            <h1>

              12

            </h1>

            <p>

              Total Applications

            </p>

          </div>

          <div className="dashboard-card">

            <h3>

              Pending

            </h3>

            <h1>

              6

            </h1>

            <p>

              Waiting for Review

            </p>

          </div>

          <div className="dashboard-card">

            <h3>

              Shortlisted

            </h3>

            <h1>

              3

            </h1>

            <p>

              Congratulations!

            </p>

          </div>

          <div className="dashboard-card">

            <h3>

              Rejected

            </h3>

            <h1>

              3

            </h1>

            <p>

              Better Luck Next Time

            </p>

          </div>

        </div>

        {/* Recent Applications */}

        <div className="applications-section">

          <h2>

            Recent Applications

          </h2>

          <table className="applications-table">

            <thead>

              <tr>

                <th>Job Title</th>

                <th>Company</th>

                <th>Location</th>

                <th>Status</th>

              </tr>

            </thead>

            <tbody>

              <tr>

                <td>Frontend Developer</td>

                <td>Infosys</td>

                <td>Chennai</td>

                <td className="pending">

                  Pending

                </td>

              </tr>

              <tr>

                <td>Mechanical Engineer</td>

                <td>L&T</td>

                <td>Chennai</td>

                <td className="shortlisted">

                  Shortlisted

                </td>

              </tr>

              <tr>

                <td>Backend Developer</td>

                <td>TCS</td>

                <td>Bangalore</td>

                <td className="rejected">

                  Rejected

                </td>

              </tr>

            </tbody>

          </table>

        </div>

                {/* Notifications */}

        <div className="notification-section">

          <h2>

            Notifications

          </h2>

          <div className="notification-card">

            <p>

              🔔 Infosys viewed your profile.

            </p>

          </div>

          <div className="notification-card">

            <p>

              🔔 Zoho shortlisted your application.

            </p>

          </div>

          <div className="notification-card">

            <p>

              🔔 TCS rejected your application.

            </p>

          </div>

          <div className="notification-card">

            <p>

              🔔 L&T scheduled your interview.

            </p>

          </div>

        </div>


        {/* Profile Summary */}

        <div className="profile-summary">

          <h2>

            My Profile

          </h2>

          <div className="profile-card">

            <div className="profile-image">

              👤

            </div>

            <div className="profile-details">

              <h3>

                Prakash S

              </h3>

              <p>

                Mechanical Engineer

              </p>

              <p>

                📧 prakash@gmail.com

              </p>

              <p>

                📞 +91 9876543210

              </p>

            </div>

          </div>

        </div>


        {/* Resume */}

        <div className="resume-section">

          <h2>

            Resume

          </h2>

          <div className="resume-card">

            <p>

              📄 Resume.pdf

            </p>

            <button className="resume-btn">

              View Resume

            </button>

          </div>

        </div>

      </div>

    </div>

  );

}

export default ApplicantDashboard;