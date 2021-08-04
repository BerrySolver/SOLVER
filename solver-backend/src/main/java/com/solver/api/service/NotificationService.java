package com.solver.api.service;

import com.solver.api.response.NotificationListRes;

public interface NotificationService {

	NotificationListRes getAllNotificationList(String accessToken);

	NotificationListRes getVideoNotificationList(String accessToken);

}
