package com.solver.api.service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.solver.api.request.ConferenceRecordPostReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.common.util.S3VideoUrlUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.conference.ConferenceLogRepository;
import com.solver.db.repository.conference.ConferenceParticipantRepository;
import com.solver.db.repository.conference.ConferenceRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	KakaoUtil kakaoUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConferenceRepository conferenceRepository;

	@Autowired
	ConferenceParticipantRepository conferenceParticipantRepository;

	@Autowired
	ConferenceLogRepository conferenceLogRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	private AmazonS3 s3Client;
	
	private String s3Url = "https://solver-bucket.s3.ap-northeast-2.amazonaws.com/";
	
    @PostConstruct          
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials("AKIARMPAI5JURFO5RVG5", "2VNZUcbCHPFewyyOxPLZndakCtAEO0ZdvvaRHYww");

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

	@Override
	public int updateConference(String accessToken, String conferenceId, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();

		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();

		if (tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (user == null) {
			return 0;
		}

		Conference conference = conferenceRepository.findById(conferenceId).orElse(null);

		if (conference == null) {
			return 1;
		}

		if (!user.getId().equals(conference.getQuestionUser().getId())) {
			return 2;
		}

		Code code = new Code();

		code.setCode("010");

		conference.setCode(code);

		conferenceRepository.save(conference);

		return 3;
	}

	@Override
	public int goOutConference(String accessToken, String conferenceId, HttpServletResponse response) {
		/* 회의 주관자인 경우의 예외처리가 필요할까? */

		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();

		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();

		if (tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (user == null) {
			return 0;
		}

		Conference conference = conferenceRepository.findById(conferenceId).orElse(null);

		if (conference == null) {
			return 1;
		}

		ConferenceLog conferenceLog = new ConferenceLog();

		String conferenceLogId = "";

		while (true) {
			conferenceLogId = RandomIdUtil.makeRandomId(13);

			if (conferenceLogRepository.findById(conferenceLogId).orElse(null) == null)
				break;
		}

		Code code = new Code();
		code.setCode("031");

		conferenceLog.setConference(conference);
		conferenceLog.setUser(user);
		conferenceLog.setRegDt(new Date(System.currentTimeMillis()));
		conferenceLog.setCode(code);
		conferenceLog.setId(conferenceLogId);

		conference.setCount(conference.getCount() - 1);

		conferenceParticipantRepository.deleteByUserId(user.getId());
		conferenceRepository.save(conference);
		conferenceLogRepository.save(conferenceLog);

		return 3;
	}

	@Override
	public int deleteConference(String accessToken, String conferenceId, HttpServletResponse response) {
		/* 회의 주관자인 경우의 예외처리가 필요할까? */
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();

		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();

		if (tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (user == null) {
			return 0;
		}

		Conference conference = conferenceRepository.findById(conferenceId).orElse(null);

		if (conference == null) {
			return 1;
		}

		if (conferenceRepository.findByIdAndQuestionUserId(conference.getId(), user.getId()).orElse(null) == null) {
			return 2;
		}

		/*
		 * 현재 화상회의 종료시에 conference 테이블에서 해당 데이터를 삭제하는데 conference_log와 FK 로 연결되어 있어
		 * conference 데이터를 삭제할 때 conference_log의 해당 화상회의 관련 데이터도 삭제되는 문제가 있음
		 */

//		conferenceRepository.deleteById(conferenceId);

		return 3;
	}

	@Override
	public int recordConference(String accessToken, String questionId, HttpServletResponse response,
			ConferenceRecordPostReq conferenceRecordPostReq) {
		/* 회의 주관자인 경우의 예외처리가 필요할까? */
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();

		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();

		if (tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (user == null) {
			return 0;
		}

		UUID uuid = UUID.randomUUID();

		String videoFile = conferenceRecordPostReq.getVideoFile();

		String saveFilename = uuid + ".mp4";

		Decoder decoder = Base64.getDecoder();
		byte[] decodedByte = decoder.decode(videoFile);
//		FileOutputStream fos = new FileOutputStream(uploadPath+File.separator+saveFilename);
//		fos.write(decodedByte);
//		fos.close();

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("mp4");

//        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		objectMetadata.setContentLength(decodedByte.length);
		ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(decodedByte);

		// (버킷명, 폴더 경로 및 파일 명, 파일데이터 ByteStream, 메타 데이터(이름, 길이 등))
		s3Client.putObject(new PutObjectRequest("solver-bucket", saveFilename, byteArrayIs, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead)); // public read 권한 주기
		
		String answerId = RandomIdUtil.makeRandomId(13);
		
		Answer answer = new Answer();
		
		Code code = new Code();
		code.setCode("051");
		
		Question question = new Question();
		question.setId(questionId);
		
		answer.setId(answerId);
		answer.setCode(code);
		answer.setUser(user);
		answer.setRegDt(new Date(System.currentTimeMillis()));
		answer.setQuestion(question);
		answer.setContent(S3VideoUrlUtil.makeS3UrlToHtml(saveFilename));
		
		answerRepository.save(answer);

		return 3;
	}

}
