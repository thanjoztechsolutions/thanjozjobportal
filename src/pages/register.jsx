import { useState } from "react";

function Register() {

  const [role, setRole] = useState("");

  const [contractorCounter, setContractorCounter] = useState(1001);

  const [contractor, setContractor] = useState({

    contractorId: "",

    companyName: "",

    contractorName: "",

    username: "",

    password: "",

    confirmPassword: "",

    email: "",

    phone: "",

    address: "",

    gst: ""

  });

  const registerContractor = (e) => {

    e.preventDefault();

    if (contractor.password !== contractor.confirmPassword) {

      alert("Passwords do not match!");

      return;

    }

    const generatedId = "CTR" + contractorCounter;

    const newContractor = {

      ...contractor,

      contractorId: generatedId

    };

    console.log(newContractor);

    alert(
      "Registration Successful!\n\nYour Contractor ID is : " +
      generatedId
    );

    setContractorCounter(contractorCounter + 1);

    setContractor({

      contractorId: "",

      companyName: "",

      contractorName: "",

      username: "",

      password: "",

      confirmPassword: "",

      email: "",

      phone: "",

      address: "",

      gst: ""

    });

  };

  return (

    <div className="register-page">

      <div className="register-container">

        <h1>Create Account</h1>

        <p>Select your role</p>

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

        </div>

        {/* Applicant Form */}

        {role === "applicant" && (

          <form className="register-form">

            <input
              type="text"
              placeholder="First Name"
            />

            <input
              type="text"
              placeholder="Last Name"
            />

            <input
              type="tel"
              placeholder="Phone Number"
            />

            <input
              type="email"
              placeholder="Gmail"
            />

            <label className="upload-label">

              Upload Resume

            </label>

            <input type="file" />

            <button>

              Register

            </button>

          </form>

        )}

        {/* Contractor Form */}

        {role === "contractor" && (

          <form
            className="register-form"
            onSubmit={registerContractor}
          >

            <input
              type="text"
              placeholder="Company Name"
              value={contractor.companyName}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  companyName: e.target.value,
                })
              }
            />

            <input
              type="text"
              placeholder="Contractor Name"
              value={contractor.contractorName}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  contractorName: e.target.value,
                })
              }
            />

            <input
              type="text"
              placeholder="Username"
              value={contractor.username}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  username: e.target.value,
                })
              }
            />

            <input
              type="password"
              placeholder="Password"
              value={contractor.password}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  password: e.target.value,
                })
              }
            />

            <input
              type="password"
              placeholder="Confirm Password"
              value={contractor.confirmPassword}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  confirmPassword: e.target.value,
                })
              }
            />

            <input
              type="email"
              placeholder="Company Email"
              value={contractor.email}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  email: e.target.value,
                })
              }
            />

            <input
              type="tel"
              placeholder="Phone Number"
              value={contractor.phone}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  phone: e.target.value,
                })
              }
            />

            <input
              type="text"
              placeholder="Company Address"
              value={contractor.address}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  address: e.target.value,
                })
              }
            />

            <input
              type="text"
              placeholder="GST Number"
              value={contractor.gst}
              onChange={(e) =>
                setContractor({
                  ...contractor,
                  gst: e.target.value,
                })
              }
            />

            <label className="upload-label">

              Upload Company License

            </label>

            <input type="file" />

            <button type="submit">

              Register

            </button>

          </form>

        )}

      </div>

    </div>

  );

}

export default Register;