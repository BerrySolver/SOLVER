package com.solver.db.repository.group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.group.GroupMember;

public interface GroupMemberRepository  extends JpaRepository<GroupMember, String>{

	List<GroupMember> findByUserId(String userId);

}
