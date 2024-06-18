package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();

  QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);

  Quiz getQuiz(Long id);

  Question getQuestion(Long id, Long questionID);

  QuizResponseDto deleteQuiz(Long id);

  QuizResponseDto patchQuizName(Long id, String newName);

  QuestionResponseDto getQuizRandomQuestion(Long id);

  QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto);

  QuestionResponseDto deleteQuestion(Long id, Long questionID);
}
