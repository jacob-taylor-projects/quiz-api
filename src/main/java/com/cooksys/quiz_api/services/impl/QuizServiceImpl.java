package com.cooksys.quiz_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;

  @Override
  public Quiz getQuiz(Long id) {
    Optional<Quiz> quiz=quizRepository.findById(id);
    if(quiz.isEmpty()){
      throw new NotFoundException("Could not find quiz with id: "+id);
    }
    return quiz.get();
  }

  @Override
  public Question getQuestion(Long id, Long questionID) {
    Optional<Question> question=questionRepository.findByIdAndQuizId(questionID,id);
    if (question.isEmpty()){
      throw new NotFoundException("Could not find question with id: "+questionID);
    }
    return question.get();
  }


  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
    Quiz quiz = quizMapper.dtoToEntity(quizRequestDto);

    for (Question question : quiz.getQuestions()) {
      question.setQuiz(quiz);

      for (Answer answer : question.getAnswers()) {
        answer.setQuestion(question);
      }
    }
    Quiz result = quizRepository.saveAndFlush(quiz);
    return quizMapper.entityToDto(result);
  }

  @Override
  public QuizResponseDto deleteQuiz(Long id) {
    Quiz quizToDelete=getQuiz(id);
    quizRepository.delete(quizToDelete);
    return quizMapper.entityToDto(quizToDelete);
  }

  @Override
  public QuizResponseDto patchQuizName(Long id, String newName) {
    Quiz quizToUpdate=getQuiz(id);
    quizToUpdate.setName(newName);
    return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));
  }

  @Override
  public QuestionResponseDto getQuizRandomQuestion(Long id) {
    Quiz quiz=getQuiz(id);
    List<Question> questions=quiz.getQuestions();
    Random random=new Random();
    Question randomQuestion=questions.get(random.nextInt(quiz.getQuestions().size()));
    return questionMapper.entityToDto(randomQuestion);
  }

  @Override
  public QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto) {
    Quiz quiz = getQuiz(id);

    for (Question question : quiz.getQuestions()) {
      question.setQuiz(quiz);

      for (Answer answer : question.getAnswers()) {
        answer.setQuestion(question);
      }
    }
    quiz.getQuestions().add(questionMapper.dtoToEntity(questionRequestDto));
    return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
  }

  @Override
  public QuestionResponseDto deleteQuestion(Long id, Long questionID) {
    Question question=getQuestion(id, questionID);
    questionRepository.delete(question);
    return questionMapper.entityToDto(question);
  }


}
