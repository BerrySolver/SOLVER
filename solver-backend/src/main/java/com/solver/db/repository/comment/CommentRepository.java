package com.solver.db.repository.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, String>{

	List<Comment> findByAnswerIdOrderByRegDtAsc(String answerId);

}
