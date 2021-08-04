package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.Notification;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NotificationListRes")
public class NotificationListRes extends BaseResponse{
	List<Notification> notificationList;
}
