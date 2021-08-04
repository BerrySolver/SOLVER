package com.solver.api.service;

import com.solver.api.request.CommentCreatePostReq;

public interface CommentService {

	void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId);

	boolean deleteAnswer(String accessToken, String commentId);

}
