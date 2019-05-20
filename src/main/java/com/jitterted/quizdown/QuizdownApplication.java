package com.jitterted.quizdown;

import com.jitterted.quizdown.adapter.QuizParser;
import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

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

    return new QuizParser().parse(quizdown);
  }
}
