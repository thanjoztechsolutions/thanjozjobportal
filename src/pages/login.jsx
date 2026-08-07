import { useState } from "react";

function Login() {

  const [role, setRole] = useState("");

  const [loginType, setLoginType] = useState("gmail");

  return (

    <div className="login-page">

      <div className="login-container">

        <h1>

          Login

        </h1>

        <p>

          Choose your account

        </p>

        <div className="role-buttons">

          <button

            className={role === "applicant" ? "active-role" : ""}

            onClick={() => setRole("applicant")}

          >

            Applicant

          </button>

          <button

            className={role === "contractor" ? "active-role" : ""}

            onClick={() => setRole("contractor")}

          >

            Contractor

          </button>

          <button

            className={role === "admin" ? "active-role" : ""}

            onClick={() => setRole("admin")}

          >

            Super Admin

          </button>

        </div>

        {/* Applicant Login */}

        {role === "applicant" && (

          <form className="login-form">

            <div className="login-switch">

              <button

                type="button"

                onClick={() => setLoginType("gmail")}

              >

                Gmail

              </button>

              <button

                type="button"

                onClick={() => setLoginType("phone")}

              >

                Phone

              </button>

            </div>

            {

              loginType === "gmail" ?

              (

                <input

                  type="email"

                  placeholder="Enter Gmail"

                />

              )

              :

              (

                <input

                  type="tel"

                  placeholder="Enter Phone Number"

                />

              )

            }

            <button>

              Login

            </button>

          </form>

        )}

        {/* Contractor Login */}

        {role === "contractor" && (

          <form className="login-form">

            <input

              type="text"

              placeholder="Contractor ID"

            />

            <input

              type="text"

              placeholder="Username"

            />

            <input

              type="password"

              placeholder="Password"

            />

            <button>

              Login

            </button>

          </form>

        )}

        {/* Super Admin Login */}

        {role === "admin" && (

          <form className="login-form">

            <input

              type="text"

              placeholder="Admin ID"

            />

            <input

              type="password"

              placeholder="Password"

            />

            <button>

              Login

            </button>

          </form>

        )}

      </div>

    </div>

  );

}

export default Login;