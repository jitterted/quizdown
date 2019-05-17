package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@SessionAttributes("question")
public class QuizController {

  private final QuestionTransformer questionTransformer;
  private final AnswerService answerService;
  private final QuestionStore questionStore;

  @Autowired
  public QuizController(
      QuestionStore questionStore,
      QuestionTransformer questionTransformer,
      AnswerService answerService) {
    this.questionStore = questionStore;
    this.questionTransformer = questionTransformer;
    this.answerService = answerService;
  }

  private static final String HTML_HEADER = "<html>\n" +
      "<head>\n" +
      "<title>Hello</title>\n" +
      "</head>\n" +
      "<body>\n" +
      "<div>Hi there, <span th:text=\"${name}\">Text</span></div>\n";
  private static final String HTML_FOOTER = "</body>\n" +
      "</html>\n";

  @ModelAttribute("question")
  public Integer initializeQuestionNumber() {
    return 1;
  }

  @GetMapping("/")
  public String question(Model model, @ModelAttribute("question") Integer questionNumber) {
    model.addAttribute("name", "Ted");
    return HTML_HEADER +
        htmlForQuestionNumber(questionNumber) +
        HTML_FOOTER;
  }

  private String htmlForQuestionNumber(int questionNumber) {
    return questionTransformer.toHtml(questionStore.findByNumber(questionNumber));
  }

  @PostMapping("/answer")
  public String answer(@RequestParam Map<String, String> map,
      Model model,
      @ModelAttribute("question") Integer questionNumber) {
    answerService.process(map);

    questionNumber++;
    model.addAttribute("question", questionNumber);

    if (questionNumber <= questionStore.count()) {
      return "redirect:/";
    }

    return "redirect:/done";
  }

  @GetMapping("/done")
  public String quizDone(Model model, SessionStatus sessionStatus) {
    sessionStatus.setComplete();
    model.addAttribute("results", answerService.results());
    return "results";
  }

}
