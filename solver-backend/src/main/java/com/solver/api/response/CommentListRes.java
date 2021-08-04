package com.solver.api.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.comment.Comment;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommentListRes")
public class CommentListRes extends BaseResponse{
	private List<CommentInfo> commentList;
	
	public static CommentListRes of(Integer statusCode, String message, List<Comment> commentList) {
		CommentListRes res = new CommentListRes();
		
		List<CommentInfo> tempCommentList = new ArrayList<CommentInfo>();
		
		for (Comment comment : commentList) {
			CommentInfo commentInfo = new CommentInfo();
			
			commentInfo.setContent(comment.getContent());
			commentInfo.setId(comment.getId());
			commentInfo.setRegDt(comment.getRegDt());
			
			tempCommentList.add(commentInfo);
		}
		
		res.setCommentList(tempCommentList);
		res.setMessage(message);
		res.setStatusCode(statusCode);
		
		return res;
	}
	
	public static CommentListRes of(Integer statusCode, String message) {
		CommentListRes res = new CommentListRes();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		
		return res;
	}
}

@Getter
@Setter
class CommentInfo{
	private String id;
	private String content;
	private Date regDt;
}