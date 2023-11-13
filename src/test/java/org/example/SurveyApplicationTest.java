package org.example;

import org.example.controllers.SurveyManagerController;
import org.example.survey.Survey;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest()
@AutoConfigureMockMvc
public class SurveyApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private static int numSurveys = 0;
    private static int numForms = 0;
    private static int questionID = 0;

    @BeforeEach
    public void testCreateSurveyAndForm(TestInfo testInfo) throws Exception {
        if (testInfo.getTags().contains("exclude")) {
            return;
        }

        numSurveys++;
        numForms++;

        MvcResult result = mockMvc.perform(put("/createSurvey"))
                .andDo(print())
                .andExpect(jsonPath("$.surveyID").value(numSurveys))
                .andReturn();

        mockMvc.perform(put("/startForm")
                        .param("surveyID", String.valueOf(numSurveys) ))
                .andDo(print());
    }

    @Test
    public void testAddAndAnswerTextQuestion() throws Exception {

        mockMvc.perform(put("/addTextQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "What is your name"))
                .andDo(print());

        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath("$.questions[0].question")
                        .value("What is your name"))
                .andExpect(jsonPath("$.questions[0].id")
                        .value(questionID + 1));

        mockMvc.perform(put("/answerTextQuestion")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "Bilal")
                )
                .andDo(print());

        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("Bilal")
                );

        questionID++;
    }

    @Test
    public void testAddAndAnswerMcQuestion() throws Exception {

        mockMvc.perform(put("/addMCQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "Pick a colour")
                        .param("options", "blue!green!red!purple"))
                .andDo(print());

        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath("$.questions[0].question")
                        .value("Pick a colour"))
                .andExpect(jsonPath("$.questions[0].id")
                        .value(questionID + 1));

        mockMvc.perform(put("/answerMC")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "blue")
                )
                .andDo(print());

        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("blue"));

        questionID++;
    }

    @Test
    public void testAddAndAnswerNumRangeQuestion() throws Exception {

        mockMvc.perform(put("/addNumRangeQuestion")
                        .param("surveyID", String.valueOf(numSurveys))
                        .param("question", "Pick a number between 12 and 19")
                        .param("min", "12")
                        .param("max", "19"))
                .andDo(print());

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

        mockMvc.perform(put("/answerNumRange")
                        .param("formID", String.valueOf(numForms))
                        .param("questionID", String.valueOf(questionID + 1))
                        .param("answer", "15")
                )
                .andDo(print());

        mockMvc.perform(get("/getSurvey")
                        .param("surveyID", String.valueOf(numSurveys)))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.forms[0].answers." + (questionID + 1) + ".answer")
                        .value("15"));

        questionID++;
    }

    @Test
    @Tag("exclude")
    public void testViewForms() throws Exception {
        mockMvc.perform(get("/getForms")
                        .param("surveyID", "1"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Tag("exclude")
    public void testCloseSurvey() throws Exception {
        mockMvc.perform(put("/closeSurvey")
                        .param("surveyID", "1"))
                .andDo(print())
                .andExpect(jsonPath(
                        "$.surveyID").value("1"));
    }

}
