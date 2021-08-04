package com.solver.db.repository.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.user.QUser;
import com.solver.db.entity.user.User;

@Repository
public class UserRepositorySupport {
	
	@Autowired
	private JPAQueryFactory queryFactory;
	QUser user = QUser.user;

	public List<User> findAll(Code mainCategory, Category subCategory, String query, String mode) {
		return queryFactory.
				selectFrom(user)
				.fetch();
	}

}
