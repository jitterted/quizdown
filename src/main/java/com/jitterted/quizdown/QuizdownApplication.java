package com.jitterted.quizdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QuizdownApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizdownApplication.class, args);
  }

  @Autowired
  private SpringTemplateEngine templateEngine;

  @Bean
  public QuestionIterator createQuestionIterator() {
    return new QuestionIterator("{mc} Choose your favorite Java keywords:\n" +
                                    "\n" +
                                    "A. final\n" +
                                    "\n" +
                                    "B. var\n" +
                                    "\n" +
                                    "C. volatile\n" +
                                    " \n" +
                                    "D. switch \n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "{fib} What is your favorite Java keyword?\n");
  }

  @PostConstruct
  public void addTemplate() {
    StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setOrder(1);
    templateEngine.addTemplateResolver(templateResolver);
  }
}
