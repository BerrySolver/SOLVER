package com.solver.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.SolverGetListReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.api.response.PaySolverRes;
import com.solver.api.response.SolverRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.OAuthToken;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Evaluation;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.PaidSolver;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.TokenId;
import com.solver.db.entity.user.User;
import com.solver.db.entity.user.UserCalendar;
import com.solver.db.repository.answer.EvaluationRepository;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.code.FavoriteFieldRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.group.GroupInfoRepository;
import com.solver.db.repository.group.GroupMemberRepository;
import com.solver.db.repository.user.FavoriteUserRepository;
import com.solver.db.repository.user.PaidSolverRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.TokenRepository;
import com.solver.db.repository.user.UserCalendarRepository;
import com.solver.db.repository.user.UserRepository;
import com.solver.db.repository.user.UserRepositorySupport;

/*userService랑 profileService 구분 짓는게 나을 듯*/
@Service
public class UserServiceImpl implements UserService {

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
	PointCodeRepository pointCodeRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	GroupMemberRepository groupMemberRepository;

	@Autowired
	GroupInfoRepository groupInfoRepository;

	@Autowired
	CodeRepository codeRepository;

	@Autowired
	FavoriteUserRepository favoriteUserRepository;

	@Autowired
	KakaoUtil kakaoUtil;

	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PaidSolverRepository paidSolverRepository;

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

		// 새로운 user테이블의 id생성
		while (true) {
			userId = RandomIdUtil.makeRandomId(13);

			if (userRepository.findById(userId).orElse(null) == null)
				break;
		}

		user.setId(userId);

		String userCalendarId = "";

		// 새로운 user테이블의 id생성
		while (true) {
			userCalendarId = RandomIdUtil.makeRandomId(13);

			if (userCalendarRepository.findById(userCalendarId).orElse(null) == null)
				break;
		}

		UserCalendar userCalendar = new UserCalendar();

		userCalendar.setId(userCalendarId);
		userCalendar.setUser(user);

//		userCalendarRepository.save(userCalendar);
		
