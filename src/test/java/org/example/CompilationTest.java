package org.example;

import org.example.answers.Answer;
import org.example.answers.MCAnswer;
import org.example.answers.NumberRangeAnswer;
import org.example.answers.TextAnswer;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;
import org.example.questions.TextQuestion;
import org.example.results.Aggregate;
import org.example.results.Compiler;
import org.example.survey.Form;
import org.example.survey.Survey;

import org.example.survey.SurveyManager;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class CompilationTest {

    private static Survey survey;
    private static SurveyManager manager;

    private static final int FORM_ID = 0;


    @BeforeAll
    public static void setUp() {
        survey = new Survey();
        survey.setSurveyID(1);

        manager = new SurveyManager();

        MultipleChoiceQuestion MCQuestion = new MultipleChoiceQuestion(
                "which of the following is true");

        MCQuestion.addChoice("true");
        MCQuestion.addChoice("false");

        TextQuestion textQuestion = new TextQuestion("What is the meaning of life?");

        NumberRangeQuestion numberRangeQuestion = new NumberRangeQuestion(
                "How many people are there in the world?", 0, 10);

        survey.addQuestion(MCQuestion);
        survey.addQuestion(textQuestion);
        survey.addQuestion(numberRangeQuestion);

        manager.addSurvey(survey);

        Form form = new Form();

        manager.answerSurvey(1, form);

        form.addAnswer(new MCAnswer("true"), 0);
        form.addAnswer(new TextAnswer("42"), 1);
        form.addAnswer(new NumberRangeAnswer("9"), 2);
    }

    @Test
    public void testAddMCQuestion() {
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) survey.getQuestions().get(0);

        assertThat(question.getQuestion().equals("which of the following is true"));
        assertThat(question.getChoices().contains("true"));
        assertThat(question.getChoices().contains("false"));

    }

    @Test
    public void testAddTextQuestion() {
        TextQuestion question = (TextQuestion) survey.getQuestions().get(1);

        assertThat(question.getQuestion().equals("What is the meaning of life?"));

    }
    @Test
    public void testAddNumberRangeQuestion() {
        NumberRangeQuestion question = (NumberRangeQuestion) survey.getQuestions().get(2);

        assertThat(question.equals("How many people are there in the world?"));
        assertThat(question.getMaxNumber() == 10);
        assertThat(question.getMaxNumber() == 0);
    }
    @Test
    public void testAnswerQuestion() {

        Map<Integer, Answer> answers = survey.getForms().get(FORM_ID).getAnswers();

        assertThat(answers.get(0).getAnswer().equals("true"));
        assertThat(answers.get(1).getAnswer().equals("42"));
        assertThat(answers.get(2).getAnswer().equals("9"));
    }

    @Test
    public void testCompile() {
        manager.compile(1);

        Aggregate aggregate = manager.getAggregate(1);

        System.out.println(aggregate.getResult(0));

    }


}
