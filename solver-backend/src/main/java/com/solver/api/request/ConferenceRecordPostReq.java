package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceRecordPostReq")
public class ConferenceRecordPostReq {
	@ApiModelProperty(name="base64data", example="base64data")
	private String videoFile;
}
