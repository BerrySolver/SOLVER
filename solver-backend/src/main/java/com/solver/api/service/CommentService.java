package com.solver.api.service;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;

public interface CommentService {

	void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId);

	boolean deleteComment(String accessToken, String commentId);

	boolean updateComment(String accessToken, String commentId, CommentUpdatePatchReq commentUpdatePatchReq);

}
