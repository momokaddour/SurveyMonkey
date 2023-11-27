package org.example;

import org.example.answers.Answer;
import org.example.answers.MCAnswer;
import org.example.answers.NumberRangeAnswer;
import org.example.answers.TextAnswer;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.TextQuestion;
import org.example.results.*;
import org.example.survey.Form;
import org.example.survey.Survey;

import org.example.survey.SurveyManager;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Bilal Chaudhry 101141634
 *
 * Test class to test the functionality of the survey manager, survey,
 * form, compiler and aggregate.
 *
 */
public class CompilationTest {

    private static Survey survey;
    private static SurveyManager manager;

    private static final int FORM_ID = 0;


    /**
     * SetUp method to initialize the survey and the form to be tested.
     *
     * Create all the questions before the tests are executed
     */
    @BeforeAll
    public static void setUp() {
        survey = new Survey();
        survey.setSurveyID(1);

        manager = new SurveyManager();

        MultipleChoiceQuestion MCQuestion = new MultipleChoiceQuestion(
                "which of the following is true");
        MCQuestion.setId(0);

        MCQuestion.addChoice("true");
        MCQuestion.addChoice("false");

        TextQuestion textQuestion = new TextQuestion("What is the meaning of life?");
        textQuestion.setId(1);

        NumberRangeQuestion numberRangeQuestion = new NumberRangeQuestion(
                "How many people are there in the world?", 0, 10);
        numberRangeQuestion.setId(2);

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

    /**
     * Test method to verify the creation of a MC question
     *
     * Test passes if the question and the options are saved properly
     */
    @Test
    public void testAddMCQuestion() {
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) survey.getQuestions().get(0);

        assertThat(question.getQuestion().equals("which of the following is true"));
        assertThat(question.getChoices().contains("true"));
        assertThat(question.getChoices().contains("false"));

    }

    /**
     * Test method to verify the creation of a text question
     *
     * Test passes if the question is saved properly
     */
    @Test
    public void testAddTextQuestion() {
        TextQuestion question = (TextQuestion) survey.getQuestions().get(1);

        assertThat(question.getQuestion().equals("What is the meaning of life?"));

    }

    /**
     * Test method to verify the creation of a number range question
     *
     * Test passes if the question and the number range as initialized in method setUp()
     */
    @Test
    public void testAddNumberRangeQuestion() {
        NumberRangeQuestion question = (NumberRangeQuestion) survey.getQuestions().get(2);

        assertThat(question.equals("How many people are there in the world?"));
        assertThat(question.getMaxNumber() == 10);
        assertThat(question.getMaxNumber() == 0);
    }

    /**
     * Test method to verify the answering of the questions
     *
     * Tests pass if the answers were saved properly and in the correct positions
     */
    @Test
    public void testAnswerQuestion() {

        Map<Integer, Answer> answers = survey.getForms().get(FORM_ID).getAnswers();

        assertThat(answers.get(0).getAnswer().equals("true"));
        assertThat(answers.get(1).getAnswer().equals("42"));
        assertThat(answers.get(2).getAnswer().equals("9"));
    }

    /**
     * Test method to verify the creation of the aggregate and the constituting results
     *
     * Test passes if the results contain the correct answers in the correct positions
     */
    @Test
    public void testCompile() {
        manager.compile(1);

        Aggregate aggregate = manager.getAggregate(null);
        aggregate.setAggregateID(1);

        Map<String, Integer> pieChartAnswers =
                ((PieChart) aggregate.getResults().get(0)).getAnswerCount();

        assertThat(pieChartAnswers.get("true") == 1);

        List<String> textListAnswers =
                ((TextAnswerList) aggregate.getResults().get(1)).getAnswerList();

        assertThat(textListAnswers.get(0).equals("42"));

        Map<String, Integer> histogramAnswers =
                ((Histogram) aggregate.getResults().get(2)).getAnswerCount();

        assertThat(histogramAnswers.get("9") == 1);

    }


}
