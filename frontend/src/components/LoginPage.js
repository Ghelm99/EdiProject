import React, { useState } from "react";
import { Alert, Button, Col, Container, Form, Row } from "react-bootstrap";

async function loginUser(credentials) {
	const response = await fetch("http://localhost:8080/auth/login", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(credentials),
	});

	const data = await response.json();

	if (data.error && data.error === "Bad credentials") {
		throw new Error("Wrong credentials!");
	}

	return {
		accessToken: data.access_token,
		refreshToken: data.refresh_token,
	};
}

const LoginPage = ({
	setAccessToken,
	setRefreshToken,
	setEmail,
	handleNavigate,
}) => {
	const [emailInput, setEmailInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [error, setError] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			if (emailInput === "" || passwordInput === "") {
				setError("Please, complete the form.");
			} else {
				const { accessToken, refreshToken } = await loginUser({
					emailInput,
					passwordInput,
				});
				setAccessToken(accessToken);
				setRefreshToken(refreshToken);
				setEmail(emailInput);
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
