import React, { useEffect, useState } from "react";
import { Alert, Button, Card, Container, Form, Table } from "react-bootstrap";
import { Book, Buildings, Person, UpcScan } from "react-bootstrap-icons";

const CreateLoanPage = () => {
	const [selectedBook, setSelectedBook] = useState();
	const [isBookSelected, setIsBookSelected] = useState(false);
	const [isLoanCreated, setIsLoanCreated] = useState(false);
	const [pageBooks, setPageBooks] = useState([]);
	const [allBooks, setAllBooks] = useState([]);
	const [queryUrl, setQueryUrl] = useState("/books?page=0&size=10");
	const [currentPage, setCurrentPage] = useState(0);
	const [recordsPerPage, setRecordsPerPage] = useState(10);
	const [searching, setSearching] = useState(false);
	const [title, setTitle] = useState("");
	const [author, setAuthor] = useState("");
	const [isbn, setIsbn] = useState("");
	const [publisher, setPublisher] = useState("");

	useEffect(() => {
		fetch("/books", {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => setAllBooks(data))
			.catch((error) => console.error(error));
	}, []);

	useEffect(() => {
		fetch(queryUrl, {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => setPageBooks(data))
			.catch((error) => console.error(error));
	}, [queryUrl]);

	const handlePageChange = (pageNumber) => {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		setCurrentPage(pageNumber);
		setQueryUrl(`/books?page=${pageNumber}&size=${recordsPerPage}`);
	};

	const handleRecordsPerPageChange = (recordsPerPage) => {
		setCurrentPage(0);
		setRecordsPerPage(recordsPerPage);
		setQueryUrl(`/books?page=${0}&size=${recordsPerPage}`);
	};

	const hasPreviousPage = () => {
		return currentPage > 0;
	};

	const hasNextPage = () => {
		return (currentPage + 1) * recordsPerPage < allBooks.length;
	};

	const handleSelectBook = (book) => {
		setSelectedBook(book);
		setIsBookSelected(true);
	};

	const handleChangeSelectedBook = () => {
		setSelectedBook();
		setIsBookSelected(false);
	};

	const handleCreateLoan = async (bookId) => {
		const email = localStorage.getItem("email");
		try {
			const response = await fetch(
				`http://localhost:8080/loans?email=${email}&bookId=${bookId}`,
				{
					method: "POST",
					credentials: "include",
				}
			);
			if (!response.ok) {
				throw new Error("Network response was not ok");
			}
		} catch (error) {
			console.error(error);
		}

		handleChangeSelectedBook();
		setIsLoanCreated(true);
	};

	return (
		<Container>
			{isBookSelected && (
				<div
					style={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
					}}>
					<Card
						style={{
							width: "30%",
							marginBottom: "3rem",
						}}>
						<Card.Body>
							<Card.Title style={{ marginBottom: "1rem" }}>
								Your selection:
							</Card.Title>
							<Card.Text style={{ marginBottom: "1rem" }}>
								<Book className="me-2" /> {selectedBook.title}
								<br />
								<Person className="me-2" /> {selectedBook.author}
								<br />
								<UpcScan className="me-2" /> {selectedBook.isbn}
								<br />
								<Buildings className="me-2" /> {selectedBook.publisher}
								<br />
							</Card.Text>
							<div
								style={{
									display: "flex",
									justifyContent: "center",
									alignItems: "center",
								}}>
								<Button
									variant="success"
									style={{ marginRight: "1rem" }}
									onClick={() => {
										handleCreateLoan(selectedBook.bookId);
									}}>
									Create loan
								</Button>
								<Button
									variant="danger"
									onClick={() => {
										handleChangeSelectedBook();
									}}>
									Change selection
								</Button>
							</div>
						</Card.Body>
					</Card>
				</div>
			)}
			{isLoanCreated && (
				<div
					style={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
					}}>
					<Alert
						variant="success"
						style={{
							width: "30%",
							marginBottom: "3rem",
						}}>
						Loan created!
					</Alert>
				</div>
			)}
			<Table bordered={false}>
				<thead>
					<tr>
						<th className="border-bottom-0">Title</th>
						<th className="border-bottom-0">Author</th>
						<th className="border-bottom-0">ISBN</th>
						<th className="border-bottom-0">Publisher</th>
					</tr>
					<tr>
						<th>
							<Form.Control
								type="text"
								placeholder={"Search by title"}
								value={title}
								onChange={(e) => {
									setTitle(e.target.value);
									setSearching(true);
									setQueryUrl(`/books?title=` + e.target.value);

									if (e.target.value === "") {
										setQueryUrl(`/books?page=0&size=${recordsPerPage}`);
										setSearching(false);
									}
								}}
							/>
						</th>
						<th>
							<Form.Control
								type="text"
								value={author}
								placeholder="Search by author"
								onChange={(e) => {
									setAuthor(e.target.value);
									setSearching(true);
									setQueryUrl(`/books?author=` + e.target.value);

									if (e.target.value === "") {
										setQueryUrl(`/books?page=0&size=${recordsPerPage}`);
										setSearching(false);
									}
								}}
							/>
						</th>
						<th>
							<Form.Control
								type="text"
								value={isbn}
								placeholder="Search by ISBN"
								onChange={(e) => {
									setIsbn(e.target.value);
									setSearching(true);
									setQueryUrl(`/books?isbn=` + e.target.value);

									if (e.target.value === "") {
										setQueryUrl(`/books?page=0&size=${recordsPerPage}`);
										setSearching(false);
									}
								}}
							/>
						</th>
						<th>
							<Form.Control
								type="text"
								value={publisher}
								placeholder="Search by publisher"
								onChange={(e) => {
									setPublisher(e.target.value);
									setSearching(true);
									setQueryUrl(`/books?publisher=` + e.target.value);

									if (e.target.value === "") {
										setQueryUrl(`/books?page=0&size=${recordsPerPage}`);
										setSearching(false);
									}
								}}
							/>
						</th>
					</tr>
				</thead>
				<tbody>
					{pageBooks.map((book, index) => (
						<tr key={index}>
							<td>{book.title}</td>
							<td>{book.author}</td>
							<td>{book.isbn}</td>
							<td>{book.publisher}</td>
							{!isBookSelected && (
								<td>
									<Button
										variant="primary"
										size="sm"
										onClick={() => handleSelectBook(book)}>
										Select book
									</Button>
								</td>
							)}
						</tr>
					))}
				</tbody>
			</Table>
			{!searching && (
				<div className="d-grid gap-4 d-flex justify-content-center">
					<div>
						<Button
							variant="primary"
							onClick={() => handlePageChange(currentPage - 1)}
							disabled={!hasPreviousPage()}>
							-
						</Button>
					</div>
					<div className="d-flex align-items-center">
						<p className="m-0"> Page {currentPage + 1}</p>
					</div>
					<div>
						<Button
							variant="primary"
							onClick={() => handlePageChange(currentPage + 1)}
							disabled={!hasNextPage()}>
							+
						</Button>
					</div>
					<div className="d-flex align-items-center">
						<Form.Select
							defaultValue={recordsPerPage}
							onChange={(e) => handleRecordsPerPageChange(e.target.value)}>
							<option value="5">5 per page</option>
							<option value="10">10 per page</option>
							<option value="20">20 per page</option>
						</Form.Select>
					</div>
				</div>
			)}
			{searching && (
				<div className="d-grid gap-4 d-flex justify-content-center">
					<div>
						<Button
							variant="primary"
							onClick={() => {
								setQueryUrl(`/books?page=0&size=${recordsPerPage}`);
								setSearching(false);
								setTitle("");
								setAuthor("");
								setIsbn("");
								setPublisher("");
							}}>
							Go back to the book catalog
						</Button>
					</div>
				</div>
			)}
		</Container>
	);
};

export default CreateLoanPage;
