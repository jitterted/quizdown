package com.jitterted.quizdown;

import com.jitterted.quizdown.adapter.QuizParser;
import com.jitterted.quizdown.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class QuizdownApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizdownApplication.class, args);
  }

  @Autowired
  private SpringTemplateEngine templateEngine;

  @PostConstruct
  public void addTemplate() {
    StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setOrder(1);
    templateEngine.addTemplateResolver(templateResolver);
  }

  @Bean
  public QuestionIterator fromQuizdown() {
    String quizdown = "|mc|A,B| Choose your favorite Java keywords:\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n" +
        "\n" +
        "C. volatile\n" +
        " \n" +
        "D. switch\n" +
        "\n" +
        "---\n" +
        "\n" +
        "|fib|map,hashmap| If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?\n";

    List<Question> questions = new QuizParser().parse(quizdown);
    return new QuestionIterator(questions);
  }
}
