package com.s7.socialnetwork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.s7.socialnetwork.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "SELECT p.* FROM posts p WHERE p.user_id = :id", nativeQuery = true)
	List<Post> findAllUserPosts(@Param("id") Long userId);

	@Query(value = "SELECT p.* FROM posts p LIMIT :limit", nativeQuery = true)
	List<Post> findLimitedAmountOfPosts(@Param("limit") Integer limit);

	@Query(value = "SELECT p.* FROM posts p WHERE p.user_id = :id LIMIT :limit", nativeQuery = true)
	List<Post> findLimitedAmountOfUserPosts(@Param("id") Long id, @Param("limit") Integer limit);

}
