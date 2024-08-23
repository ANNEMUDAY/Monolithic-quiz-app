package com.example.quiz_app.controller;

import com.example.quiz_app.model.Question;
import com.example.quiz_app.model.QuestionWrapper;
import com.example.quiz_app.model.Response;
import com.example.quiz_app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getAllQuestions(@PathVariable Integer id){
        return quizService.getAllQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> SubmitQuestions(@PathVariable Integer id,@RequestBody List<Response> responses){
        return quizService.calculateCorrectQues(id,responses);
    }
}
