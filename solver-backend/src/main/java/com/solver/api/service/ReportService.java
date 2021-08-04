package com.solver.api.service;

import com.solver.api.request.ReportPostReq;
import com.solver.db.entity.answer.ReportAnswer;
import com.solver.db.entity.comment.ReportComment;
import com.solver.db.entity.question.ReportQuestion;

public interface ReportService {

	ReportQuestion createReportQuestion(ReportPostReq reportPostReq, String token);

	ReportAnswer createReportAnswer(ReportPostReq reportPostReq, String token);

	ReportComment createReportComment(ReportPostReq reportPostReq, String token);

}
