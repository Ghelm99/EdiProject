import { useState } from "react";

const useUserEmail = () => {
	const [userEmail, setUserEmail] = useState(
		localStorage.getItem("user_email") || ""
	);

	const saveUserEmail = (userEmail) => {
		localStorage.setItem("user_email", userEmail);
		setUserEmail(userEmail);
	};

	return {
		userEmail,
		setUserEmail: saveUserEmail,
	};
};

export default useUserEmail;
