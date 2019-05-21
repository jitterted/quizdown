package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Set;

@Controller
@SessionAttributes(names = {"question", "name"})
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

  @GetMapping("/")
  public String welcome(Model model) {
    model.addAttribute("welcomeForm", new WelcomeForm(""));
    return "welcome";
  }

  @PostMapping("/start")
  public String startQuiz(Model model, WelcomeForm welcomeForm) {
    model.addAttribute("question", "1");
    model.addAttribute("name", welcomeForm.getFirstName());
    return "redirect:/question";
  }

  @GetMapping("/question")
  public String question(@ModelAttribute("question") Integer questionNumber) {
    return htmlPageGenerator.forQuestion(questionStore.findByNumber(questionNumber));
  }

  @PostMapping("/answer")
  public String answer(Model model,
      @RequestParam Map<String, String> answerMap,
      @ModelAttribute("question") Integer questionNumber,
      @ModelAttribute("name") String name) {

    answerService.processAnswer(name, answerMap);

    questionNumber = incrementQuestionNumberInSession(model, questionNumber);

    if (questionNumber <= questionStore.count()) {
      return "redirect:/question";
    }

    return "redirect:/done";
  }

  private Integer incrementQuestionNumberInSession(Model model, Integer questionNumber) {
    questionNumber++;
    model.addAttribute("question", String.valueOf(questionNumber));
    return questionNumber;
  }

  @GetMapping("/done")
  public String quizSessionDone(@ModelAttribute("name") String name,
      SessionStatus sessionStatus,
      RedirectAttributes redirectAttributes) {
    sessionStatus.setComplete();
    redirectAttributes.addFlashAttribute("username", name);
    return "redirect:/results";
  }

  @GetMapping("/results")
  public String quizResults(Model model, @ModelAttribute("username") String username) {
    Set<Answer> answers = answerService.answersFor(username);
    model.addAttribute("gradedAnswers", GradedAnswerView.toResultsView(answers));
    return "results";
  }

}
