import React from "react";
import ReactDOM from "react-dom/client";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

import "./index.css";

import Home from "./pages/home";
import Jobs from "./pages/jobs";
import About from "./pages/about";
import Contact from "./pages/contact";
import Login from "./pages/login";
import Register from "./pages/register";
import JobDetails from "./pages/applicant/jobdetails";
import ContractorDashboard from "./pages/contractor/contractordashboard";
import PostJob from "./pages/contractor/postjob";
import MyJobs from "./pages/contractor/myjobs";
import Applicants from "./pages/contractor/applicants";
import Profile from "./pages/contractor/profile";
import JobProvider from "./context/jobcontext";
import ApplicationProvider from "./context/applicationcontext";
import Success from "./pages/success";
import ApplyJob from "./pages/applicant/applyjob";
import ApplicantDashboard from "./pages/applicant/applicantdashboard";
import MyApplications from "./pages/applicant/myapplications";
import AdminDashboard from "./pages/superadmin/dashboard";
import SuperAdminJobs from "./pages/superadmin/jobs";
import Contractors from "./pages/superadmin/contractors";
import SuperAdminApplicants from "./pages/superadmin/applicants";
import Reports from "./pages/superadmin/reports";
import AdminApplicants from "./pages/superadmin/applicants";
import ContractorDetails from "./pages/superadmin/contractordetails";
import EditJob from "./pages/contractor/editjob";
import AllApplicants from "./pages/contractor/allapplicants";





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