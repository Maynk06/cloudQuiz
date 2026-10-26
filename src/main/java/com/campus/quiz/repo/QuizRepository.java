package com.campus.quiz.repo;
import com.campus.quiz.model.Quiz;
import com.campus.quiz.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuizRepository extends JpaRepository<Quiz,Long>{ List<Quiz> findAllByOrderByStartsAtDesc(); List<Quiz> findBySubjectOrderByStartsAtDesc(Subject subject); }
