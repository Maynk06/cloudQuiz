package com.campus.quiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Arrays;

@Configuration
@ControllerAdvice
public class GlobalModelAttributes {
    private final Environment environment;
    public GlobalModelAttributes(Environment environment){this.environment=environment;}
    @ModelAttribute
    public void addCloudMode(Model model){model.addAttribute("cloudMode", Arrays.asList(environment.getActiveProfiles()).contains("cloud"));}
}
