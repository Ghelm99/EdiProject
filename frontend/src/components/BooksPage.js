import { useEffect, useState } from "react";
import { Button, Container, Form, Table } from "react-bootstrap";

const BooksPage = () => {
	const [pageBooks, setPageBooks] = useState([]);
	const [allBooks, setAllBooks] = useState([]);
	const [queryUrl, setQueryUrl] = useState(
		"https://backend.ediprojectcsrf.online/books?page=0&size=10"
	);
	const [currentPage, setCurrentPage] = useState(0);
	const [recordsPerPage, setRecordsPerPage] = useState(10);
	const [searching, setSearching] = useState(false);
	const [title, setTitle] = useState("");
	const [author, setAuthor] = useState("");
	const [isbn, setIsbn] = useState("");
	const [publisher, setPublisher] = useState("");

	useEffect(() => {
		fetch("https://backend.ediprojectcsrf.online/books", {
			method: "GET",
			credentials: "include",
		})
			.then((response) => response.json())
			.then((data) => setAllBooks(data))
			.catch((error) => console.error(error));
	}, []);

	useEffect(() => {
		fetch(queryUrl, {
			method: "GET",
			credentials: "include",
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
		setQueryUrl(
			`https://backend.ediprojectcsrf.online/books?page=${pageNumber}&size=${recordsPerPage}`
		);
	};

	const handleRecordsPerPageChange = (recordsPerPage) => {
		setCurrentPage(0);
		setRecordsPerPage(recordsPerPage);
		setQueryUrl(
			`https://backend.ediprojectcsrf.online/books?page=${0}&size=${recordsPerPage}`
		);
	};

	const hasPreviousPage = () => {
		return currentPage > 0;
	};

	const hasNextPage = () => {
		return (currentPage + 1) * recordsPerPage < allBooks.length;
	};

	return (
		<Container>
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
									setQueryUrl(
										`https://backend.ediprojectcsrf.online/books?title=` +
											e.target.value
									);

									if (e.target.value === "") {
										setQueryUrl(
											`https://backend.ediprojectcsrf.online/books?page=0&size=${recordsPerPage}`
										);
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
									setQueryUrl(
										`https://backend.ediprojectcsrf.online/books?author=` +
											e.target.value
									);

									if (e.target.value === "") {
										setQueryUrl(
											`https://backend.ediprojectcsrf.online/books?page=0&size=${recordsPerPage}`
										);
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
									setQueryUrl(
										`https://backend.ediprojectcsrf.online/books?isbn=` +
											e.target.value
									);

									if (e.target.value === "") {
										setQueryUrl(
											`https://backend.ediprojectcsrf.online/books?page=0&size=${recordsPerPage}`
										);
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
									setQueryUrl(
										`https://backend.ediprojectcsrf.online/books?publisher=` +
											e.target.value
									);

									if (e.target.value === "") {
										setQueryUrl(
											`https://backend.ediprojectcsrf.online/books?page=0&size=${recordsPerPage}`
										);
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
								setQueryUrl(
									`https://backend.ediprojectcsrf.online/books?page=0&size=${recordsPerPage}`
								);
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

export default BooksPage;
