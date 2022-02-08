package com.s7.socialnetwork.mappers;

import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.Post;
import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.models.PostDTO;
import com.s7.socialnetwork.repositories.PostRepository;

@Component
public class PostMapper {

	private final PostRepository postRepository;

	public PostMapper(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public PostDTO postToPostDTO(Post post) {
		if (post == null) {
			return new PostDTO();
		}
		return new PostDTO(post.getId(), post.getPostContent(), post.getUser().getId(), post.getLastUpdate());
	}

	public Post postDTOtoPost(PostDTO postDTO, RegularUser user) {
		if (postDTO == null) {
			return new Post();
		}
		return new Post(postDTO.getId(), postDTO.getPostContent(), user, postDTO.getLastUpdate());
	}

}
