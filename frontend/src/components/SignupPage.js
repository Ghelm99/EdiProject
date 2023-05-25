import React, { useState } from "react";
import { Alert, Button, Col, Container, Form, Row } from "react-bootstrap";

async function signupUser(credentials) {
	if (!(credentials.email === "") && !(credentials.password === "")) {
		const response = await fetch("http://localhost:8080/users", {
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
	}
}

const SignupPage = ({ handleNavigate }) => {
	const [nameInput, setNameInput] = useState("");
	const [surnameInput, setSurnameInput] = useState("");
	const [emailInput, setEmailInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [confirmPasswordInput, setConfirmPasswordInput] = useState("");
	const [passwordError, setPasswordError] = useState("");
	const [signupError, setSignupError] = useState("");
	const [success, setSuccess] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			if (!(passwordInput === confirmPasswordInput)) {
				setPasswordError("The two password don't match!");
			} else if (
				emailInput === "" ||
				passwordInput === "" ||
				confirmPasswordInput === ""
			) {
				setSignupError("Please, complete the form.");
			} else {
				const userData = {
					name: nameInput,
					surname: surnameInput,
					email: emailInput,
					password: passwordInput,
				};
				signupUser(userData);
				setSuccess("Sign up successful!");
			}
		} catch (error) {
			setSignupError(error.message);
			setNameInput("");
			setSurnameInput("");
			setEmailInput("");
			setPasswordInput("");
			setConfirmPasswordInput("");
		}
	};

	return (
		<Container>
			<Row>
				<Col className="col-md-4 offset-md-4 text-center">
					<h1>Please, sign up!</h1>
					<Form onSubmit={handleSubmit}>
						<Form.Group controlId="formBasicName">
							<Form.Control
								placeholder="Name"
								value={nameInput}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setNameInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formBasicSurname">
							<Form.Control
								placeholder="Surname"
								value={surnameInput}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setSurnameInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formBasicEmail">
							<Form.Control
								type="email"
								placeholder="Email"
								value={emailInput}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setEmailInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						<Form.Group controlId="formBasicPassword">
							<Form.Control
								type="password"
								placeholder="Password"
								value={passwordInput}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setPasswordInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						<Form.Group controlId="formBasicConfirmPassword">
							<Form.Control
								type="password"
								placeholder="Confirm Password"
								value={confirmPasswordInput}
								onSelect={() => {
									setPasswordError("");
									setSignupError("");
									setSuccess("");
								}}
								onChange={(e) => setConfirmPasswordInput(e.target.value)}
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
