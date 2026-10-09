import { Link } from "react-router-dom";

function Sidebar() {

  return (

    <div className="sidebar">

      <div className="sidebar-logo">

        <h2>

          Job Portal

        </h2>

      </div>

      <ul className="sidebar-menu">

        <li>

          <Link to="/applicantdashboard">

            🏠 Dashboard

          </Link>

        </li>

        <li>

          <Link to="/profile">

            👤 Profile

          </Link>

        </li>

        <li>

          <Link to="/resume">

            📄 Resume

          </Link>

        </li>

        <li>

          <Link to="/appliedjobs">

            💼 Applied Jobs

          </Link>

        </li>

        <li>

          <Link to="/savedjobs">

            ⭐ Saved Jobs

          </Link>

        </li>

        <li>

          <Link to="/settings">

            ⚙ Settings

          </Link>

        </li>

        <li>

          <Link to="/">

            🚪 Logout

          </Link>

        </li>

      </ul>

    </div>

  );

}

export default Sidebar;