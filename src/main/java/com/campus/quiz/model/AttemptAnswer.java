package com.campus.quiz.model;

import jakarta.persistence.*;

@Entity @Table(name="attempt_answers",uniqueConstraints=@UniqueConstraint(columnNames={"attempt_id","question_id"}))
public class AttemptAnswer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="attempt_id",nullable=false) private QuizAttempt attempt;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="question_id",nullable=false) private QuizQuestion question;
    @Column(nullable=false) private int selectedOption;
    protected AttemptAnswer() {}
    public AttemptAnswer(QuizAttempt attempt,QuizQuestion question,int selectedOption){this.attempt=attempt;this.question=question;this.selectedOption=selectedOption;}
    public QuizQuestion getQuestion(){return question;} public int getSelectedOption(){return selectedOption;} public void setSelectedOption(int value){selectedOption=value;}
}
