import { useState } from "react";

const useRefreshToken = () => {
	const [refreshToken, setRefreshToken] = useState(
		localStorage.getItem("refresh_token") || ""
	);

	const saveRefreshToken = (refreshToken) => {
		localStorage.setItem("refresh_token", refreshToken);
		setRefreshToken(refreshToken);
	};

	return {
		refreshToken,
		setRefreshToken: saveRefreshToken,
	};
};

export default useRefreshToken;
