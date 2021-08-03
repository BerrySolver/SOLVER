package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SolverResponse")
public class SolverListRes extends BaseResponse {
	private List<SolverRes> list;

	public static SolverListRes of(Integer statusCode, String message, List<SolverRes> solverList) {
		SolverListRes res = new SolverListRes();
		res.setList(solverList);
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
