package com.campus.quiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="quiz_attempts",uniqueConstraints=@UniqueConstraint(columnNames={"quiz_id","student_id"}))
public class QuizAttempt {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="quiz_id",nullable=false) private Quiz quiz;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private UserAccount student;
    @Column(nullable=false) private LocalDateTime startedAt;
    @Column(nullable=false) private LocalDateTime endsAt;
    private LocalDateTime submittedAt;
    @Column(nullable=false) private int score;
    @OneToMany(mappedBy="attempt",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
    private List<AttemptAnswer> answers=new ArrayList<>();
    protected QuizAttempt() {}
    public QuizAttempt(Quiz quiz,UserAccount student,LocalDateTime startedAt,LocalDateTime endsAt){this.quiz=quiz;this.student=student;this.startedAt=startedAt;this.endsAt=endsAt;}
    public Long getId(){return id;} public Quiz getQuiz(){return quiz;} public UserAccount getStudent(){return student;} public LocalDateTime getStartedAt(){return startedAt;} public LocalDateTime getEndsAt(){return endsAt;} public LocalDateTime getSubmittedAt(){return submittedAt;} public int getScore(){return score;} public List<AttemptAnswer> getAnswers(){return answers;} public boolean isSubmitted(){return submittedAt!=null;}
    public int selectedFor(Long questionId){return answers.stream().filter(a->a.getQuestion().getId().equals(questionId)).map(AttemptAnswer::getSelectedOption).findFirst().orElse(-1);}
    public void putAnswer(QuizQuestion question,int selected){AttemptAnswer answer=answers.stream().filter(a->a.getQuestion().getId().equals(question.getId())).findFirst().orElseGet(()->{AttemptAnswer a=new AttemptAnswer(this,question,selected);answers.add(a);return a;});answer.setSelectedOption(selected);}
    public void finish(int score,LocalDateTime time){this.score=score;this.submittedAt=time;}
}
