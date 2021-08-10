package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.response.NotificationListRes;

public interface NotificationService {

	NotificationListRes getAllNotificationList(String accessToken, HttpServletResponse response);

	NotificationListRes getVideoNotificationList(String accessToken, HttpServletResponse response);

}
