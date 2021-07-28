package com.solver.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroupArticleFile extends BaseEntity{
	private String fileName;
	private String fileUrl;
	private String fileType;
	private int fileSize;
	
	@ManyToOne
	@JoinColumn(name="articleId")
	private GroupArticle groupArticle;
	
	@OneToMany(mappedBy="groupArticle", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupComment> groupComment;
}
