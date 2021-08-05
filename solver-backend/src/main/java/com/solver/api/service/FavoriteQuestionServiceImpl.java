package com.solver.api.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.question.FavoriteQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class FavoriteQuestionServiceImpl implements FavoriteQuestionService{

	@Autowired
	FavoriteQuestionRepository favoriteQuestionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	KakaoUtil kakaoUtil;
	
	// 좋아요
	@Override
	public FavoriteQuestion createFavoriteQuestion(String accessToken, Question question) {
		String token = accessToken.split(" ")[1];

		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우
		if (user == null) {
			return null;
		}

		String id = "";

		while (true) {
			id = RandomIdUtil.makeRandomId(13);

			if (favoriteQuestionRepository.findById(id).orElse(null) == null)
				break;
		}

		// 이미 좋아요 한 질문인 경우
		if (favoriteQuestionRepository.findByUserIdAndQuestionId(user.getId(), question.getId()).orElse(null) != null) {
			return null;
		}

		FavoriteQuestion favoriteQuestion = new FavoriteQuestion();
		favoriteQuestion.setQuestion(question);
		favoriteQuestion.setUser(user);
		favoriteQuestion.setId(id);

		return favoriteQuestionRepository.save(favoriteQuestion);
	}
	
	// 좋아요 취소
	@Override
	public void deleteFavoriteQuestion(String token, Question question) {
		Optional<User> user = userRepository.findByKakaoId(kakaoUtil.getKakaoUserIdByToken(token));
		
		Optional<FavoriteQuestion> favoriteQuestion = favoriteQuestionRepository.findByUserIdAndQuestionId(user.get().getId(), question.getId());
		
		try {
			favoriteQuestionRepository.deleteById(favoriteQuestion.get().getId());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
		
		return;
	}
	
	
}
