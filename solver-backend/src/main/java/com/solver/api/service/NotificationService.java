package com.solver.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.response.NotificationListRes;
import com.solver.api.response.NotificationRes;

public interface NotificationService {

	List<NotificationRes> getAllNotificationList(String accessToken, HttpServletResponse response);

}
