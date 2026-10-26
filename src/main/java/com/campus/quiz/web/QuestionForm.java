package com.campus.quiz.web;
public class QuestionForm {
    private String prompt="", optionA="", optionB="", optionC="", optionD=""; private int correctOption;
    public String getPrompt(){return prompt;} public void setPrompt(String v){prompt=v;}
    public String getOptionA(){return optionA;} public void setOptionA(String v){optionA=v;}
    public String getOptionB(){return optionB;} public void setOptionB(String v){optionB=v;}
    public String getOptionC(){return optionC;} public void setOptionC(String v){optionC=v;}
    public String getOptionD(){return optionD;} public void setOptionD(String v){optionD=v;}
    public int getCorrectOption(){return correctOption;} public void setCorrectOption(int v){correctOption=v;}
}
