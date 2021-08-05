package com.solver.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.answer.FavoriteAnswer;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.answer.FavoriteAnswerRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class FavoriteAnswerServiceImpl implements FavoriteAnswerService {

	@Autowired
	FavoriteAnswerRepository favoriteAnswerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	KakaoUtil kakaoUtil;

	@Override
	public int createFavoriteAnswer(String accessToken, String answerId) {
		String token = accessToken.split(" ")[1];

		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우?
		if (user == null) {
			return 0;
		}

		Answer answer = answerRepository.findById(answerId).orElse(null);

		// 없는 답변에 대한 것인 경우
		if (answer == null) {
			return 1;
		}

		String id = "";

		while (true) {
			id = RandomIdUtil.makeRandomId(13);

			if (favoriteAnswerRepository.findById(id).orElse(null) == null)
				break;
		}

		// 이미 좋아요 한 답변인 경우
		if (favoriteAnswerRepository.findByUserIdAndAnswerId(user.getId(), answer.getId()).orElse(null) != null) {
			return 2;
		}

		FavoriteAnswer favoriteAnswer = new FavoriteAnswer();
		favoriteAnswer.setAnswer(answer);
		favoriteAnswer.setUser(user);
		favoriteAnswer.setId(id);

		favoriteAnswerRepository.save(favoriteAnswer);

		return 3;
	}

	@Override
	public int deleteFavoriteAnswer(String accessToken, String answerId) {
		String token = accessToken.split(" ")[1];

		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우?
		if (user == null) {
			return 0;
		}

		Answer answer = answerRepository.findById(answerId).orElse(null);

		// 없는 답변에 대한 것인 경우
		if (answer == null) {
			return 1;
		}

		FavoriteAnswer favoriteAnswer = favoriteAnswerRepository.findByUserIdAndAnswerId(user.getId(), answer.getId()).orElse(null);

		// 이미 좋아요 한 답변인 경우
		if (favoriteAnswer == null) {
			return 2;
		}

		favoriteAnswerRepository.deleteById(favoriteAnswer.getId());

		return 3;
	}
}