		return userRepository.save(user);
	}

	@Override
	public Token insertToken(OAuthToken oauthToken, Long kakaoId) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);

		TokenId tokenId = new TokenId();
		tokenId.setTokenId(user.get().getId());

		// 토큰 정보 저장
		Token token = new Token();
		token.setAccessToken(oauthToken.getAccess_token());
		token.setRefreshToken(oauthToken.getRefresh_token());
		token.setUser(user.get());
		token.setTokenId(tokenId);

		// DB에 저장
		return tokenRepository.save(token);
	}

	@Override
	public void deleteToken(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		long id = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Optional<User> user = userRepository.findByKakaoId(id);

		// userId로 token테이블에서 데이터 삭제
		tokenRepository.deleteByUserId(user.get().getId());
	}

	@Override
	public void singUp(UserRegistPostReq userRegistPostReq, String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		// DB에 저장된 더미 데이터 가져옴
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		Code code = new Code();

		// 유저 타입 입력
		code.setCode("001");

		if (user == null) {
			return;
		}

		// 유저 정보 입력
		user.setNickname(userRegistPostReq.getNickname());
		user.setCode(code);

		UserCalendar userCalendar = userCalendarRepository.findByUserId(user.getId());

		if (userCalendar == null) {
			userCalendar = new UserCalendar();
			userCalendar.setUser(user);

			String userCalenderId = "";

			while (true) {
				userCalenderId = RandomIdUtil.makeRandomId(13);

				if (userRepository.findById(userCalenderId).orElse(null) == null)
					break;
			}

			userCalendar.setId(userCalenderId);
		}

		// 유저 시간표 정보
		userCalendar.setWeekdayTime(userRegistPostReq.getWeekdayTime());
		userCalendar.setWeekendTime(userRegistPostReq.getWeekendTime());

		favoriteFieldRepository.deleteByUserId(user.getId());

		for (String categoryCode : userRegistPostReq.getSelectedCode()) {
			FavoriteField favoriteField = new FavoriteField();

			String favoriteFieldId = "";

			// 새로운 user테이블의 id생성
			while (true) {
				favoriteFieldId = RandomIdUtil.makeRandomId(13);

				if (favoriteFieldRepository.findById(favoriteFieldId).orElse(null) == null)
					break;
			}

			Category category = new Category();

			category.setSubCategoryCode(categoryCode);

			favoriteField.setUser(user);
			favoriteField.setId(favoriteFieldId);
			favoriteField.setCategory(category);

			favoriteFieldRepository.save(favoriteField);
		}

		// DB에 저장
		userRepository.save(user);
		userCalendarRepository.save(userCalendar);
	}

	@Override
	public void deleteUser(String accessToken, HttpServletResponse response) {
		// accessToken부분
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		userRepository.deleteByKakaoId(kakaoId);
	}

	@Override
	public List<SolverRes> getUserList(SolverGetListReq solverGetListReq) {

		// 필터, 조회 값이 존재하는지 얻기
		String mode = solverGetListReq.getMode();
		String query = solverGetListReq.getQuery();
		Code mainCategory = codeRepository.findByCode(solverGetListReq.getMainCategory());
		Category subCategory = categoryRepository.findBySubCategoryCode(solverGetListReq.getSubCategory());

		// 기본 유저 데이터 불러오기
		List<User> userList = userRepositorySupport.findAll(mainCategory, subCategory, solverGetListReq.getQuery(),
				solverGetListReq.getMode());

		List<SolverRes> solverList = new ArrayList<SolverRes>();
		for (User user : userList) {
			String nickname = user.getNickname();

			if (query != null && !nickname.contains(query))
				continue;

			SolverRes solverRes = new SolverRes();
			String profileUrl = user.getProfileUrl();
			String introduction = user.getIntroduction();

			// 닉네임(UNIQUE)을 기준으로 기본 정보 부르기
			Optional<User> oneUser = userRepository.findByNickname(nickname);

			List<PointLog> pointList = oneUser.get().getPointLog();
			List<Evaluation> evaluationList = oneUser.get().getEvaluateAnswer();
			List<FavoriteField> favoriteFieldList = oneUser.get().getFavoriteField();

			/* 포인트 계산 - entity 변경할 예정이어서 수정 필요 */
			int point = 0;

			for (PointLog pointLog : pointList) {
				PointCode pointCode = pointCodeRepository.findByPointCode(pointLog.getPointCode().getPointCode());
				int type = pointCode.getPointCode().charAt(0) - '0'; // 코드의 시작이 0이면 얻는 포인트 - 1이면 잃는 포인트

				if (type == 0) {
					point += pointCode.getValue();
				}
			}
			/* 포인트 계산 끝 */

			// 최소 노출 기준 : 100베리
			if (point >= 100) {

				/* 평점 계산 */
				float evaluationScore = 0;

				for (Evaluation evaluation : evaluationList) {
					evaluationScore += evaluation.getScore();
				}

				evaluationScore /= evaluationList.size();
				/* 평점 계산 끝 */

				boolean main_falg = false;
				List<Category> checkList = new ArrayList<>();
				if (mainCategory == null) {
					for (FavoriteField favoriteField : favoriteFieldList) {
						checkList.add(favoriteField.getCategory());
					}	
					main_falg = true;				
				} else {
					for (FavoriteField favoriteField : favoriteFieldList) {
						if (mainCategory.getCode().equals(favoriteField.getCategory().getCode().getCode())) {
							checkList.add(favoriteField.getCategory());
							main_falg = true;
						}
					}
				}

				boolean sub_flag = false;
				if(main_falg && (subCategory != null)) {
					for (Category category : checkList) {
						if(category.getSubCategoryCode().equals(subCategory.getSubCategoryCode())) {
							sub_flag=true;
						}
					}
				}else if(main_falg && !sub_flag) {
					sub_flag=true;
				}

				if((mainCategory==null&&subCategory==null)||(main_falg&&subCategory==null)||(main_falg&&sub_flag)) {
					List<String> favoriteFieldNameList = new ArrayList<>();
					
					for (FavoriteField favoriteField : favoriteFieldList) {
						favoriteFieldNameList.add(favoriteField.getCategory().getSubCategoryName());
					}			
					
					solverRes.setProfileUrl(profileUrl);
					solverRes.setPoint(point);
					
					solverRes.setNickname(nickname);
					solverRes.setEvaluationScore(evaluationScore);
					solverRes.setIntroduction(introduction);
					solverRes.setFavoriteFieldNameList(favoriteFieldNameList);

					int follower = favoriteUserRepository.findByUserId(user.getId()).size();
					solverRes.setFollower(follower);

					solverList.add(solverRes);
				}
			}
		}

		/* 정렬 기준(Mode) 시작 */
		if (mode != null && mode.equals("pointDesc")) {
			Collections.sort(solverList, new Comparator<SolverRes>() {

				@Override
				public int compare(SolverRes o1, SolverRes o2) {
					return o2.getPoint() - o1.getPoint();
				}

			});
		} else if (mode != null && mode.equals("evaluationDesc")) {
			Collections.sort(solverList, new Comparator<SolverRes>() {

				@Override
				public int compare(SolverRes o1, SolverRes o2) {
					return (int) o2.getEvaluationScore() - (int) o1.getEvaluationScore();
				}

			});
		} else if (mode != null && mode.equals("followerDesc")) {
			Collections.sort(solverList, new Comparator<SolverRes>() {

				@Override
				public int compare(SolverRes o1, SolverRes o2) {
					return (int) o2.getFollower() - (int) o1.getFollower();
				}

			});
		}
		/* 정렬 기준(Mode) 끝 */

		return solverList;
	}

	@Override
	public String getNickname(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		return user.getNickname();
	}

	@Override
	public List<PaySolverRes> getPaySolver(String accessToken) {
		String token = accessToken.split(" ")[1];
		
		// 오늘 홍보를 하기로 한 사람들의 정보
		Date date = new Date();
		List<PaidSolver> paidSolvers = paidSolverRepository.findByStartTimeBeforeAndEndTimeAfter(date, date);
		List<PaySolverRes> list = new ArrayList<PaySolverRes>();
		
		if(paidSolvers.size() == 0)
			return list;
		
		// paidSolver 리스트 생성
		for (PaidSolver paidSolver : paidSolvers) {
			PaySolverRes res = new PaySolverRes();
			res.setNickname(paidSolver.getUser().getNickname());
			res.setProfileUrl(paidSolver.getUser().getProfileUrl());
			List<PointLog> pointLog = paidSolver.getUser().getPointLog();
			
			int point = 0;
			for (PointLog p : pointLog) {
				if(p.getPointCode().getPointCode().charAt(0) == '0') // 얻은 로그라면
					point += p.getPointCode().getValue();
			}

			res.setPoint(point);			
			list.add(res);
		}
		
		if(!token.equals("null")) {
			// 로그인 상태 - 관심분야에 있는 사람으로 추리기
			TokenResponse tokenResponse = new TokenResponse();			
			tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
			Long kakaoId = tokenResponse.getKakaoId();			
			User user = userRepository.findByKakaoId(kakaoId).orElse(null);
			List<FavoriteField> fields = user.getFavoriteField(); // 나의 관심분야 가져오기
			
			List<PaySolverRes> result = new ArrayList<PaySolverRes>();
			for (PaySolverRes paySolverRes : list) {
				User solverUser = userRepository.findByNickname(paySolverRes.getNickname()).orElse(null);

				boolean flag = false;

				for (FavoriteField field : solverUser.getFavoriteField()) { // 검색된 사람의 관심분야 확인하기
					for(FavoriteField basic : fields) {
						if(basic.getCategory().getSubCategoryCode().equals(field.getCategory().getSubCategoryCode()))
							flag = true;
						if(flag) break;
					}
					if(flag) break;	// 하나라도 존재하면 더이상 확인하지 않음				
				}
				
				if(flag)// 같은게 하나라도 있으면 추가
					result.add(paySolverRes);
			}
			
			list = result;
		}
		
		// 랜덤하게 배치
		Collections.shuffle(list);
		if(list.size() > 8) {
			// 대상자가 8명 이상일 경우 노출할 8명을 추출하기
			List<PaySolverRes> result = new ArrayList<PaySolverRes>(list.subList(0, 8));
			list = result;
		}		
		
		return list;
	}
	
	public void insertDefaultProfile(Long kakaoId, String profileUrl) {
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(user.getProfileUrl() == null) {
			user.setProfileUrl(profileUrl);
			userRepository.save(user);
		}
		
	}
}
