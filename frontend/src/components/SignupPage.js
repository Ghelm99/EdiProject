import React, { useState } from "react";
import { Alert, Button, Col, Container, Form, Row } from "react-bootstrap";

async function signupUser(credentials) {
	if (!(credentials.email === "") && !(credentials.password === "")) {
		const response = await fetch("http://localhost:8080/auth/signup", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(credentials),
		});

		const data = await response.json();

		if (data.error && data.error === "Bad credentials") {
			throw new Error("Bad credentials");
		}

		if (data.access_token === "" && data.refresh_token === "") {
			throw new Error("Sign up unsuccessful!");
		}
	}
}

const SignupPage = ({ handleNavigate }) => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [confirmPassword, setConfirmPassword] = useState("");
	const [passwordError, setPasswordError] = useState("");
	const [signupError, setSignupError] = useState("");
	const [success, setSuccess] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			if (!(password === confirmPassword)) {
				setPasswordError("The two password don't match!");
			} else if (email === "" || password === "" || confirmPassword === "") {
				setSignupError("Please, complete the form.");
			} else {
				signupUser({ email, password });
				setSuccess("Sign up successful!");
			}
		} catch (error) {
			setSignupError(error.message);
			setEmail("");
			setPassword("");
		}
	};

	return (
		<Container>
			<Row>
				<Col className="col-md-4 offset-md-4 text-center">
					<h1>Please, sign up!</h1>
					<Form onSubmit={handleSubmit}>
						<Form.Group controlId="formBasicEmail">
							<Form.Control
								type="email"
								placeholder="Email"
								value={email}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setEmail(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						<Form.Group controlId="formBasicPassword">
							<Form.Control
								type="password"
								placeholder="Password"
								value={password}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setPassword(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						<Form.Group controlId="formBasicConfirmPassword">
							<Form.Control
								type="password"
								placeholder="Confirm Password"
								value={confirmPassword}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setConfirmPassword(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						{passwordError && (
							<Alert variant="danger" className="mb-3">
								{passwordError}
							</Alert>
						)}

						{signupError && (
							<Alert variant="danger" className="mb-3">
								{signupError}
							</Alert>
						)}

						{success && (
							<Alert variant="success" className="mb-3">
								{success} Click <a href="/login">here</a> to log in.
							</Alert>
						)}

						<Button variant="primary" type="submit">
							Submit
						</Button>
					</Form>
				</Col>
			</Row>
		</Container>
	);
};

export default SignupPage;
