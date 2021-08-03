package com.solver.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Evaluation;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.group.GroupMember;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.entity.user.UserCalendar;
import com.solver.db.repository.answer.EvaluationRepository;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.FavoriteFieldRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.group.GroupMemberRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserCalendarRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService{
	@Autowired
	UserRepository userRepository;
	
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
	KakaoUtil kakaoUtil;

	/* 마이페이지 정보를 불러 올 때 그룹이나 관심분야는 그냥 이름만 불러오나 - o
	 * 관심분야는 sub category 기준인가 - o
	 * */
	@Override
	public ProfileRes getProfileInfo(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		List<PointLog> pointList = user.get().getPointLog();
		List<Evaluation> evaluationList = user.get().getEvaluatedAnswer();
		List<GroupMember> groupMemberList = user.get().getGroupMember();
		List<FavoriteField> favoriteFieldList = user.get().getFavoriteField();
		
		/* 포인트 계산 - entity 변경할 예정이어서 수정 필요 */
		int point = 0;
		int remainingPoint = 0;
		
		for (PointLog pointLog : pointList) {
			PointCode pointCode = pointCodeRepository.findByPointCode(pointLog.getPointCode().getPointCode());
			
			if(pointCode.getPointCode().equals("100") || pointCode.getPointCode().equals("101") || pointCode.getPointCode().equals("102")) {
				remainingPoint -= pointCode.getValue();
				
			}
			else {
				point += pointCode.getValue();
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
		List<String> favoriteFieldNameList = new ArrayList<>();
		
		for (FavoriteField favoriteField : favoriteFieldList) {
			favoriteFieldNameList.add(favoriteField.getCategory().getSubCategoryName());
		}
		/* 관심 분야 이름 리스트 생성 끝 */
		
		/* response 데이터 저장 */
		ProfileRes profileRes = new ProfileRes();
		
		profileRes.setEvaluationScore(evaluationScore);
		profileRes.setFavoriteFieldNameList(favoriteFieldNameList);
		profileRes.setGroupNameList(groupNameList);
		profileRes.setPoint(point);
		profileRes.setRemainingPoint(remainingPoint);
		profileRes.setNickname(user.get().getNickname());
		profileRes.setIntroduction(user.get().getIntroduction());
		profileRes.setLinkText(user.get().getLinkText());
		profileRes.setProfileUrl(user.get().getProfileUrl());
		
		profileRes.setPossibleTime(user.get().getUserCalendar().getPossibleTime());
		/* response 데이터 저장 끝 */
		
		return profileRes;
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
		
		UserCalendar userCalendar = userCalendarRepository.findByUserId(user.getId());
		
		userCalendar.setPossibleTime(profileUpdatePatchReq.getPossibleTime());
		userCalendar.setUser(user);
		
	}
}
