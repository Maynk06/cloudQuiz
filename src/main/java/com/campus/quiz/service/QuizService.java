package com.campus.quiz.service;

import com.campus.quiz.model.*;import com.campus.quiz.repo.*;import com.campus.quiz.web.*;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizzes; private final QuizAttemptRepository attempts; private final UserAccountRepository users;
    public QuizService(QuizRepository quizzes,QuizAttemptRepository attempts,UserAccountRepository users){this.quizzes=quizzes;this.attempts=attempts;this.users=users;}
    @Transactional(readOnly=true) public List<Quiz> quizzesFor(Subject subject){return quizzes.findBySubjectOrderByStartsAtDesc(subject);}
    @Transactional(readOnly=true) public List<Quiz> allQuizzes(){return quizzes.findAllByOrderByStartsAtDesc();}
    @Transactional(readOnly=true) public Quiz getQuiz(Long id){return quizzes.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found"));}
    @Transactional public Quiz create(QuizForm form,UserAccount teacher){validate(form);Quiz q=new Quiz(form.getTitle().trim(),teacher.getSubject(),LocalDateTime.parse(form.getStartsAt()),form.getDurationMinutes(),form.isResultsVisible(),teacher.getUsername());q.replaceQuestions(toQuestions(form));return quizzes.save(q);}
    @Transactional public Quiz update(Long id,QuizForm form,UserAccount teacher){validate(form);Quiz q=owned(id,teacher);if(attempts.countByQuizId(id)>0)throw new IllegalStateException("This quiz already has student attempts, so its questions are locked. Create a new quiz for updated questions.");q.update(form.getTitle().trim(),LocalDateTime.parse(form.getStartsAt()),form.getDurationMinutes(),form.isResultsVisible());q.replaceQuestions(toQuestions(form));return q;}
    @Transactional(readOnly=true) public boolean canEdit(Long quizId){return attempts.countByQuizId(quizId)==0;}
    @Transactional(readOnly=true) public Quiz owned(Long id,UserAccount teacher){Quiz quiz=quizzes.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found"));if(quiz.getSubject()!=teacher.getSubject())throw new SecurityException("This quiz belongs to another subject");return quiz;}
    @Transactional(readOnly=true) public QuizAttempt attemptFor(Quiz quiz,String username){return attempts.findByQuizIdAndStudentUsername(quiz.getId(),username).orElse(null);}
    @Transactional(readOnly=true) public List<QuizAttempt> studentHistory(String username){return attempts.findByStudentUsernameOrderByStartedAtDesc(username);}
    @Transactional(readOnly=true) public List<QuizAttempt> attemptsFor(Long quizId,UserAccount teacher){owned(quizId,teacher);return attempts.findByQuizIdOrderByStartedAtDesc(quizId);}
    @Transactional public QuizAttempt start(Long quizId,String username){Quiz quiz=getQuiz(quizId);LocalDateTime now=LocalDateTime.now();if(now.isBefore(quiz.getStartsAt()))throw new IllegalStateException("This quiz is locked until its scheduled start time.");
        if(attemptFor(quiz,username)!=null)throw new IllegalStateException("You have already started or submitted this quiz.");
        UserAccount student=users.findByUsername(username).orElseThrow();return attempts.save(new QuizAttempt(quiz,student,now,now.plusMinutes(quiz.getDurationMinutes())));
    }
    @Transactional public void saveAnswer(Long attemptId,String username,int questionIndex,int selected){QuizAttempt a=ownedAttempt(attemptId,username);if(a.isSubmitted())return;
        if(!LocalDateTime.now().isBefore(a.getEndsAt())){finish(a);return;}
        if(selected<0||selected>3||questionIndex<0||questionIndex>=a.getQuiz().getQuestions().size())throw new IllegalArgumentException("Invalid answer");
        a.putAnswer(a.getQuiz().getQuestions().get(questionIndex),selected);
    }
    @Transactional public QuizAttempt submit(Long attemptId,String username){QuizAttempt a=ownedAttempt(attemptId,username);if(!a.isSubmitted())finish(a);return a;}
    @Transactional(readOnly=true) public QuizAttempt ownedAttempt(Long attemptId,String username){QuizAttempt a=attempts.findById(attemptId).orElseThrow(()->new NoSuchElementException("Attempt not found"));if(!a.getStudent().getUsername().equals(username))throw new SecurityException("Not your quiz attempt");return a;}
    @Transactional public void submitExpired(){for(QuizAttempt a:attempts.findBySubmittedAtIsNullAndEndsAtBefore(LocalDateTime.now()))finish(a);}
    @Transactional public void finish(QuizAttempt a){int score=0;for(AttemptAnswer answer:a.getAnswers())if(answer.getSelectedOption()==answer.getQuestion().getCorrectOption())score+=2;a.finish(score,LocalDateTime.now());}
    public QuizForm toForm(Quiz quiz){QuizForm form=new QuizForm();form.setTitle(quiz.getTitle());form.setStartsAt(quiz.getStartsAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));form.setDurationMinutes(quiz.getDurationMinutes());form.setResultsVisible(quiz.isResultsVisible());List<QuestionForm> qfs=new ArrayList<>();for(QuizQuestion q:quiz.getQuestions()){QuestionForm f=new QuestionForm();f.setPrompt(q.getPrompt());f.setOptionA(q.getOptionA());f.setOptionB(q.getOptionB());f.setOptionC(q.getOptionC());f.setOptionD(q.getOptionD());f.setCorrectOption(q.getCorrectOption());qfs.add(f);}form.setQuestions(qfs);return form;}
    private List<QuizQuestion> toQuestions(QuizForm form){List<QuizQuestion> out=new ArrayList<>();int i=1;for(QuestionForm q:form.getQuestions())out.add(new QuizQuestion(i++,q.getPrompt().trim(),q.getOptionA().trim(),q.getOptionB().trim(),q.getOptionC().trim(),q.getOptionD().trim(),q.getCorrectOption()));return out;}
    private void validate(QuizForm f){if(f.getTitle()==null||f.getTitle().isBlank()||f.getTitle().length()>100)throw new IllegalArgumentException("Enter a quiz title up to 100 characters.");if(f.getQuestions()==null||f.getQuestions().size()!=10)throw new IllegalArgumentException("A quiz must contain exactly 10 questions.");if(f.getDurationMinutes()<1||f.getDurationMinutes()>180)throw new IllegalArgumentException("Duration must be between 1 and 180 minutes.");try{LocalDateTime.parse(f.getStartsAt());}catch(Exception e){throw new IllegalArgumentException("Choose a valid scheduled start time.");}for(QuestionForm q:f.getQuestions()){if(q.getPrompt()==null||q.getPrompt().isBlank()||q.getOptionA().isBlank()||q.getOptionB().isBlank()||q.getOptionC().isBlank()||q.getOptionD().isBlank()||q.getCorrectOption()<0||q.getCorrectOption()>3)throw new IllegalArgumentException("Complete all 10 questions, four options, and the correct answer.");}}
}
