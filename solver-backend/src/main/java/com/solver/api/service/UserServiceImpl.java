package com.solver.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.OAuthToken;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Evaluation;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.group.GroupMember;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.TokenId;
import com.solver.db.entity.user.User;
import com.solver.db.entity.user.UserCalendar;
import com.solver.db.repository.answer.EvaluationRepository;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.FavoriteFieldRepository;
import com.solver.db.repository.group.GroupInfoRepository;
import com.solver.db.repository.group.GroupMemberRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.TokenRepository;
import com.solver.db.repository.user.UserCalendarRepository;
import com.solver.db.repository.user.UserRepository;


/*userService랑 profileService 구분 짓는게 나을 듯*/
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserCalendarRepository userCalendarRepository;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	PointLogRepository pointLogRepository;
	
	@Autowired
	FavoriteFieldRepository favoriteFieldRepository;
	
	@Autowired
	EvaluationRepository evaluationRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	GroupMemberRepository groupMemberRepository;
	
	@Autowired
	GroupInfoRepository groupInfoRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Override
	public Optional<User> checkNickname(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		return user;
	}


	@Override
	public Optional<User> getUserByKakaoId(Long kakaoId) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		return user;
	}


	@Override
	public User insertUser(Long kakaoId) {
		User user = new User();
		
		user.setKakaoId(kakaoId);
		
		String userId = "";
		
		//새로운 user테이블의 id생성
		while(true) {
			userId = RandomIdUtil.makeRandomId(13);
			
			if(userRepository.findById(userId).orElse(null) == null)
				break;
		}
		
		user.setId(userId);
		
		return userRepository.save(user);
	}

	@Override
	public Token insertToken(OAuthToken oauthToken, Long kakaoId) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		TokenId tokenId = new TokenId();
		tokenId.setTokenId(user.get().getId());
		
		//토큰 정보 저장
		Token token = new Token();
		token.setAccessToken(oauthToken.getAccess_token());
		token.setRefreshToken(oauthToken.getRefresh_token());
		token.setUser(user.get());
		token.setTokenId(tokenId);
		
		//DB에 저장
		return tokenRepository.save(token);
	}


	@Override
	public void deleteToken(String accessToken) {
		long id = kakaoUtil.getKakaoUserIdByToken(accessToken);
		
		Optional<User> user = userRepository.findByKakaoId(id);
		
		//userId로 token테이블에서 데이터 삭제
		tokenRepository.deleteByUserId(user.get().getId());
	}


	@Override
	public void singUp(UserRegistPostReq userRegistPostReq, String accessToken) {
		String token = accessToken.split(" ")[1];
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		//DB에 저장된 더미 데이터 가져옴
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		Code code = new Code();
		
		//유저 타입 입력
		code.setCode(userRegistPostReq.getType());
		
		if(user == null) {
			return;
		}
		
		//유저 정보 입력
		user.setNickname(userRegistPostReq.getNickname());
		user.setCode(code);
		
		UserCalendar userCalendar = new UserCalendar();
		String userCalendarId = "";
		
		//새로운 user테이블의 id생성
		while(true) {
			userCalendarId = RandomIdUtil.makeRandomId(13);
			
			if(userCalendarRepository.findById(userCalendarId).orElse(null) == null)
				break;
		}
		
		//유저 시간표 정보
		userCalendar.setId(userCalendarId);
		userCalendar.setPossibleTime(userRegistPostReq.getPossibleTime());
		userCalendar.setUser(user);
		
		//DB에 저장
		userRepository.save(user);
		userCalendarRepository.save(userCalendar);
	}
	
	@Override
	public void deleteUser(String accessToken) {
		//accessToken부분
		String token = accessToken.split(" ")[1];
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		userRepository.deleteByKakaoId(kakaoId);
	}


	/* 마이페이지 정보를 불러 올 때 그룹이나 관심분야는 그냥 이름만 불러오나 - o
	 * 관심분야는 sub category 기준인가 - o
	 * */
	@Override
	public void getProfileInfo(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		String userId = user.get().getId();
		
		List<PointLog> pointList = pointLogRepository.findByUserId(userId);
		List<Evaluation> evaluationList = evaluationRepository.findByAnswerUserId(userId);
		List<GroupMember> groupMemberList = groupMemberRepository.findByUserId(userId);
		List<FavoriteField> favoriteFieldList = favoriteFieldRepository.findByUserId(userId);
		
		/* 포인트 계산 - entity 변경할 예정이어서 수정 필요 */
		int point = 0;
		int remainingPoint = 0;
		
		for (PointLog pointLog : pointList) {
			if(pointLog.getCode().getCode().equals("081")) {
				point += pointLog.getUsePoint();
			}
			else if(pointLog.getCode().getCode().equals("082")) {
				remainingPoint -= pointLog.getUsePoint();
			}
		}
		
		remainingPoint += point;
		
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
		List<String> fieldNameList = new ArrayList<>();
		
		for (FavoriteField favoriteField : favoriteFieldList) {
			fieldNameList.add(favoriteField.getCategory().getSubCategoryName());
		}
		/* 관심 분야 이름 리스트 생성 끝 */
		
	}

	//프로필 이미지 저장 부분 추가 필요
	@Override
	public void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken) {
		String token = accessToken.split(" ")[1];
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		//여기서 기존 이미지 삭제 필요
		user.setNickname(profileUpdatePatchReq.getNickname());
		user.setProfileUrl(profileUpdatePatchReq.getProfileUrl());
		user.setIntroduction(profileUpdatePatchReq.getIntroduction());
		user.setLinkText(profileUpdatePatchReq.getLinkText());
		
		userRepository.save(user);
		
		List<String> categoryList = profileUpdatePatchReq.getCategoryList();
		favoriteFieldRepository.deleteByUserId(user.getId());
		
		for (String subCategoryCode : categoryList) {
			FavoriteField favoriteField = new FavoriteField();
			String id = "";
			
			while(true) {
				id = RandomIdUtil.makeRandomId(13);
				
				if(favoriteFieldRepository.findById(id).orElse(null) == null)
					break;
						
			}
			
			Category category = categoryRepository.findBySubCategoryCode(subCategoryCode);
			favoriteField.setId(id);
			favoriteField.setUser(user);
			favoriteField.setCategory(category);
			
			favoriteFieldRepository.save(favoriteField);
		}
		
	}
}
