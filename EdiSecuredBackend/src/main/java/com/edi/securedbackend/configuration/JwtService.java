package com.edi.securedbackend.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;

	/* Method used to extract all the claims from a token. */
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/* Method used to extract a specific claim from the token's claims using a resolver function. */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/* Method used to extract the email from the token's claims. */
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/* Method used to extract the expiration date from the token's claims. */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/* Method used to generate a refresh token for the given userDetails. */
	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}

	/* Method used to build a new token for the given userDetails, extraClaims and expiration. */
	private String buildToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails,
			long expiration
	) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/* Method used to getting the signing key for the JWT tokens. */
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/* Method used to generate an authentication token for the given userDetails. */
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	/* Method used to generate an authentication token for the given userDetails and extraClaims. */
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
	) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	/* Method used to check if a token is expired. */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/* Method used to check if a token is valid for the given userDetails. */
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractEmail(token);
		return (username.equals(userDetails.getUsername())) && (! isTokenExpired(token));
	}

}
