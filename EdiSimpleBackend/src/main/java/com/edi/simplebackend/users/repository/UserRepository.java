package com.edi.simplebackend.users.repository;

import com.edi.simplebackend.users.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

	Optional<UserData> findById(Long id);

	Optional<UserData> findByUsername(String username);

}
