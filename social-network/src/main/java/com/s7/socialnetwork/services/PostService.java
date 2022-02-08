package com.s7.socialnetwork.services;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.s7.socialnetwork.domain.Post;
import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.mappers.PostMapper;
import com.s7.socialnetwork.models.PostDTO;
import com.s7.socialnetwork.models.PostListDTO;
import com.s7.socialnetwork.repositories.PostRepository;
import com.s7.socialnetwork.repositories.UserRepository;

@Service
public class PostService {

	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public PostService(PostMapper postMapper, PostRepository postRepository, UserRepository userRepository) {
		super();
		this.postMapper = postMapper;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	public PostDTO getPostById(Long id) {
		return postRepository.findById(id).map(postMapper::postToPostDTO).orElseThrow(ResourceNotFoundException::new);
	}

	public PostListDTO getAllUserPosts(Long userId) {
		return new PostListDTO(postRepository.findAllUserPosts(userId).stream().map(postMapper::postToPostDTO)
				.collect(Collectors.toList()));
	}

	public PostListDTO getAllPosts() {
		return new PostListDTO(
				postRepository.findAll().stream().map(postMapper::postToPostDTO).collect(Collectors.toList()));
	}

	public PostDTO createNewPost(PostDTO postDTO, String username) {
		Optional<RegularUser> optionalUser = userRepository.findByUsername(username);
		RegularUser user = new RegularUser();
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			throw new ResourceNotFoundException();
		}
		Post post = postMapper.postDTOtoPost(postDTO, user);
		Post savedPost = postRepository.save(post);
		return postMapper.postToPostDTO(savedPost);
	}

	public PostDTO savePostByDTO(Long id, PostDTO postDTO, String username) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		Post post = optionalPost.get();
		Optional<RegularUser> optionalUser = userRepository.findByUsername(username);
		RegularUser user = new RegularUser();
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			throw new ResourceNotFoundException();
		}
		post = postMapper.postDTOtoPost(postDTO, user);
		post.setLastUpdate(new Date());
		return postMapper.postToPostDTO(postRepository.save(post));
	}

	public PostDTO patchPost(Long id, PostDTO postDTO) {
		Post post = postRepository.getById(id);
		if (post == null) {
			throw new ResourceNotFoundException();
		}
		if (post.getPostContent() == null) {
			post.setPostContent(postDTO.getPostContent());
		}
		post.setLastUpdate(new Date());
		return postMapper.postToPostDTO(postRepository.save(post));
	}

	public void deleteById(Long id) {
		Optional<Post> post = postRepository.findById(id);
		if (post.isPresent()) {
			postRepository.delete(post.get());
		}
	}

}
