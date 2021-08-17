package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PaySolverListRes")
public class PaySolverListRes extends BaseResponse{
	private List<PaySolverRes> list;
	
	public static PaySolverListRes of(int i, String string, List<PaySolverRes> paySolverRes) {
		PaySolverListRes res = new PaySolverListRes();
		res.setList(paySolverRes);
		res.setStatusCode(i);
		res.setMessage(string);
		
		return res;
	}
}
