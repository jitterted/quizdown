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

  private final AnswerService answerService;
  private final QuestionStore questionStore;
  private final HtmlPageGenerator htmlPageGenerator;

  @Autowired
  public QuizController(
      QuestionStore questionStore,
      HtmlPageGenerator htmlPageGenerator,
      AnswerService answerService) {
    this.questionStore = questionStore;
    this.htmlPageGenerator = htmlPageGenerator;
    this.answerService = answerService;
  }

  @ModelAttribute("question")
  public Integer initializeQuestionNumber() {
    return 1;
  }

  @GetMapping("/")
  public String question(Model model, @ModelAttribute("question") Integer questionNumber) {
    model.addAttribute("name", "Ted");

    return htmlPageGenerator.forQuestion(questionStore.findByNumber(questionNumber));
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
