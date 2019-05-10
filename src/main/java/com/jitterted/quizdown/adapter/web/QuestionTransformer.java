package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.springframework.stereotype.Service;

@Service
public class QuestionTransformer {

  private final HtmlTransformer fillInTheBlankTransformer;
  private final HtmlTransformer multipleChoiceTransformer;

  public QuestionTransformer() {
    fillInTheBlankTransformer = new FillInTheBlankTransformer();
    multipleChoiceTransformer = new MultipleChoiceTransformer();
  }

  public String toHtml(Question question) {
    HtmlTransformer htmlTransformer = transformerFor(question.type());
    return "<form method='post' action='/answer'>\n" +
        htmlTransformer.toHtml(question.content()) +
        "<div><button type=\"submit\">Next</button></div>\n" +
        "</form>\n";
  }

  private HtmlTransformer transformerFor(QuestionType questionType) {
    return switch (questionType) {
      case FIB -> fillInTheBlankTransformer;
      case MC -> multipleChoiceTransformer;
    };
  }

}
