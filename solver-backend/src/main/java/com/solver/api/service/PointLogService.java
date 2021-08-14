package com.solver.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.SolverGetListReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.api.response.PointLogRes;
import com.solver.api.response.SolverRes;
import com.solver.common.model.OAuthToken;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.User;

public interface PointLogService {
	List<PointLogRes> getPointLogList(String nickName);
}
