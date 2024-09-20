package org.practiceprojects.quizapp.dao;

import org.practiceprojects.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * from monolith_questions q WHERE q.category=:category ORDER BY id LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numQ);
}
