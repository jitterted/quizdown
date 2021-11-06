package com.jitterted.quizdown;

import com.jitterted.quizdown.adapter.QuizParser;
import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        ClassPathResource resource = new ClassPathResource("quiz.md");

        try {
            String quizdown = readFromFile(resource);
            return new QuizParser().parse(quizdown);
        } catch (IOException e) {
            throw new IllegalStateException("Can't locate Quiz at " + resource);
        }
    }

    private String readFromFile(ClassPathResource resource) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(resource.getInputStream()), 1024)) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
