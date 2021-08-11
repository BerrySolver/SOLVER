package com.solver.api.response;

import java.util.Date;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//질문 상세조회 시 반환하는 형태
@Getter
@Setter
@ApiModel("QuestionResponse")
public class QuestionRes extends BaseResponse{
	@ApiModelProperty(name="유저 닉네임")
	private String nickname;
	
	@ApiModelProperty(name="질문 상태")
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
	
	@ApiModelProperty(name="댓글수")
	private int answerCount;
	
	@ApiModelProperty(name="좋아요수")
	private int likeCount;
	
	@ApiModelProperty(name="북마크수")
	private int bookmarkCount;
	
	@ApiModelProperty(name="현재 사용자의 좋아요 여부")
	private boolean isLiked;
	
	@ApiModelProperty(name="현재 사용자의 북마크 여부")
	private boolean isBookmarked;
	
	public static QuestionRes of(Integer statusCode, String message, Question question, boolean isLiked, boolean isBookmarked) {
		QuestionRes res = new QuestionRes();
		
		// 외래키 필드들은 객체를 넣어야 한다.
		User user = new User();
		Code type = new Code();
		Code mainCategory = new Code();
		Category subCategory = new Category();
		
		user = question.getUser();
		type = question.getCode();
		mainCategory = question.getMainCategory();
		subCategory = question.getSubCategory();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setNickname(user.getNickname());
		res.setType(type.getCodeName());
		res.setTitle(question.getTitle());
		res.setContent(question.getContent());
		res.setMainCategory(mainCategory.getCodeName());
		res.setSubCategory(subCategory.getSubCategoryName());
		res.setDifficulty(question.getDifficulty());
		res.setRegDt(question.getRegDt());
		res.setExpirationTime(question.getExpirationTime());
		res.setConferenceOpened(question.isConferenceOpened());
		res.setReadCount(question.getReadCount());
		res.setAnswerCount(question.getAnswer().size());
		res.setLikeCount(question.getFavoriteQuestion().size());
		res.setBookmarkCount(question.getBookmarkQuestion().size());
		res.setLiked(isLiked);
		res.setBookmarked(isBookmarked);
		
		
		return res;
	}
}
