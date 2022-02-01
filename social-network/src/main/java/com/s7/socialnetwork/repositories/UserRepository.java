package com.s7.socialnetwork.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s7.socialnetwork.domain.RegularUser;

@Repository
public interface UserRepository extends JpaRepository<RegularUser, Long> {

	Optional<RegularUser> findByUsername(String username);

}
