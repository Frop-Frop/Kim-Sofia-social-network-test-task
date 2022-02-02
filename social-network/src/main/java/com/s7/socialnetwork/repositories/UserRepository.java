package com.s7.socialnetwork.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.s7.socialnetwork.domain.RegularUser;

@Repository
public interface UserRepository extends JpaRepository<RegularUser, Long> {

	Optional<RegularUser> findByUsername(String username);

	@Query(value = "select u.* from users u where lower(u.first_name) like lower(concat('%', :searchTerm, '%'))"
			+ " or lower(u.last_name) like lower(concat('%', :searchTerm, '%'))"
			+ " or lower(concat(u.first_name, ' ', u.last_name)) like lower(concat('%', :searchTerm, '%'))"
			+ " or lower(concat(u.last_name, ' ', u.first_name)) like lower(concat('%', :searchTerm, '%'))", nativeQuery = true)
	List<RegularUser> search(@Param("searchTerm") String searchTerm);

	@Query(value = "SELECT u.* FROM users u JOIN friends f on u.id = f.user_id WHERE f.user_id=:id", nativeQuery = true)
	List<RegularUser> findFriends(@Param("id") Long friendId);

	@Query(value = "SELECT u.* FROM users u JOIN friends f on u.id = f.friend_id WHERE f.friend_id=:id", nativeQuery = true)
	List<RegularUser> findFriendOf(@Param("id") Long friendId);

	@Query(value = "select count(*) from friends", nativeQuery = true)
	Long countFriends();

}
