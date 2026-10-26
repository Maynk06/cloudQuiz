package com.campus.quiz.repo;
import com.campus.quiz.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt,Long>{
 Optional<QuizAttempt> findByQuizIdAndStudentUsername(Long quizId,String username);
 List<QuizAttempt> findByStudentUsernameOrderByStartedAtDesc(String username);
 List<QuizAttempt> findByQuizIdOrderByStartedAtDesc(Long quizId);
 List<QuizAttempt> findBySubmittedAtIsNullAndEndsAtBefore(LocalDateTime time);
 long countByQuizId(Long quizId);
}
