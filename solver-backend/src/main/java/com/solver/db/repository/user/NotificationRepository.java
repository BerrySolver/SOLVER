package com.solver.db.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.code.Code;
import com.solver.db.entity.user.Notification;

public interface NotificationRepository extends JpaRepository<Notification, String>{

	List<Notification> findByUserId(String id);

	List<Notification> findByUserIdAndCode(String userId, Code code);
}
