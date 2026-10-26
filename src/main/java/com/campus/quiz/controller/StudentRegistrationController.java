package com.campus.quiz.controller;

import com.campus.quiz.model.UserAccount;import com.campus.quiz.repo.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;

@Controller public class StudentRegistrationController {
 private final UserAccountRepository users;private final PasswordEncoder encoder;
 public StudentRegistrationController(UserAccountRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @GetMapping("/register") public String register(){return "auth/register";}
 @PostMapping("/register") public String create(@RequestParam String username,@RequestParam String displayName,@RequestParam String password,Model model){
  String id=username.trim();String name=displayName.trim();if(!id.matches("[A-Za-z0-9._-]{3,40}")){model.addAttribute("error","Use 3–40 letters, numbers, dots, dashes, or underscores for your student ID.");return "auth/register";}
  if(name.length()<2||name.length()>80){model.addAttribute("error","Enter a name between 2 and 80 characters.");return "auth/register";}
  if(password.length()<8){model.addAttribute("error","Choose a password with at least 8 characters.");return "auth/register";}
  if(users.findByUsername(id).isPresent()){model.addAttribute("error","That student ID is already registered. Try signing in.");return "auth/register";}
  users.save(new UserAccount(id,encoder.encode(password),name,"STUDENT",null));return "redirect:/login?registered";
 }
}
