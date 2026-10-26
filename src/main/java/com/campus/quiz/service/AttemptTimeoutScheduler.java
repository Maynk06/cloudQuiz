package com.campus.quiz.service;
import org.springframework.scheduling.annotation.Scheduled;import org.springframework.stereotype.Component;
@Component public class AttemptTimeoutScheduler {private final QuizService service;public AttemptTimeoutScheduler(QuizService service){this.service=service;}@Scheduled(fixedDelay=15000) public void closeExpiredAttempts(){service.submitExpired();}}
