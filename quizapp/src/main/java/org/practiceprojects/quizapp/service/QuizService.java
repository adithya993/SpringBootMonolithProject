package org.practiceprojects.quizapp.service;

import org.practiceprojects.quizapp.dao.QuestionDao;
import org.practiceprojects.quizapp.dao.QuizDao;
import org.practiceprojects.quizapp.model.Question;
import org.practiceprojects.quizapp.model.QuestionWrapper;
import org.practiceprojects.quizapp.model.Quiz;
import org.practiceprojects.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try{
            List<Question> questions = questionDao.findRandomQuestionByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB){
            questionsForUser.add(new QuestionWrapper(q.getId(), q.getQuestion(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questionsFromDB = quiz.getQuestions();
        int score = 0;
        for(Response response : responses){
            for(Question q : questionsFromDB)
                if(Objects.equals(response.getId(), q.getId()) && response.getResponse().equals(q.getCorrectanswer()))
                    score++;
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
