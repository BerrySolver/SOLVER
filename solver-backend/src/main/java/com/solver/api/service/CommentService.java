package com.solver.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;
import com.solver.db.entity.comment.Comment;

public interface CommentService {

	void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId, HttpServletResponse response);

	boolean deleteComment(String accessToken, String commentId, HttpServletResponse response);

	boolean updateComment(String accessToken, String commentId, CommentUpdatePatchReq commentUpdatePatchReq, HttpServletResponse response);

	List<Comment> getCommentList(String answerId);

}
