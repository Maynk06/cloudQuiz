package com.campus.quiz.model;

import jakarta.persistence.*;

@Entity @Table(name="quiz_questions")
public class QuizQuestion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="quiz_id",nullable=false) private Quiz quiz;
    @Column(nullable=false) private int position;
    @Column(nullable=false,length=700) private String prompt;
    @Column(nullable=false) private String optionA;
    @Column(nullable=false) private String optionB;
    @Column(nullable=false) private String optionC;
    @Column(nullable=false) private String optionD;
    @Column(nullable=false) private int correctOption;
    protected QuizQuestion() {}
    public QuizQuestion(int position,String prompt,String optionA,String optionB,String optionC,String optionD,int correctOption){this.position=position;this.prompt=prompt;this.optionA=optionA;this.optionB=optionB;this.optionC=optionC;this.optionD=optionD;this.correctOption=correctOption;}
    public void setQuiz(Quiz quiz){this.quiz=quiz;} public Long getId(){return id;} public int getPosition(){return position;} public String getPrompt(){return prompt;}
    public String getOptionA(){return optionA;} public String getOptionB(){return optionB;} public String getOptionC(){return optionC;} public String getOptionD(){return optionD;} public int getCorrectOption(){return correctOption;}
    public String option(int index){return switch(index){case 0->optionA;case 1->optionB;case 2->optionC;case 3->optionD;default->"";};}
}
