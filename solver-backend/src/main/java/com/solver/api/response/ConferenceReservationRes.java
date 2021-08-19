package com.solver.api.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.conference.ConferenceReservation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceReservationRes")
public class ConferenceReservationRes extends BaseResponse{
	private List<ConferenceReservationForm> reservationList;
	
	public static ConferenceReservationRes of(List<ConferenceReservation> list, Integer statusCode, String message) {
		ConferenceReservationRes res = new ConferenceReservationRes();
		
		List<ConferenceReservationForm> tempList = new ArrayList<>();
		
		for (ConferenceReservation conferenceReservation : list) {
			ConferenceReservationForm conferenceReservationForm = new ConferenceReservationForm();
			conferenceReservationForm.setId(conferenceReservation.getId());
			conferenceReservationForm.setQuestionId(conferenceReservation.getQuestion().getId());
			conferenceReservationForm.setStartDt(conferenceReservation.getStartDt());
			conferenceReservationForm.setUserId(conferenceReservation.getUser().getId());
			conferenceReservationForm.setQuestionTitle(conferenceReservation.getQuestion().getTitle());
			
			tempList.add(conferenceReservationForm);
		}
		res.setReservationList(tempList);
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}

@Getter
@Setter
class ConferenceReservationForm{
	private String userId;
	
	private String questionId;
	
	private String questionTitle;
	
	private Date startDt;
	
	private String Id;
}
