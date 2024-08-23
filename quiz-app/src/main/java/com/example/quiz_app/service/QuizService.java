package com.example.quiz_app.service;

import com.example.quiz_app.dao.QuestionRepo;
import com.example.quiz_app.dao.QuizRepo;

import com.example.quiz_app.model.Question;
import com.example.quiz_app.model.QuestionWrapper;
import com.example.quiz_app.model.Quiz;
import com.example.quiz_app.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return new ResponseEntity<>("Successfully created quiz", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getAllQuestions(Integer id)
    {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateCorrectQues(Integer id, List<Response> responses) {
        //Quiz quiz=quizRepo.findById(id).get();
        Optional<Quiz> quiz=quizRepo.findById(id);
        List<Question> questions=quiz.get().getQuestions();
        System.out.println(questions);
        int i=0;
        int correct=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                correct++;
            }
            i++;
        }
        return new ResponseEntity<>(correct,HttpStatus.OK);
    }
}
