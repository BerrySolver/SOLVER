package com.solver.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.PaySolverReq;
import com.solver.api.response.PointLogRes;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.PaidSolver;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.user.PaidSolverRepository;
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
	
	@Autowired
	PaidSolverRepository paidSolverRepository;

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
		
		Collections.sort(list, new Comparator<PointLogRes>() {
			@Override
			public int compare(PointLogRes o1, PointLogRes o2) {
				return o2.getRedDt().compareTo(o1.getRedDt());
			}
		});
		
		return list;
	}

	@Override
	public void payPoint(PaySolverReq paySolverReq) throws ParseException {
		
		Optional<User> user = userRepository.findByNickname(paySolverReq.getUserNick());
		System.out.println(user.get().getId());

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date to1 = transFormat.parse(paySolverReq.getStartRegDt());
		Date to2 = transFormat.parse(paySolverReq.getEndRegDt());
		
		long diffSec = (to2.getTime() - to1.getTime()) / 1000;
        long diffDays = diffSec / (24*60*60);
        
		String len = Long.toString((diffDays + 1)+2);
		
		// paid solver 추가하기		
		PaidSolver paidSolver = new PaidSolver();
		paidSolver.setId(RandomIdUtil.makeRandomId(13));
		paidSolver.setUser(user.get());
		paidSolver.setStartTime(to1);
		paidSolver.setEndTime(to2);
		
		paidSolverRepository.save(paidSolver);		
		
		// point log 추가하기
		PointLog pointLog = new PointLog();
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		PointCode pointCode = pointCodeRepository.findByPointCode("10"+len);
		pointLog.setPointCode(pointCode);
		pointLog.setUser(user.get());	
		
		pointLogRepository.save(pointLog);	

	}

}
