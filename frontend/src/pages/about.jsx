import { Link } from "react-router-dom";

function About() {

    return (

        <div className="about-page">

            <section className="about-hero">

                <h1>About JobPortal</h1>

                <p>

                    Connecting talented professionals with leading companies
                    through a simple, secure and modern recruitment platform.

                </p>

            </section>


            <section className="about-section">

                <h2>Who We Are</h2>

                <p>

                    JobPortal is an online recruitment platform designed to
                    simplify the hiring process. Whether you are a job seeker
                    looking for your dream career or a company searching for
                    talented professionals, our platform makes recruitment
                    easier, faster and more efficient.

                </p>

            </section>


            <section className="mission-vision">

                <div className="about-box">

                    <h2>🎯 Our Mission</h2>

                    <p>

                        To connect employers and job seekers through an
                        efficient and reliable hiring platform.

                    </p>

                </div>

                <div className="about-box">

                    <h2>🚀 Our Vision</h2>

                    <p>

                        To become one of the most trusted digital recruitment
                        platforms by delivering quality hiring experiences.

                    </p>

                </div>

            </section>


            <section className="why-us">

                <h2>Why Choose Us?</h2>

                <div className="features">

                    <div className="feature-card">

                        <h3>✔ Verified Companies</h3>

                        <p>Only trusted employers post jobs.</p>

                    </div>

                    <div className="feature-card">

                        <h3>💼 Thousands of Jobs</h3>

                        <p>Explore opportunities across industries.</p>

                    </div>

                    <div className="feature-card">

                        <h3>⚡ Easy Applications</h3>

                        <p>Apply for jobs quickly with a simple process.</p>

                    </div>

                    <div className="feature-card">

                        <h3>🔒 Secure Platform</h3>

                        <p>Your information is handled securely.</p>

                    </div>

                </div>

            </section>


            <section className="stats-section">

                <div className="stat-card">

                    <h1>1200+</h1>

                    <p>Jobs Posted</p>

                </div>

                <div className="stat-card">

                    <h1>500+</h1>

                    <p>Companies</p>

                </div>

                <div className="stat-card">

                    <h1>3500+</h1>

                    <p>Applicants</p>

                </div>

                <div className="stat-card">

                    <h1>95%</h1>

                    <p>Hiring Success</p>

                </div>

            </section>


            <section className="about-footer">

                <h2>Start Your Career Journey Today</h2>

                <Link to="/jobs">

                    <button className="post-btn">

                        Browse Jobs

                    </button>

                </Link>

            </section>

        </div>

    );

}

export default About;