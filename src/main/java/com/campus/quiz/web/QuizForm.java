package com.campus.quiz.web;
import java.util.ArrayList;import java.util.List;
public class QuizForm {
    private String title="", startsAt=java.time.LocalDateTime.now().plusHours(1).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); private int durationMinutes=15; private boolean resultsVisible=true;
    private List<QuestionForm> questions=new ArrayList<>();
    public QuizForm(){for(int i=0;i<10;i++)questions.add(new QuestionForm());}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getStartsAt(){return startsAt;} public void setStartsAt(String v){startsAt=v;}
    public int getDurationMinutes(){return durationMinutes;} public void setDurationMinutes(int v){durationMinutes=v;}
    public boolean isResultsVisible(){return resultsVisible;} public void setResultsVisible(boolean v){resultsVisible=v;}
    public List<QuestionForm> getQuestions(){return questions;} public void setQuestions(List<QuestionForm> v){questions=v;}
}
