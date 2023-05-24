import { Col, Container, Row } from "react-bootstrap";

const Footer = () => {
	return (
		<footer className="bg-primary py-1 fixed-bottom">
			<Container>
				<Row>
					<Col className="text-center">
						<p className="text-white m-0">&copy; Simple library 2023</p>
					</Col>
				</Row>
			</Container>
		</footer>
	);
};

export default Footer;
