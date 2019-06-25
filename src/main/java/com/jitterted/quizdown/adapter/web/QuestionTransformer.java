package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.Response;
import org.springframework.stereotype.Service;

@Service
public class QuestionTransformer {

  private final HtmlTransformer fillInTheBlankTransformer;
  private final HtmlTransformer multipleChoiceTransformer;

  public QuestionTransformer() {
    fillInTheBlankTransformer = new FillInTheBlankTransformer();
    multipleChoiceTransformer = new MultipleChoiceTransformer();
  }

  public String toHtml(Question question, Response response) {
    HtmlTransformer htmlTransformer = transformerFor(question.type());
    return "<form method='post' action='/answer'>\n" +
        htmlTransformer.toHtml(question.content(), response) +
        "  <input type=\"hidden\" id=\"question\" name=\"question\" value=\"" + question.number() + "\">\n" +
        "  <div class=\"field\">\n" +
        "    <div class=\"control\">\n" +
        generatePreviousLink(question) +
        "      <button class=\"button is-link\">\n" +
        "        Next Question\n" +
        "      </button>\n" +
        "    </div>\n" +
        "  </div>\n" +
        "</form>\n";
  }

  private String generatePreviousLink(Question question) {
    int previousQuestionNumber = question.number() - 1;
    if (previousQuestionNumber == 0) {
      return "      <a class=\"button\" disabled>Previous</a>\n";
    }
    return "      <a class=\"button\" href=\"/question?question=" +
        previousQuestionNumber + "\">Previous</a>\n";
  }

  private HtmlTransformer transformerFor(QuestionType questionType) {
    switch (questionType) {
      case FIB:
        return fillInTheBlankTransformer;
      case MC:
        return multipleChoiceTransformer;
      default:
        throw new IllegalStateException("Type " + questionType + " was not expected.");
    }
  }

}
