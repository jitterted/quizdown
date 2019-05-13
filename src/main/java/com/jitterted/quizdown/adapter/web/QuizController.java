package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.Map;

@Controller
public class QuizController {

  private final QuestionTransformer questionTransformer;
  private final AnswerService answerService;
  private final Iterator<Question> questionIterator;

  public QuizController(
      QuestionStore questionStore,
      QuestionTransformer questionTransformer,
      AnswerService answerService) {
    this.questionIterator = questionStore.questions().iterator();
    this.questionTransformer = questionTransformer;
    this.answerService = answerService;
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
    answerService.process(map);
    if (questionIterator.hasNext()) {
      return "redirect:/";
    }
    return "redirect:/done";
  }

  @GetMapping("/done")
  public String quizDone() {
//    QuizResults results = answerService.results(); // later on this will be keyed by user session info
    // put results into model
    // use template to display their score
    return "Well done!";
  }

}
