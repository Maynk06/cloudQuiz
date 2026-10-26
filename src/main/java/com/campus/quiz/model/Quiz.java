package com.campus.quiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Subject subject;
    @Column(nullable=false) private LocalDateTime startsAt;
    @Column(nullable=false) private int durationMinutes=15;
    @Column(nullable=false) private boolean resultsVisible=true;
    @Column(nullable=false) private String createdBy;
    @OneToMany(mappedBy="quiz",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
    @OrderBy("position ASC") private List<QuizQuestion> questions=new ArrayList<>();
    protected Quiz() {}
    public Quiz(String title,Subject subject,LocalDateTime startsAt,int durationMinutes,boolean resultsVisible,String createdBy){this.title=title;this.subject=subject;this.startsAt=startsAt;this.durationMinutes=durationMinutes;this.resultsVisible=resultsVisible;this.createdBy=createdBy;}
    public void replaceQuestions(List<QuizQuestion> newQuestions){questions.clear();for(QuizQuestion q:newQuestions){q.setQuiz(this);questions.add(q);}}
    public Long getId(){return id;} public String getTitle(){return title;} public Subject getSubject(){return subject;} public LocalDateTime getStartsAt(){return startsAt;}
    public int getDurationMinutes(){return durationMinutes;} public boolean isResultsVisible(){return resultsVisible;} public String getCreatedBy(){return createdBy;} public List<QuizQuestion> getQuestions(){return questions;}
    public void update(String title,LocalDateTime startsAt,int durationMinutes,boolean resultsVisible){this.title=title;this.startsAt=startsAt;this.durationMinutes=durationMinutes;this.resultsVisible=resultsVisible;}
}
