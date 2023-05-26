import { useState } from "react";

const useEmail = () => {
	const [email, setEmail] = useState(localStorage.getItem("email") || "");

	const saveEmail = (email) => {
		localStorage.setItem("email", email);
		setEmail(email);
	};

	return {
		email,
		setEmail: saveEmail,
	};
};

export default useEmail;
