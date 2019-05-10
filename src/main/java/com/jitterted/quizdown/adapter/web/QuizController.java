package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.QuestionIterator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class QuizController {

  private final QuestionIterator questionIterator;
  private final QuestionTransformer questionTransformer;

  public QuizController(
      QuestionIterator questionIterator,
      QuestionTransformer questionTransformer) {
    this.questionIterator = questionIterator;
    this.questionTransformer = questionTransformer;
  }

  public static final String HTML_HEADER = "<html>\n" +
      "<head>\n" +
      "<title>Hello</title>\n" +
      "</head>\n" +
      "<body>\n" +
      "<div>Hi there, <span th:text=\"${name}\">Text</span></div>\n";
  public static final String HTML_FOOTER = "</body>\n" +
      "</html>\n";

  @GetMapping("/")
  public String question(Model model) {
    model.addAttribute("name", "Ted");
    return HTML_HEADER +
        nextQuestionHtml() +
        HTML_FOOTER;
  }

  private String nextQuestionHtml() {
    return questionTransformer.toHtml(questionIterator.next());
  }

  @PostMapping("/answer")
  public String answer(@RequestParam Map<String, String> map) {
    if (questionIterator.hasNext()) {
      return "redirect:/";
    }
    return "redirect:/done";
  }

  @GetMapping("/done")
  public String quizDone() {
    return "Well done!";
  }

}
