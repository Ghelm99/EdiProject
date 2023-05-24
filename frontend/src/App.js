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
import useAccessToken from "./components/UseAccessToken";
import useRefreshToken from "./components/UseRefreshToken";
import useUserEmail from "./components/UseUserEmail";
import "./css/main.min.css";

const App = () => {
	const { accessToken, setAccessToken } = useAccessToken();
	const { refreshToken, setRefreshToken } = useRefreshToken();
	const navigate = useNavigate();
	const { userEmail, setUserEmail } = useUserEmail();

	function handleNavigate(path) {
		navigate(path);
	}

	useEffect(() => {
		console.log("access_token: " + localStorage.getItem("access_token"));
		console.log("refresh_token: " + localStorage.getItem("refresh_token"));
		console.log("user_email: " + localStorage.getItem("user_email"));
	}, [accessToken, refreshToken, userEmail]);

	return (
		<div className="App">
			<Topbar
				accessToken={accessToken}
				refreshToken={refreshToken}
				userEmail={userEmail}
				setAccessToken={setAccessToken}
				setRefreshToken={setRefreshToken}
				setUserEmail={setUserEmail}
				handleNavigate={handleNavigate}
			/>
			<div style={{ marginTop: "4rem", marginBottom: "4rem" }}>
				<Routes>
					<Route
						path="/"
						element={
							<HomePage
								handleNavigate={handleNavigate}
								accessToken={accessToken}
								refreshToken={refreshToken}
							/>
						}></Route>
					<Route path="/catalogue" element={<BooksPage />}></Route>
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
								setAccessToken={setAccessToken}
								setRefreshToken={setRefreshToken}
								setUserEmail={setUserEmail}
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
