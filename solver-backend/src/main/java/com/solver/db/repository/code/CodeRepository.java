package com.solver.db.repository.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.CommonCode;

@Repository
public interface CodeRepository extends JpaRepository<Code, String>{

	Code findByCode(String type);

	List<Code> findByCommonCode(CommonCode commonCode);

}
