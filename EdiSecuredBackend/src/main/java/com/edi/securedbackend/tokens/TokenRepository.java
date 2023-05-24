package com.edi.securedbackend.tokens;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

	@Query(
			value = """
					select t from Token t inner join UserData u\s
					on t.userData.userId = u.userId\s
					where u.userId = :id and (t.expired = false or t.revoked = false)\s
					"""
	)
	List<Token> findAllValidTokenByUser(Long id);

	Optional<Token> findByToken(String token);

}
