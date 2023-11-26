package org.example;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test Class
 * Tests controller end points and function
 * @author Bilal Chaudhry 101141634
 * @version 1.0
 */

@SpringBootTest()
@AutoConfigureMockMvc
public class SurveyApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private static int numSurveys = 0;
    private static int numForms = 0;
    private static int questionID = 0;

    /**
     * Create a survey and form for each other test case
     * @param testInfo : used to allow exclusion of certain test cases
     * @throws Exception
     */
    @BeforeEach
    public void testCreateSurveyAndForm(TestInfo testInfo) throws Exception {
        if (testInfo.getTags().contains("exclude")) {
            return;
        }

        // increment the amount of surveys and forms
        numSurveys++;
        numForms++;

        mockMvc.perform(get("/createSurvey"))
                .andDo(print())
                .andExpect(jsonPath("$.surveyID").value(numSurveys))
                .andReturn();

        mockMvc.perform(get("/createForm")
                        .param("surveyID", String.valueOf(numSurveys) ))
                .andDo(print());
    }

    /**
     * Test method to test creation and answering of a TextQuestion
     * @throws Exception
     */
    @Test
    public void testAddAndAnswerTextQuestion() throws Exception {

        // create a text question
        mockMvc.perform(get("/addTextQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "What is your name"))
                .andDo(print());

        // verify the creation of the question
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath("$.questions[0].question")
                        .value("What is your name"))
                .andExpect(jsonPath("$.questions[0].id")
                        .value(questionID + 1));

        // create an answer for the question
        mockMvc.perform(get("/answerTextQuestion")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "Bilal")
                )
                .andDo(print());

        // verify the creation of the answer
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("Bilal")
                );

        // increment the ID, as question IDs are unique
        questionID++;
    }

    /**
     * Test method to test creation and answering of a MCQuestion
     * @throws Exception
     */
    @Test
    public void testAddAndAnswerMCQuestion() throws Exception {

        // create a MC question
        mockMvc.perform(get("/addMCQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "Pick a colour")
                        .param("options", "blue!green!red!purple"))
                .andDo(print());

        // verify the creation of the MC question
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath("$.questions[0].question")
                        .value("Pick a colour"))
                .andExpect(jsonPath("$.questions[0].id")
                        .value(questionID + 1));

        // create MC question answer
        mockMvc.perform(get("/answerMC")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "blue")
                )
                .andDo(print());

        // verify the creation of the MC answer
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("blue"));

        mockMvc.perform(get("/closeSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print());

        questionID++;
    }

    /**
     * Test method to test creation and answering of a NumRange
     * @throws Exception
     */
    @Test
    public void testAddAndAnswerNumRangeQuestion() throws Exception {

        // create a NumRange question
        mockMvc.perform(get("/addNumRangeQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "Pick a number between 12 and 19")
                        .param("min", "12")
                        .param("max", "19"))
                .andDo(print());

        // verify the creation of the question
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath("$.questions[0].question")
                        .value("Pick a number between 12 and 19"))
                .andExpect(jsonPath("$.questions[0].minNumber")
                        .value("12"))
                .andExpect(jsonPath("$.questions[0].maxNumber")
                        .value("19"))
                .andExpect(jsonPath("$.questions[0].id")
                        .value(questionID + 1));

        // create an answer for the NumRange
        mockMvc.perform(get("/answerNumRange")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "15")
                )
                .andDo(print());

        // verify the creation of the answer
        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("15"));

        mockMvc.perform(get("/closeSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print());

        mockMvc.perform(get("/getAggregate")
                        .param("aggregateID", "1"))
                .andDo(print()).
                andExpect(jsonPath("$.results[0].answerCount." + "15").value(1));

        questionID++;
    }

    /**
     * Test method to view the forms
     * @throws Exception
     */
    @Test
    @Tag("exclude")
    public void testViewForms() throws Exception {
        mockMvc.perform(get("/getForms")
                        .param("surveyID", "1"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
