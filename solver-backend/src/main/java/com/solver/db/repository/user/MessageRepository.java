package com.solver.db.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.user.Message;

public interface MessageRepository extends JpaRepository<Message, String>{

	List<Message> findBySendUserId(String id);

	List<Message> findByReceiveUserId(String id);
}
