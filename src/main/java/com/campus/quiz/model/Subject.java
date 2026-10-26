package com.campus.quiz.model;

public enum Subject {
    ADVANCED_JAVA("Advanced Java Programming", "AJ", "#7766f5"),
    CLOUD_COMPUTING("Cloud Computing", "CC", "#1ca6a1"),
    COMPUTER_NETWORKS("Computer Networks", "CN", "#5185eb"),
    FRONTEND_ENGINEERING("Frontend Engineering", "FE", "#ec8a42"),
    APTITUDE("Aptitude", "AP", "#d85b91");
    private final String label, shortName, color;
    Subject(String label, String shortName, String color) { this.label=label; this.shortName=shortName; this.color=color; }
    public String getLabel(){return label;} public String getShortName(){return shortName;} public String getColor(){return color;}
}
