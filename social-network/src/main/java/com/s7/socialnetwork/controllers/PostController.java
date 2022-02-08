package com.s7.socialnetwork.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s7.socialnetwork.models.PostDTO;
import com.s7.socialnetwork.models.PostListDTO;
import com.s7.socialnetwork.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Post controller", description = "Post related operations")
@RestController
@RequestMapping("social-network/posts/")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@Operation(summary = "Get list of all posts", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<PostListDTO> getAllPosts() {
		return new ResponseEntity<PostListDTO>(postService.getAllPosts(), HttpStatus.OK);
	}

	@Operation(summary = "Get list of all posts", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("user/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<PostListDTO> getAllUserPosts(@PathVariable Long id) {
		return new ResponseEntity<PostListDTO>(postService.getAllUserPosts(id), HttpStatus.OK);
	}

	@Operation(summary = "Get list of all posts", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
		return new ResponseEntity<PostDTO>(postService.getPostById(id), HttpStatus.OK);
	}

	@Operation(summary = "Update post by postDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id,
			Principal principal) {
		return new ResponseEntity<PostDTO>(postService.savePostByDTO(id, postDTO, principal.getName()), HttpStatus.OK);
	}

	@Operation(summary = "Patch post by postDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<PostDTO> patchPost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
		return new ResponseEntity<PostDTO>(postService.patchPost(id, postDTO), HttpStatus.OK);
	}

	@Operation(summary = "Create new post by postDTO")
	@PostMapping()
	public ResponseEntity<PostDTO> createNewPost(@RequestBody PostDTO postDTO, Principal principal) {
		return new ResponseEntity<PostDTO>(postService.createNewPost(postDTO, principal.getName()), HttpStatus.OK);
	}

	@Operation(summary = "Delete post by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
