package com.s7.socialnetwork.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends AbstractEntity {

	private String postContent;
	@ManyToOne(cascade = CascadeType.MERGE)
	private RegularUser user;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdate;

	public Post(Long id, String postContent, RegularUser user, Date lastUpdate) {
		super(id);
		this.postContent = postContent;
		this.user = user;
		this.lastUpdate = lastUpdate;
	}

}
