package com.solver.db.repository.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solver.db.entity.code.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

}
