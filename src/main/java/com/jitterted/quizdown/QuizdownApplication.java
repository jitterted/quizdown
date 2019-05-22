package com.jitterted.quizdown;

import com.jitterted.quizdown.adapter.QuizParser;
import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class QuizdownApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizdownApplication.class, args);
  }

  @Bean
  public ITemplateResolver stringTemplateResolver() {
    StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setOrder(2);
    return templateResolver;
  }

  @Bean
  public QuestionStore fromQuizdown() {
    ClassPathResource resource = new ClassPathResource("/quiz.md", QuizdownApplication.class);
    try {
      String quizdown = Files.readString(resource.getFile().toPath());
      return new QuizParser().parse(quizdown);
    } catch (IOException e) {
      throw new IllegalStateException("Can't locate Quiz at " + resource.getPath());
    }
  }
}
