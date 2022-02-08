package com.s7.socialnetwork.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {

	private Long id;
	private String postContent;
	private Long userId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date lastUpdate;

}
