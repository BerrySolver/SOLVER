package com.solver.api.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.comment.CommentRepository;
import com.solver.db.repository.user.NotificationRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CodeRepository codeRepository;

	@Override
	public void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Answer answer = answerRepository.findById(answerId).orElse(null);
		
		if(answer == null) {
			return;
		}
		
		String id = "";
		
		while(true) {
			id = RandomIdUtil.makeRandomId(13);
			
			if(commentRepository.findById(id).orElse(null) == null)
				break;
		}
		
		// ?????? DB ??????
		Comment comment = new Comment();
		comment.setContent(commentCreatePostReq.getContent());
		comment.setAnswer(answer);
		comment.setUser(user);
		comment.setRegDt(new Date(System.currentTimeMillis()));
		comment.setId(id);
		
		commentRepository.save(comment);
		
		// ?????? ?????? : ????????? ????????? ?????? ??????????????? ????????? ?????????.
		Notification notification = new Notification();
		notification.setId(RandomIdUtil.makeRandomId(13));
		notification.setQuestion(answer.getQuestion());
		notification.setRegDt(new Date(System.currentTimeMillis()));
		
		Code notiCode = codeRepository.findByCode("061");
		notification.setCode(notiCode);
		notification.setUser(comment.getAnswer().getUser());
		
		notificationRepository.save(notification);
	}

	@Override
	public boolean deleteComment(String accessToken, String commentId, HttpServletResponse response) {
		//??????????????? ??????
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		//????????? ?????? ????????????
		if(comment == null) {
			return false;
		}
		
		//?????? ????????? ????????? ???????????? ??????
		if(!comment.getUser().getId().equals(user.getId())) {
			return false;
		}
		
		commentRepository.deleteById(commentId);
		
		return true;
	}

	@Override
	public boolean updateComment(String accessToken, String commentId, CommentUpdatePatchReq commentUpdatePatchReq, HttpServletResponse response) {
		//??????????????? ??????
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		//????????? ?????? ????????????
		if(comment == null) {
			return false;
		}
		
		//?????? ????????? ????????? ???????????? ??????
		if(!comment.getUser().getId().equals(user.getId())) {
			return false;
		}
		
		comment.setContent(commentUpdatePatchReq.getContent());
		
		Comment resultComment = commentRepository.save(comment);
		
		if(resultComment == null)
			return false;
		
		return true;
	}

	@Override
	public List<Comment> getCommentList(String answerId) {
		
		List<Comment> commentList = commentRepository.findByAnswerIdOrderByRegDtAsc(answerId);
		
		
		return commentList;
	}

}
