import React, { useState } from "react";
import { Button, Form, Container, Row, Col } from "react-bootstrap";

const ChangePasswordPage = ({ email }) => {
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChangePassword = async (e) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setErrorMessage("Passwords do not match.");
      return;
    }

    try {
      const response = await fetch(`/users?email=${email}&password=${password}&newPassword=${newPassword}`, {
        method: "PUT",
      });

      if (response.ok) {
        // Password changed successfully
        console.log("Password changed successfully");
        // Reset form fields
        setPassword("");
        setNewPassword("");
        setConfirmPassword("");
        setErrorMessage("");
      } else if (response.status === 401) {
        setErrorMessage("Invalid password. Please try again.");
      } else {
        setErrorMessage("An error occurred. Please try again later.");
      }
    } catch (error) {
      setErrorMessage("An error occurred. Please try again later.");
    }
  };

  return (
    <Container>
			<Row>
				<Col className="col-md-4 offset-md-4 text-center">
					<h1>Change your password:</h1>
					<Form onSubmit={handleChangePassword}>
						<Form.Group controlId="formBasicPassword">
							<Form.Control
								placeholder="Enter Current Password"
								value={password}
                                type="password"
								onChange={(e) => setPassword(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formBasicConfirmPassword">
							<Form.Control
								placeholder="Enter New Password"
								value={newPassword}
                                type="password"
								onChange={(e) => setNewPassword(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>
						<Form.Group controlId="formBasicConfirmPassword">
							<Form.Control
								type="password"
								placeholder="Confirm New Password"
								value={confirmPassword}
								onSelect={() => {						
								}}
								onChange={(e) => setConfirmPassword(e.target.value)}
								className="mb-3"
							/>
						</Form.Group>

                        {errorMessage && <p className="text-danger">{errorMessage}</p>}
						<Button variant="primary" type="submit">
							Submit
						</Button>
					</Form>
				</Col>
			</Row>
		</Container>
	);
};

export default ChangePasswordPage;
