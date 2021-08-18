package com.solver.api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.ProfilePossibleTimePatchReq;
import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;
import com.solver.api.response.ProfileTabRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.answer.Evaluation;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.group.GroupMember;
import com.solver.db.entity.question.BookmarkQuestion;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.FavoriteUser;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.entity.user.UserCalendar;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.answer.EvaluationRepository;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.FavoriteFieldRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.conference.ConferenceLogRepository;
import com.solver.db.repository.group.GroupMemberRepository;
import com.solver.db.repository.question.BookmarkQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.FavoriteUserRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserCalendarRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	ConferenceLogRepository conferenceLogRepository;
	
	@Autowired
	BookmarkQuestionRepository bookmarkQuestionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	UserCalendarRepository userCalendarRepository;
	
	@Autowired
	PointLogRepository pointLogRepository;
	
	@Autowired
	FavoriteFieldRepository favoriteFieldRepository;
	
	@Autowired
	EvaluationRepository evaluationRepository;
	
	@Autowired
	PointCodeRepository pointCodeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	GroupMemberRepository groupMemberRepository;
	
	@Autowired
	FavoriteUserRepository favoriteUserRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	/* 마이페이지 정보를 불러 올 때 그룹이나 관심분야는 그냥 이름만 불러오나 - o
	 * 관심분야는 sub category 기준인가 - o
	 * */
	@Override
	public ProfileRes getProfileInfo(String token, String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		List<PointLog> pointList = user.get().getPointLog();
		List<Evaluation> evaluationList = user.get().getEvaluateAnswer();
		List<GroupMember> groupMemberList = user.get().getGroupMember();
		List<FavoriteField> favoriteFieldList = user.get().getFavoriteField();
		
		/* 포인트 계산 - entity 변경할 예정이어서 수정 필요 */
		int point = 0;
		int remainingPoint = 0;
		
		int plus = 0, minus = 0;
		
		for (PointLog pointLog : pointList) {
			PointCode pointCode = pointCodeRepository.findByPointCode(pointLog.getPointCode().getPointCode());
			
			if(pointCode.getPointCode().charAt(0) == '0') {
				plus += pointCode.getValue();
			}else {
				minus += pointCode.getValue();
			}
			
//			if(pointCode.getPointCode().equals("100") || pointCode.getPointCode().equals("101") || pointCode.getPointCode().equals("102")) {
//				remainingPoint -= pointCode.getValue();
//				
//			}
//			else {
//				point += pointCode.getValue();
//			}
		}
		
		point = plus;
		remainingPoint = plus - minus;
		
		/* 포인트 계산 끝*/
		
		/* 평점 계산 */
		
		float evaluationScore = 0;
		
		for (Evaluation evaluation : evaluationList) {
			evaluationScore += evaluation.getScore();
		}
		
		evaluationScore /= evaluationList.size();
		
		/* 평점 계산 끝 */
		
		/* 가입한 그룹 이름 리스트 생성 */
		List<String> groupNameList = new ArrayList<>();
		
		for (GroupMember groupMember : groupMemberList) {
			groupNameList.add(groupMember.getGroup().getGroupName());
		}
		/* 가입한 그룹 이름 리스트 생성 끝 */
		
		/* 관심 분야 이름 리스트 생성 */
		List<String> favoriteFieldNameList = new ArrayList<>();
		List<String> favoriteFieldCodeList = new ArrayList<>();
		
		for (FavoriteField favoriteField : favoriteFieldList) {
			favoriteFieldNameList.add(favoriteField.getCategory().getSubCategoryName());
			favoriteFieldCodeList.add(favoriteField.getCategory().getSubCategoryCode());
		}
		/* 관심 분야 이름 리스트 생성 끝 */
		
		/* response 데이터 저장 */
		ProfileRes profileRes = new ProfileRes();
		
		if (Double.isNaN(evaluationScore)) {
			evaluationScore = 0;
		}
		
		/* 내가 이 유저 팔로우 여부 */
		boolean isFollowing = false;
		if (!token.equals("null")) {
			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
	
			Long kakaoId = tokenResponse.getKakaoId();
			
			Optional<User> me = userRepository.findByKakaoId(kakaoId);
			
			for (FavoriteUser favoriteUser : user.get().getFavoriteUser()) {
				if (favoriteUser.getFollowingUser().getId().equals(me.get().getId())) {
					isFollowing =  true;
					break;
				}
			}
		}
		
		profileRes.setEvaluationScore(evaluationScore);
		profileRes.setFavoriteFieldCodeList(favoriteFieldCodeList);
		profileRes.setFavoriteFieldNameList(favoriteFieldNameList);
		profileRes.setGroupNameList(groupNameList);
		profileRes.setPoint(point);
		profileRes.setRemainingPoint(remainingPoint);
		profileRes.setNickname(user.get().getNickname());
		profileRes.setIntroduction(user.get().getIntroduction());
		profileRes.setLinkText(user.get().getLinkText());
		profileRes.setProfileUrl(user.get().getProfileUrl());
		
		profileRes.setWeekdayTime(user.get().getUserCalendar().getWeekdayTime());
		profileRes.setWeekendTime(user.get().getUserCalendar().getWeekendTime());
		
		profileRes.setFollowers(user.get().getFavoriteUser().size());
		profileRes.setFollowings(user.get().getFavoriteFollowingUser().size());
		profileRes.setFollowing(isFollowing);
		/* response 데이터 저장 끝 */
		
		return profileRes;
	}

	//프로필 이미지 저장 부분 추가 필요
	@Override
	public void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		
		User user = userRepository.findByKakaoId(kakaoId).get();
		//여기서 기존 이미지 삭제 필요
		user.setNickname(profileUpdatePatchReq.getNickname());
		user.setProfileUrl(profileUpdatePatchReq.getProfileUrl());
		user.setIntroduction(profileUpdatePatchReq.getIntroduction());
		user.setLinkText(profileUpdatePatchReq.getLinkText());
		
		userRepository.save(user);
		
		List<String> categoryList = profileUpdatePatchReq.getCategoryCodeList();
		favoriteFieldRepository.deleteByUserId(user.getId());
		
		if(categoryList == null) {
            return;
        }
		
		for (String subCategoryCode : categoryList) {
			FavoriteField favoriteField = new FavoriteField();
			String id = "";
			
			if(categoryList == null) {
	            return;
	        }
			
			while(true) {
				id = RandomIdUtil.makeRandomId(13);
				
				if(favoriteFieldRepository.findById(id).orElse(null) == null)
					break;
						
			}
			
			Category category = categoryRepository.findBySubCategoryCode(subCategoryCode);
			
			if(category == null)
				continue;
			
			favoriteField.setId(id);
			favoriteField.setUser(user);
			favoriteField.setCategory(category);
			
			favoriteFieldRepository.save(favoriteField);
		}
	}

	@Override
	public void updateProfilePossibleTime(ProfilePossibleTimePatchReq profilePossibleTimePatchReq, String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		UserCalendar userCalendar = userCalendarRepository.findByUserId(user.getId());
		
		userCalendar.setWeekdayTime(profilePossibleTimePatchReq.getWeekdayTime());
		userCalendar.setWeekendTime(profilePossibleTimePatchReq.getWeekendTime());
		
		userCalendarRepository.save(userCalendar);
		
	}

	/* 유저 탭 정보 조회
	 * tabNum
	 * 0: SOLVE 기록
	 * 1: 답변 목록
	 * 2: 질문 목록 
	 * */
	@Override
	public ProfileTabRes getProfileTabInfo(String nickname, int tabNum) {
		ProfileTabRes profileTabRes = new ProfileTabRes();
		User user = userRepository.findByNickname(nickname).orElse(null);
		
		String userId = user.getId();
		
		if(tabNum == 0) {
			List<Answer> textAnswerList = answerRepository.findTextAnswerByUserId(userId);
			List<Answer> videoAnswerList = answerRepository.findVideoAnswerByUserId(userId);
			
			int textAnswerCount = textAnswerList.size();
			int videoAnswerCount = videoAnswerList.size();
			int videoAnswerTime = 0;
			
			List<ConferenceLog> conferenceEnterList = conferenceLogRepository.findByUserIdOrderByConferenceId(userId);
			
			for (ConferenceLog conferenceLog : conferenceEnterList) {
				//입장
				if(conferenceLog.getCode().getCode().equals("030")) {
					videoAnswerTime -= conferenceLog.getRegDt().getTime();
				}
				//퇴장
				else if(conferenceLog.getCode().getCode().equals("031")) {
					videoAnswerTime += conferenceLog.getRegDt().getTime();
				}
			}
			
			videoAnswerTime /= (1000*60);
			
			List<Answer> myAnswerList = new ArrayList<Answer>();
			myAnswerList.addAll(textAnswerList);
			myAnswerList.addAll(videoAnswerList);
			
			profileTabRes = ProfileTabRes.makeAnswerStatistics(videoAnswerTime, videoAnswerCount, textAnswerCount, myAnswerList);
		}
		//답변을 달은 질문 목록
		else if(tabNum == 1) {
			List<Answer> answerList = answerRepository.findByUserId(userId);
			
			Set<String> questionIdSet = new HashSet<>();
			
			for (Answer answer : answerList) {
				questionIdSet.add(answer.getQuestion().getId());
			}
			
			List<String> questionIdList = new ArrayList<>(questionIdSet);
			
			if(questionIdList.size() != 0) {
				List<Question> answerQuestionList = questionRepository.findAllById(questionIdList);
			
				profileTabRes = ProfileTabRes.makeAnswerQuestionList(answerQuestionList);
			}
		}
		//내가 작성한 질문 목록
		else if(tabNum == 2) {
			List<Question> questionList = questionRepository.findByUserId(userId);
			
			profileTabRes = ProfileTabRes.makeMyQuestionList(questionList);
		}
		//내가 북마크한 질문 목록
		else if(tabNum == 3) {
			List<BookmarkQuestion> bookmarkList = bookmarkQuestionRepository.findAllByUserId(userId);
			
			Set<String> questionIdSet = new HashSet<>();
			
			for (BookmarkQuestion bookmark : bookmarkList) {
				questionIdSet.add(bookmark.getQuestion().getId());
			}
			
			List<String> questionIdList = new ArrayList<>(questionIdSet);
			
			List<Question> questionList = questionRepository.findAllById(questionIdList);
			
			profileTabRes = ProfileTabRes.makeBookmarkQuestionList(questionList);
		}
		
		return profileTabRes;
	}

	@Override
	public int followUser(String accessToken, String nickname, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}


		User myUserInfo = userRepository.findByKakaoId(kakaoId).orElse(null);

		//없는 유저인 경우
		if (myUserInfo == null) {
			return 0;
		}

		User followedUserInfo = userRepository.findByNickname(nickname).orElse(null);
		
		//없는 유저인 경우
		if (followedUserInfo == null) {
			return 0;
		}

		String id = "";

		while (true) {
			id = RandomIdUtil.makeRandomId(13);

			if (favoriteUserRepository.findById(id).orElse(null) == null)
				break;
		}

		//이미 팔로우 한 유저인 경우
		if (favoriteUserRepository.findByUserIdAndFollowingUserId(followedUserInfo.getId(), myUserInfo.getId()).orElse(null) != null) {
			return 2;
		}

		
		FavoriteUser favoriteUser = new FavoriteUser();
		favoriteUser.setFollowingUser(myUserInfo);
		favoriteUser.setUser(followedUserInfo);
		favoriteUser.setId(id);

		favoriteUserRepository.save(favoriteUser);

		return 3;
	}
	
	@Override
	public int unFollowUser(String accessToken, String nickname, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}


		User myUserInfo = userRepository.findByKakaoId(kakaoId).orElse(null);

		//없는 유저인 경우
		if (myUserInfo == null) {
			return 0;
		}

		User followedUserInfo = userRepository.findByNickname(nickname).orElse(null);
		
		//없는 유저인 경우
		if (followedUserInfo == null) {
			return 0;
		}
		
		FavoriteUser favoriteUser = favoriteUserRepository.findByUserIdAndFollowingUserId(followedUserInfo.getId(), myUserInfo.getId()).orElse(null);

		//팔로우 하지 않은 유저인 경우
		if (favoriteUser == null) {
			return 2;
		}

		favoriteUserRepository.delete(favoriteUser);

		return 3;
	}
}
