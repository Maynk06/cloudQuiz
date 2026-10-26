package com.campus.quiz.repo;
import com.campus.quiz.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;import java.util.List;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{ Optional<UserAccount> findByUsername(String username); List<UserAccount> findByRoleOrderByDisplayNameAsc(String role); }
