import { useState } from "react";

const usePassword = () => {
	const [password, setPassword] = useState(
		localStorage.getItem("password") || ""
	);

	const savePassword = (password) => {
		localStorage.setItem("password", password);
		setPassword(password);
	};

	return {
		password,
		setPassword: savePassword,
	};
};

export default usePassword;
