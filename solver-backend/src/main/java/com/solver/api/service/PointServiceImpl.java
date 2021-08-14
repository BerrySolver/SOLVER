package com.solver.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.response.PointLogRes;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class PointServiceImpl implements PointLogService {
	
	@Autowired
	PointCodeRepository pointCodeRepository;
	
	@Autowired
	PointLogRepository pointLogRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<PointLogRes> getPointLogList(String nickName) {
		
		List<PointLogRes> list = new ArrayList<PointLogRes>();
		
		Optional<User> user = userRepository.findByNickname(nickName);
		
		if (user.orElse(null) == null) {
			return null;
		}
		
		List<PointLog> pointLog = pointLogRepository.findByUserId(user.get().getId());
		
		for (PointLog point : pointLog) {
			PointLogRes log = new PointLogRes();
			PointCode pointCode = pointCodeRepository.findByPointCode(point.getPointCode().getPointCode());
			log.setRedDt(point.getRegDt());
			log.setPointCode(pointCode.getPointCode());
			log.setPointName(pointCode.getPointName());
			log.setValue(pointCode.getValue());
			log.setCode(pointCode.getCode().getCode());
			list.add(log);
		}
		
		return list;
	}

}
