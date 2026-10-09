import { useState } from "react";

function Contact() {

    const [form, setForm] = useState({

        name: "",

        email: "",

        subject: "",

        message: ""

    });

    const handleChange = (e) => {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        alert("Thank you! Your message has been sent.");

        setForm({

            name: "",

            email: "",

            subject: "",

            message: ""

        });

    };

    return (

        <div className="contact-page">

            <section className="contact-hero">

                <h1>Contact Us</h1>

                <p>

                    We'd love to hear from you. Whether you're a job seeker
                    or an employer, feel free to get in touch with us.

                </p>

            </section>

            <section className="contact-info">

                <div className="contact-card">

                    <h2>📍 Address</h2>

                    <p>

                        JobPortal Headquarters<br/>

                        Chennai, Tamil Nadu, India

                    </p>

                </div>

                <div className="contact-card">

                    <h2>📞 Phone</h2>

                    <p>

                        +91 98765 43210

                    </p>

                </div>

                <div className="contact-card">

                    <h2>📧 Email</h2>

                    <p>

                        support@jobportal.com

                    </p>

                </div>

                <div className="contact-card">

                    <h2>🕒 Working Hours</h2>

                    <p>

                        Monday - Friday<br/>

                        9:00 AM - 6:00 PM

                    </p>

                </div>

            </section>

            <section className="contact-form-section">

                <div className="contact-form-container">

                    <h2>Send Us a Message</h2>

                    <form onSubmit={handleSubmit}>

                        <input
                            type="text"
                            name="name"
                            placeholder="Your Name"
                            value={form.name}
                            onChange={handleChange}
                            required
                        />

                        <input
                            type="email"
                            name="email"
                            placeholder="Email Address"
                            value={form.email}
                            onChange={handleChange}
                            required
                        />

                        <input
                            type="text"
                            name="subject"
                            placeholder="Subject"
                            value={form.subject}
                            onChange={handleChange}
                            required
                        />

                        <textarea
                            rows="6"
                            name="message"
                            placeholder="Your Message"
                            value={form.message}
                            onChange={handleChange}
                            required
                        ></textarea>

                        <button type="submit">

                            Send Message

                        </button>

                    </form>

                </div>

            </section>

        </div>

    );

}

export default Contact;