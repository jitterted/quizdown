package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class QuizParserTest {

    @Test
    public void quizdownWithOneFibQuestionParsedIntoListOfOneFibQuestion() throws Exception {
        String fibQuizdown = "|fib|merp| If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";

        QuestionStore questionStore = new QuizParser().parse(fibQuizdown);

        assertThat(questionStore.questions())
                .hasSize(1);

        assertThat(questionStore.questions().get(0).type())
                .isEqualByComparingTo(QuestionType.FIB);
    }

    @Test
    public void withTwoQuestionsHasSizeOfTwo() throws Exception {
        String quizdown = "|mc|A,D| Choose your favorite Java keywords:\n" +
                "\n" +
                "A. final\n" +
                "\n" +
                "B. var\n" +
                "\n" +
                "C. volatile\n" +
                " \n" +
                "D. switch \n" +
                "\n" +
                "---\n" +
                "\n" +
                "|fib|goto| What is your least favorite Java keyword?\n";

        QuestionStore questionStore = new QuizParser().parse(quizdown);

        List<QuestionType> types = questionStore.questions()
                .stream()
                .map(Question::type)
                .collect(Collectors.toList());

        assertThat(types)
                .hasSize(2)
                .containsExactly(QuestionType.MC, QuestionType.FIB);

    }

}