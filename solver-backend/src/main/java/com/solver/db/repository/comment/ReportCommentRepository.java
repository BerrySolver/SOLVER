package com.solver.db.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.comment.ReportComment;

public interface ReportCommentRepository extends JpaRepository<ReportComment, String>{

}
