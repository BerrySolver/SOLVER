package com.solver.api.response;

import java.util.Date;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.Code;
import com.solver.db.entity.Question;
import com.solver.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionResponse")
public class QuestionRes extends BaseResponse{
	@ApiModelProperty(name="유저 ID")
	private String userId;
	
	@ApiModelProperty(name="질문 유형")
	private String type;
	
	@ApiModelProperty(name="질문 제목")
	private String title;
	
	@ApiModelProperty(name="질문 내용")
	private String content;
	
	@ApiModelProperty(name="대분류")
	private String mainCategory;
	
	@ApiModelProperty(name="소분류")
	private String subCategory;
	
	@ApiModelProperty(name="난이도")
	private int difficulty;
	
	@ApiModelProperty(name="생성 시간")
	private Date regDt;
	
	@ApiModelProperty(name="만료 시간")
	private Date expirationTime;
	
	@ApiModelProperty(name="화상 열린 여부")
	private boolean conferenceOpened;
	
	@ApiModelProperty(name="조회수")
	private int readCount;
	
	public static QuestionRes of(Integer statusCode, String message, Question question) {
		QuestionRes res = new QuestionRes();
		User user = new User();
		Code code = new Code();
		
		user = question.getUser();
		code = question.getCode();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setUserId(user.getId());
//		res.setType(code.getCode());
		res.setTitle(question.getTitle());
		res.setContent(question.getContent());
//		res.setMainCategory(question.getMainCategroy());
//		res.setSubCategory(question.getSubCategory());
		res.setDifficulty(question.getDifficulty());
		res.setRegDt(question.getRegDt());
		res.setExpirationTime(question.getExpirationTime());
		res.setConferenceOpened(question.isConferenceOpened());
		res.setReadCount(question.getReadCount());
		
		return res;
	}
}
