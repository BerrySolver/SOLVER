package com.solver.api.service;

import com.solver.api.request.QuestionReq;
import com.solver.db.entity.Question;

public interface QuestionService {
	Question createQuestion(QuestionReq questionReq);
}	
