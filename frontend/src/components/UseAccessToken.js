import { useState } from "react";

const useAccessToken = () => {
	const [accessToken, setAccessToken] = useState(
		localStorage.getItem("access_token") || ""
	);

	const saveAccessToken = (accessToken) => {
		localStorage.setItem("access_token", accessToken);
		setAccessToken(accessToken);
	};

	return {
		accessToken,
		setAccessToken: saveAccessToken,
	};
};

export default useAccessToken;
