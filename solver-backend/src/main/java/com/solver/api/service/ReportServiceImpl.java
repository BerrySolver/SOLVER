package com.solver.api.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.ReportPostReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.answer.ReportAnswer;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.comment.ReportComment;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.question.ReportQuestion;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.answer.ReportAnswerRepository;
import com.solver.db.repository.comment.CommentRepository;
import com.solver.db.repository.comment.ReportCommentRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.question.ReportQuestionRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportQuestionRepository reportQuestionRepository;
	
	@Autowired
	ReportAnswerRepository reportAnswerRepository;
	
	@Autowired
	ReportCommentRepository reportCommentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	@Override
	public ReportQuestion createReportQuestion(ReportPostReq reportPostReq, String token, HttpServletResponse response) {
		ReportQuestion reportQuestion = new ReportQuestion();
		
		String reportQuestionId = "";
				
		while(true) {
			reportQuestionId = RandomIdUtil.makeRandomId(13);
			
			if(reportQuestionRepository.findById(reportQuestionId).orElse(null) == null)
				break;
		}
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		// 외래키 참조값 생성
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		Optional<Question> question = questionRepository.findById(reportPostReq.getTargetId());
		
		if (user.orElse(null) == null || question.orElse(null) == null) {
			return null;
		}
		
		reportQuestion.setId(reportQuestionId);
		reportQuestion.setReporterUser(user.get());
		reportQuestion.setQuestion(question.get());
		reportQuestion.setReason(reportPostReq.getReason());
		
		return reportQuestionRepository.save(reportQuestion);
	}

	@Override
	public ReportAnswer createReportAnswer(ReportPostReq reportPostReq, String token, HttpServletResponse response) {
		ReportAnswer reportAnswer = new ReportAnswer();
		
		String reportAnswerId = "";
				
		while(true) {
			reportAnswerId = RandomIdUtil.makeRandomId(13);
			
			if(reportAnswerRepository.findById(reportAnswerId).orElse(null) == null)
				break;
		}
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		// 외래키 참조값 생성
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		Optional<Answer> answer = answerRepository.findById(reportPostReq.getTargetId());
		
		if (user.orElse(null) == null || answer.orElse(null) == null) {
			return null;
		}
		
		reportAnswer.setId(reportAnswerId);
		reportAnswer.setReporterUser(user.get());
		reportAnswer.setAnswer(answer.get());
		reportAnswer.setReason(reportPostReq.getReason());
		
		return reportAnswerRepository.save(reportAnswer);
	}

	@Override
	public ReportComment createReportComment(ReportPostReq reportPostReq, String token, HttpServletResponse response) {
		ReportComment reportComment = new ReportComment();
		
		String reportCommentId = "";
				
		while(true) {
			reportCommentId = RandomIdUtil.makeRandomId(13);
			
			if(reportCommentRepository.findById(reportCommentId).orElse(null) == null)
				break;
		}
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		// 외래키 참조값 생성
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		Optional<Comment> comment = commentRepository.findById(reportPostReq.getTargetId());
		
		if (user.orElse(null) == null || comment.orElse(null) == null) {
			return null;
		}
		
		reportComment.setId(reportCommentId);
		reportComment.setReporterUser(user.get());
		reportComment.setComment(comment.get());
		reportComment.setReason(reportPostReq.getReason());
		
		return reportCommentRepository.save(reportComment);
	}

}
