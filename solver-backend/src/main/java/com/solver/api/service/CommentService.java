package com.solver.api.service;

import java.util.List;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;
import com.solver.db.entity.comment.Comment;

public interface CommentService {

	void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId);

	boolean deleteComment(String accessToken, String commentId);

	boolean updateComment(String accessToken, String commentId, CommentUpdatePatchReq commentUpdatePatchReq);

	List<Comment> getCommentList(String answerId);

}
