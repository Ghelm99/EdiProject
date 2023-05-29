import React, { useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import LoansPage from "./components/ActiveLoansPage";
import BooksPage from "./components/BooksPage";
import ContactPage from "./components/ContactPage";
import CreateLoanPage from "./components/CreateLoanPage";
import Footer from "./components/Footer";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import SignupPage from "./components/SignupPage";
import Topbar from "./components/Topbar";
import useEmail from "./components/UseEmail";
import usePassword from "./components/UsePassword";
import "./css/main.min.css";

const App = () => {
	const navigate = useNavigate();
	const { email, setEmail } = useEmail();
	const { password, setPassword } = usePassword();

	function handleNavigate(path) {
		navigate(path);
	}

	useEffect(() => {
	}, [email, password]);

	return (
		<div className="App">
			<Topbar
				email={email}
				setEmail={setEmail}
				handleNavigate={handleNavigate}
			/>
			<div style={{ marginTop: "4rem", marginBottom: "4rem" }}>
				<Routes>
					<Route
						path="/"
						element={
							<HomePage handleNavigate={handleNavigate} email={email} />
						}></Route>
					<Route path="/catalog" element={<BooksPage />}></Route>
					<Route path="/contacts" element={<ContactPage />}></Route>
					<Route path="/loans" element={<LoansPage />}></Route>
					<Route
						path="/createLoan"
						element={
							<CreateLoanPage handleNavigate={handleNavigate} />
						}></Route>
					<Route
						path="/login"
						element={
							<LoginPage
								setEmail={setEmail}
								setPassword={setPassword}
								handleNavigate={handleNavigate}
							/>
						}></Route>
					<Route path="/signup" element={<SignupPage />}></Route>
				</Routes>
			</div>
			<Footer />
		</div>
	);
};

export default App;
