package com.solver.db.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.question.BookmarkQuestion;

public interface BookmarkQuestionRepository extends JpaRepository<BookmarkQuestion, String>{

}
