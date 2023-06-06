import { useState } from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import { Link, NavLink } from "react-router-dom";

const Topbar = ({ email, handleLogout }) => {

  const [expanded, setExpanded] = useState(false);

  const handleLogoutClick = async () => {
    try {
      await handleLogout("/");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <Navbar bg="primary" variant="dark" expand="lg" expanded={expanded}>
        <Container fluid>
          <Navbar.Brand as={Link} to="/">
            Simple library
          </Navbar.Brand>
          <Navbar.Toggle
            aria-controls="basic-navbar-nav"
            onClick={() => setExpanded((expanded) => !expanded)}
          />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              {email && (
                <>
                  <Nav.Link as={Link} to="/catalog">
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
            {email ? (
              <Nav className="ms-auto">
                <Nav.Item className="d-flex align-items-center">
                  <NavLink
                    to="/changePassword"
                    className="nav-link me-3"
                    activeclassname="active"
                  >
                    <i
                      className="bi bi-person-circle me-2"
                      style={{ fontSize: "1.5rem" }}
                    ></i>{" "}
                    {email}
                  </NavLink>
                  <Button variant="danger" onClick={handleLogoutClick}>
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
