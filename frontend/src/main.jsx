import React from "react";
import ReactDOM from "react-dom/client";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

import "./index.css";

import Home from "./pages/home.jsx";
import Jobs from "./pages/jobs.jsx";
import About from "./pages/about.jsx";
import Contact from "./pages/contact.jsx";
import Login from "./pages/login.jsx";
import Register from "./pages/register.jsx";
import JobDetails from "./pages/applicant/jobdetails.jsx";
import ContractorDashboard from "./pages/contractor/contractordashboard.jsx";
import PostJob from "./pages/contractor/postjob.jsx";
import MyJobs from "./pages/contractor/myjobs.jsx";
import Applicants from "./pages/contractor/applicants.jsx";
import Profile from "./pages/contractor/profile.jsx";
import JobProvider from "./context/jobcontext.jsx";
import ApplicationProvider from "./context/applicationcontext.jsx";
import Success from "./pages/success.jsx";
import ApplyJob from "./pages/applicant/applyjob.jsx";
import ApplicantDashboard from "./pages/applicant/applicantdashboard.jsx";
import MyApplications from "./pages/applicant/myapplications.jsx";
import AdminDashboard from "./pages/superadmin/dashboard.jsx";
import SuperAdminJobs from "./pages/superadmin/jobs.jsx";
import Contractors from "./pages/superadmin/contractors.jsx";
import SuperAdminApplicants from "./pages/superadmin/applicants.jsx";
import Reports from "./pages/superadmin/reports.jsx";
import AdminApplicants from "./pages/superadmin/applicants.jsx";
import ContractorDetails from "./pages/superadmin/contractordetails.jsx";
import EditJob from "./pages/contractor/editjob.jsx";
import AllApplicants from "./pages/contractor/allapplicants.jsx";





const router = createBrowserRouter([

  {
    path:"/",
    element:<Home/>
  },
   
  {
  path:"/job/:id",
  element:<JobDetails/>
  },

  {
    path:"/jobs",
    element:<Jobs/>
  },

  {
    path:"/about",
    element:<About/>
  },

  {
    path:"/contact",
    element:<Contact/>
  },

  {
    path:"/login",
    element:<Login/>
  },

  {
    path:"/register",
    element:<Register/>
  },

  {
  path: "/contractor/dashboard",
  element: <ContractorDashboard />
},
{
  path: "/contractor/postjob",
  element: <PostJob />
},
{
  path: "/contractor/myjobs",
  element: <MyJobs />
},
{
  path: "/contractor/applicants/:jobId",
  element: <Applicants />
},
{
  path: "/contractor/profile",
  element: <Profile />
},

{
  path:"/apply/:id",
  element:<ApplyJob/>
},

{
  path:"/success",
  element:<Success/>
},

{
    path: "/applicant/dashboard",
    element: <ApplicantDashboard />
},
{
    path: "/applicant/myapplications",
    element: <MyApplications />
},

{
    path: "/superadmin/dashboard",
    element: <AdminDashboard />
},

{
    path: "/superadmin/jobs",
    element: <SuperAdminJobs />
},

{
    path: "/superadmin/contractors",
    element: <Contractors />
},

{
    path: "/superadmin/applicants",
    element: <SuperAdminApplicants />
},

{
    path: "/superadmin/reports",
    element: <Reports />
},

{
  path: "/superadmin/contractor/:id",
  element: <ContractorDetails />
},

{
  path: "/contractor/editjob/:id",
  element: <EditJob />
},

{
    path: "/contractor/allapplicants",
    element: <AllApplicants />
},

]);



ReactDOM.createRoot(document.getElementById("root")).render(

  <React.StrictMode>

 <JobProvider>

    <ApplicationProvider>

        <RouterProvider router={router}/>

    </ApplicationProvider>

</JobProvider>

  </React.StrictMode>

);