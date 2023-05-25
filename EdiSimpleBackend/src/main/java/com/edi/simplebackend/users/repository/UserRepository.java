package com.edi.simplebackend.users.repository;

import com.edi.simplebackend.users.model.UserData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, Long> {

	Optional<UserData> findByEmail(String email);

	Optional<UserData> findById(Long id);

}
