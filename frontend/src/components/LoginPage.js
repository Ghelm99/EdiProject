import React, { useState } from "react";
import { Alert, Button, Col, Container, Form, Row } from "react-bootstrap";

async function loginUser(credentials) {
	const response = await fetch(
		`http://localhost:8080/userAccess/login?email=${credentials.emailInput}&password=${credentials.passwordInput}`,
		{
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
		}
	);

	const responseBody = await response.json();

	return {
		email: responseBody.email,
		password: responseBody.password,
	};
}

const LoginPage = ({ setEmail, setPassword, handleNavigate }) => {
	const [emailInput, setEmailInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [error, setError] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			if (emailInput === "" || passwordInput === "") {
				setError("Please, complete the form.");
			} else {
				const { email, password } = await loginUser({
					emailInput,
					passwordInput,
				});
				setEmail(email);
				setPassword(password);
				handleNavigate("/");
			}
		} catch (error) {
			setError("Wrong credentials!");
		}
	};

	return (
		<Container>
			<Row>
				<Col className="col-md-4 offset-md-4 text-center">
					<h1>Please, log in!</h1>
					<Form onSubmit={handleSubmit}>
						<Form.Group controlId="formBasicEmail">
							<Form.Control
								type="email"
								placeholder="Email"
								value={emailInput}
								onSelect={() => setError("")}
								onChange={(e) => setEmailInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

						<Form.Group controlId="formBasicPassword">
							<Form.Control
								type="password"
								placeholder="Password"
								value={passwordInput}
								onSelect={() => setError("")}
								onChange={(e) => setPasswordInput(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						{error && (
							<Alert variant="danger" className="text-center">
								{error}
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

export default LoginPage;
