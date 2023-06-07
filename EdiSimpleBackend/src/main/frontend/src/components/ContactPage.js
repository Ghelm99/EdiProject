import React, { useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";

const ContactPage = () => {
	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [message, setMessage] = useState("");
	const [rating, setRating] = useState(0);
	const [suggestions, setSuggestions] = useState("");

	const handleSubmit = (e) => {
		e.preventDefault();
		const emailContent = `Name: ${name}\nEmail: ${email}\nMessage: ${message}\nRating: ${rating}\nSuggestions: ${suggestions}`;
		window.location.href = `mailto:contact@yourdomain.com?subject=Contact Form Submission&body=${emailContent}`;
		setName("");
		setEmail("");
		setMessage("");
		setRating(0);
		setSuggestions("");
	};

	const handleRatingChange = (e) => {
		setRating(Number(e.target.value));
	};

	return (
		<Container className="col-md-4 offset-md-4 text-center">
			<Row>
				<Col>
					<h1>Contact Us</h1>
					<Form onSubmit={handleSubmit}>
						<Form.Group controlId="formName">
							<Form.Control
								type="text"
								placeholder="Enter your name"
								value={name}
								onChange={(e) => setName(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formEmail">
							<Form.Control
								type="email"
								placeholder="Enter your email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formMessage">
							<Form.Control
								as="textarea"
								rows={3}
								placeholder="Enter your message"
								value={message}
								onChange={(e) => setMessage(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formRating">
							<Form.Control
								as="select"
								value={rating}
								className="mb-3"
								onChange={handleRatingChange}>
								<option value={0}>Select rating</option>
								<option value={1}>1 star</option>
								<option value={2}>2 stars</option>
								<option value={3}>3 stars</option>
								<option value={4}>4 stars</option>
								<option value={5}>5 stars</option>
							</Form.Control>
						</Form.Group>
						<Form.Group controlId="formSuggestions">
							<Form.Control
								as="textarea"
								rows={3}
								placeholder="Enter your suggestions"
								value={suggestions}
								className="mb-3"
								onChange={(e) => setSuggestions(e.target.value)}
							/>
						</Form.Group>
						<Button variant="primary" type="submit">
							Submit
						</Button>
					</Form>
				</Col>
			</Row>
		</Container>
	);
};

export default ContactPage;
