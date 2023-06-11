import React, { useEffect, useState } from "react";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import {
	Book,
	Buildings,
	CalendarX,
	Person,
	UpcScan,
} from "react-bootstrap-icons";

const ActiveLoansPage = () => {
	const [allUserLoans, setAllUserLoans] = useState([]);

	const fetchLoansData = async () => {
		try {
			const response = await fetch(
				`https://first-level-backend.up.railway.app/loans?email=${localStorage.getItem(
					"email"
				)}`,
				{
					method: "GET",
					headers: {
						"Content-Type": "application/json",
						// Passing the cookie here
						Cookie: document.cookie,
					},
				}
			);
			const data = await response.json();
			setAllUserLoans(data);
		} catch (error) {
			console.error(error);
		}
	};

	useEffect(() => {
		fetchLoansData();
	}, []);

	const deleteLoan = async (loanId) => {
		try {
			await fetch(
				`https://first-level-backend.up.railway.app/loans?loanId=${loanId}`,
				{
					method: "DELETE",
				}
			);
			await fetchLoansData();
		} catch (error) {
			console.error(error);
		}
	};

	const getColumnsForRow = () => {
		let items = allUserLoans.map((loan) => {
			return (
				<Col
					sm={12}
					md={12}
					lg={6}
					xl={6}
					xxl={4}
					className="p-4"
					key={loan.loan.loanId}>
					<Card className="flex-grow-1">
						<Card.Body>
							<Card.Title style={{ marginBottom: "1rem" }}>
								<Book className="me-3" />
								{loan.book.title}
							</Card.Title>
							<Card.Text style={{ marginBottom: "1rem" }}>
								<Person className="me-3" />
								{loan.book.author} <br />
								<UpcScan className="me-3" />
								{loan.book.isbn} <br />
								<Buildings className="me-3" />
								{loan.book.publisher} <br />
								<CalendarX className="me-3" />
								{loan.loan.returnDate} <br />
							</Card.Text>

							<Button
								variant="danger"
								onClick={() => deleteLoan(loan.loan.loanId)}>
								Delete loan
							</Button>
						</Card.Body>
					</Card>
				</Col>
			);
		});
		return items;
	};

	return (
		<Container>
			<Row>{getColumnsForRow()}</Row>
		</Container>
	);
};

export default ActiveLoansPage;
