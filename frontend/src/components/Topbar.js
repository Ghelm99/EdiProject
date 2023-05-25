import { useState } from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";

const Topbar = ({ userEmail, setUserEmail, handleNavigate }) => {
	const handleLogout = () => {
		handleNavigate("/");
		setUserEmail("");
	};

	const [expanded, setExpanded] = useState(false);

	return (
		<>
			<Navbar bg="primary" variant="dark" expand="lg" expanded={expanded}>
				<Container fluid>
					<Navbar.Brand as={Link} to="/">
						Simple library
					</Navbar.Brand>
					<Navbar.Toggle
						aria-controls="basic-navbar-nav"
						onClick={() => setExpanded(expanded ? false : "expanded")}
					/>
					<Navbar.Collapse id="basic-navbar-nav">
						<Nav className="me-auto">
							{userEmail && (
								<>
									<Nav.Link as={Link} to="/catalogue">
										Book catalog
									</Nav.Link>
									<Nav.Link as={Link} to="/createLoan">
										Create loan
									</Nav.Link>
									<Nav.Link as={Link} to="/loans">
										Active loans
									</Nav.Link>
								</>
							)}
							<Nav.Link as={Link} to="/contacts">
								Contacts
							</Nav.Link>
						</Nav>
						{userEmail ? (
							<Nav className="ms-auto">
								<Nav.Item className="d-flex align-items-center">
									<Nav.Link as={Link} to="/" className="me-3">
										<i
											className="bi bi-person-circle me-2"
											style={{ fontSize: "1.5rem" }}></i>{" "}
										{userEmail}
									</Nav.Link>
									<Button variant="danger" onClick={handleLogout}>
										Log out
									</Button>
								</Nav.Item>
							</Nav>
						) : (
							<Nav className="ms-auto">
								<Nav.Link as={Link} to="/login">
									Log in
								</Nav.Link>
								<Nav.Link as={Link} to="/signup">
									Sign up
								</Nav.Link>
							</Nav>
						)}
					</Navbar.Collapse>
				</Container>
			</Navbar>
		</>
	);
};

export default Topbar;
