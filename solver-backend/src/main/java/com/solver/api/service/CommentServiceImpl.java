package com.solver.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.comment.CommentRepository;
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

	@Override
	public void createComment(String accessToken, CommentCreatePostReq commentCreatePostReq, String answerId) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
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
		
		Comment comment = new Comment();
		comment.setContent(commentCreatePostReq.getContent());
		comment.setAnswer(answer);
		comment.setUser(user);
		comment.setRegDt(new Date(System.currentTimeMillis()));
		comment.setId(id);
		
		commentRepository.save(comment);
	}

	@Override
	public boolean deleteComment(String accessToken, String commentId) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		//잘못된 요청 예외처리
		if(comment == null) {
			return false;
		}
		
		//다른 유저의 댓글을 삭제하는 경우
		if(!comment.getUser().getId().equals(user.getId())) {
			return false;
		}
		
		commentRepository.deleteById(commentId);
		
		return true;
	}

	@Override
	public boolean updateComment(String accessToken, String commentId, CommentUpdatePatchReq commentUpdatePatchReq) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		//잘못된 요청 예외처리
		if(comment == null) {
			return false;
		}
		
		//다른 유저의 답변을 수정하는 경우
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
