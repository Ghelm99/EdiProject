import React from "react";
import { Button, Col, Container } from "react-bootstrap";

const HomePage = ({ handleNavigate, userEmail }) => {
	return (
		<Container className="text-center">
			<Col className="col-md-4 offset-md-4 text-center">
				<div className="d-flex flex-column align-items-center">
					<h1>Welcome to Simple library!</h1>
					<p>
						At Simple library, we believe that reading is one of life's greatest
						pleasures. <br></br> Our mission is to provide a convenient,
						user-friendly platform where you can explore a vast collection of
						books and discover your next great read.
					</p>
					{userEmail ? (
						<Button
							variant="primary"
							onClick={() => handleNavigate("/catalogue")}>
							Search for books
						</Button>
					) : (
						<Button variant="primary" onClick={() => handleNavigate("/login")}>
							Login
						</Button>
					)}
				</div>
			</Col>
		</Container>
	);
};

export default HomePage;
