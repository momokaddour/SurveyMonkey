package org.example.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.example.answers.MCAnswer;
import org.example.answers.NumberRangeAnswer;
import org.example.answers.TextAnswer;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;
import org.example.questions.TextQuestion;
import org.example.repos.AggregateRepo;
import org.example.repos.FormRepo;
import org.example.repos.QuestionRepo;
import org.example.repos.SurveyRepo;
import org.example.results.Aggregate;
import org.example.results.Compiler;
import org.example.results.Result;
import org.example.results.TextAnswerList;
import org.example.survey.Form;
import org.example.survey.Survey;
import org.example.survey.SurveyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Strings;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * SurveyManagerController class responsible for user interaction with the application
 * @author Mohamed Kaddour, Akshay Vashisht
 */
@Controller
public class SurveyManagerController {

    @Autowired
    private SurveyRepo surveyRepo;

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private AggregateRepo aggregateRepo;

    private SurveyManager surveyManager;

    private Compiler compiler;


    /**
     * Initializes the survey manager
     * */
    @PostConstruct
    public void initialize() {
        this.surveyManager = new SurveyManager();
        this.compiler = new Compiler();
    }

    /**
     * Request to create a brand new survey with an automatically generated ID
     * @return A JSON object instance of the survey
     * */
    @Transactional
    @RequestMapping(value = "/createSurvey", method = GET)
    @ResponseBody
    public Survey createSurvey()
    {
        Survey survey = new Survey();
        surveyRepo.save(survey);
        return survey;
    }

    /**
     * Adds a text question to the requested survey. This is done through providing the request an ID to identify
     * the desired survey and then specifying the question.
     * @return none
     * @param ID Integer
     * @param questionText String
     * */
    @Transactional
    @RequestMapping(value = "/addTextQuestion", method = GET)
    @ResponseBody
    public void addTextQuestion(@RequestParam(value = "surveyID") Integer ID,
                                @RequestParam(value = "question") String questionText)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        survey.addQuestion(new TextQuestion(questionText));
        surveyRepo.save(survey);
    }

    /**
     * Adds an MC question to the requested survey. This is done through providing the request an ID to identify
     * the desired survey and then specifying the question. The request is also specified a series of options,
     * through a single String where each option is seperated by a '!' character
     * @return none
     * @param ID Integer
     * @param questionText String
     * @param options String
     * */
    @RequestMapping(value = "/addMCQuestion", method = GET)
    @ResponseBody
    public void addMcQuestion(@RequestParam(value = "surveyID") Integer ID,
                              @RequestParam(value = "question") String questionText,
                              @RequestParam(value = "options") String options)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestion(questionText);

        //NOTE: For options, when using the request, separate each option by a '!'
        //Assume the limit for MC options is 10
        String[] optionsArr = options.split("!", 10);

        for (String option : optionsArr)
        {
            question.addChoice(option);
        }

        survey.addQuestion(question);
        surveyRepo.save(survey);
    }

    /**
     * Adds a num range question to the requested survey. This is done through providing the request an ID to identify
     * the desired survey and then specifying the question. Specify the maximum and minimum possible values
     * in the request as well using the aptly named parameters. The range is the range to display on the histogram.
     * @return none
     * @param ID Integer
     * @param questionText String
     * @param max int
     * @param min int
     * @param range int
     * */
    @RequestMapping(value = "/addNumRangeQuestion", method = GET)
    @ResponseBody
    public void addNumRangeQuestion(@RequestParam(value = "surveyID") Integer ID,
                                    @RequestParam(value = "question") String questionText,
                                    @RequestParam(value = "min") int min,
                                    @RequestParam(value = "max") int max,
                                    @RequestParam(value = "range") int range)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        if (range > (max - min)) {
            System.out.println("INVALID RANGE...ABORTING");
        }
        survey.addQuestion(new NumberRangeQuestion(questionText, min, max, range));
        surveyRepo.save(survey);
    }

    /**
     * Closes the survey. As designed, closing the survey allows for the compilation of all results to begin.
     * @return Survey
     * @param ID the ID to identify the survey to close
     * */
    @RequestMapping(value = "/closeSurvey", method = GET)
    @ResponseBody
    public String closeSurvey(@RequestParam(value = "surveyID") Integer ID)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        Aggregate aggregate = compiler.compile(survey);

        survey.setAggregate(aggregate);
        survey.close();
        surveyRepo.save(survey);
        return "Survey " + ID + " closed, you can close this window.";
    }

    /**
     * Returns the requested aggregate
     * @return Aggregate
     * @param ID The ID to identify the aggregate
     * */
    @RequestMapping(value = "/getAggregate", method = GET)
    public String getAggregate(@RequestParam(value = "surveyID") Integer ID, Model model)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        Aggregate aggregate = survey.getAggregate();

        List<TextAnswerList> textAnswerLists = new ArrayList<>();

        for(Result r :  aggregate.getResults()) {
            if(r instanceof TextAnswerList) {
                textAnswerLists.add((TextAnswerList) r);
            }
        }

        model.addAttribute("survey", survey);
        model.addAttribute("textAnswerLists", textAnswerLists);
        model.addAttribute("imageNames", aggregate.getImageNames());

        return "viewImages";
    }

    /**
     * A simple get that returns a JSON object of the survey given an ID.
     * @return none
     * @param ID the ID to identify the server
     * */
    @RequestMapping(value = "/getSurvey", method = GET)
    @ResponseBody
    public Survey getSurvey(@RequestParam(value = "surveyID") Integer ID)
    {
        //Return survey of specified ID
        return surveyRepo.findBySurveyID(ID);
    }

    /**
     * Creates a new form for the specified survey ID
     *
     * @param ID survey ID that the form is for.
     * */
    @RequestMapping(value = "/createForm", method = GET)
    @ResponseBody
    public void createForm(@RequestParam(value = "surveyID") Integer ID)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        Form form = new Form();
        survey.addForm(form);
        System.out.println(form.getFormID());
        surveyRepo.save(survey);
    }

    /**
     * Answers an MC question for the specified question of the survey for the specified form ID. Method
     * also ensures that the question is actually multiple choice and then checks that the option is
     * valid.
     *
     * @param ID
     * @param s
     * @param qNum
     * */
    @RequestMapping(value = "/answerMC", method = GET)
    @ResponseBody
    public void answerMCSurveyQuestion(@RequestParam(required = false, value = "formID", name="formID") Integer ID,
                                       @RequestParam(required = false, value = "questionID", name="questionID") Integer qNum,
                                       @RequestParam(required = false, value = "answer", name="answer") String s)
    {
        Form form = formRepo.findByFormID(ID);

        System.out.println("formID: " + ID);
        System.out.println("questionID: " + qNum);
        System.out.println("selectedChoice: " + s);

        //Checks if that question is of type MC, if not get out.
        if (!(questionRepo.findByQuestionId(qNum) instanceof MultipleChoiceQuestion))
        {
            //failure temporary log
            System.out.println("FAIL NO MC QUESTION");
        }
        else
        {
            if ((((MultipleChoiceQuestion) questionRepo.findByQuestionId(qNum)).getChoices()).contains(s)) {
                form.addAnswer(new MCAnswer(s), qNum);
                formRepo.save(form);
            }
        }
    }

    /**
     * Answers a Text question for the specified question of the survey for the specified form ID. Method
     * also ensures that the question is actually text.
     *
     * @param ID
     * @param s
     * @param qNum
     * */
    @RequestMapping(value = "/answerTextQuestion", method = GET)
    @ResponseBody
    public void answerTextQuestion(@RequestParam(value = "formID") Integer ID,
                                   @RequestParam(value = "questionID") Integer qNum,
                                   @RequestParam(value = "answer") String s)
    {
        Form form = formRepo.findByFormID(ID);

        //Checks if that question is of type Text, if not get out.
        if (!(questionRepo.findByQuestionId(qNum) instanceof TextQuestion))
        {
            //failure temporary log
            System.out.println("FAIL NOT TEXT QUESTION");
        }
        else
        {
            form.addAnswer(new TextAnswer(s), qNum);
            formRepo.save(form);
        }
    }

    /**
     * Answers a num range question for the specified question of the survey for the specified form ID. Method
     * also ensures that the question is actually num range.
     *
     * @param ID
     * @param s
     * @param qNum
     * */
    @RequestMapping(value = "/answerNumRange", method = GET)
    @ResponseBody
    public void answerNumRangeQuestion(@RequestParam(value = "formID") Integer ID,
                                       @RequestParam(value = "questionID") Integer qNum,
                                       @RequestParam(value = "answer") String s)
    {
        Form form = formRepo.findByFormID(ID);

        //Checks if that question is of type Text, if not get out.
        if (!(questionRepo.findByQuestionId(qNum) instanceof NumberRangeQuestion question))
        {
            //failure temporary log
            System.out.println("FAIL NOT NUM RANGE");
        }
        else
        {
            int number = Integer.parseInt(s);

            if ((number < question.getMinNumber()) || (number > question.getMaxNumber()))
            {
                System.out.println("TEMP FAIL OUT OF RANGE");
            }
            else {
                form.addAnswer(new NumberRangeAnswer(s), qNum);
                formRepo.save(form);
            }
        }
    }

    /**
     * Returns the form to view responses.
     *
     * @return form
     * @param ID form ID
     * */
    @RequestMapping(value = "/getForm", method = GET)
    @ResponseBody
    public Form getForm(@RequestParam(value = "formID") Integer ID)
    {
        //Return survey of specified ID
        return formRepo.findByFormID(ID);
    }

    /**
     * Returns the forms to view all responses for the specified Survey.
     *
     * @return form
     * @param ID Integer
     * */
    @RequestMapping(value = "/getForms", method = GET)
    @ResponseBody
    public List<Form> getForms(@RequestParam(value = "surveyID") Integer ID)
    {
        //Return survey of specified ID
        return surveyRepo.findBySurveyID(ID).getForms();
    }

    /**
     * Renders the view for the template "surveyViewCreateSurvey"
     *
     * @param model Model
     * @return String
     */
    @Transactional
    @RequestMapping(value = "/surveyViewCreateSurvey", method = GET)
    public String surveyViewCreateSurvey(Model model)
    {
        Survey survey = new Survey();

        surveyRepo.save(survey);

        model.addAttribute("surveyID", survey.getSurveyID());

        return "surveyViewCreateSurvey";
    }

    /**
     * Renders the view for the template "surveyViewCreateForm"
     *
     * @param ID Integer
     * @param model Model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/surveyViewCreateForm", method = GET)
    public String surveyViewCreateForm(@RequestParam(value = "surveyID")Integer ID, Model model)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        Form form = new Form();

        List<MultipleChoiceQuestion> mcList = new ArrayList<>();
        List<NumberRangeQuestion> rangeList = new ArrayList<>();
        List<TextQuestion> textList = new ArrayList<>();

        for (Question q : survey.getQuestions()) {
            if (q instanceof MultipleChoiceQuestion) {
                mcList.add((MultipleChoiceQuestion) q);
            }
            else if (q instanceof NumberRangeQuestion) {
                rangeList.add((NumberRangeQuestion) q);
            }
            else {
                textList.add((TextQuestion) q);
            }
        }

        survey.addForm(form);
        formRepo.save(form);

        model.addAttribute("formID", form.getFormID());
        model.addAttribute("survey", survey);
        model.addAttribute("mcList", mcList);
        model.addAttribute("rangeList", rangeList);
        model.addAttribute("textList", textList);

        return "surveyViewCreateForm";
    }

    /**
     * Renders the view for the template "surveyViewQuestions"
     *
     * @param ID Integer
     * @param model Model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/surveyViewQuestions", method = GET)
    public String surveyViewQuestions(@RequestParam(value = "surveyID") Integer ID, Model model)
    {
        Survey survey = getSurvey(ID);
        List<Question> surveyQuestions = survey.getQuestions();

        model.addAttribute("surveyQuestions", surveyQuestions);
        model.addAttribute("surveyID", ID);
        return "surveyViewQuestions";
    }

    /**
     * Renders the view for the template "surveyViewAnswerText"
     *
     * @param formID Integer
     * @param questionID Integer
     * @param surveyID Integer
     * @param answer String
     * @param model Model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/surveyViewAnswerText", method = GET)
    public String surveyViewAnswerText(@RequestParam(value = "formID") Integer formID,
                                       @RequestParam(value = "questionID") Integer questionID,
                                       @RequestParam(value = "surveyID") Integer surveyID,
                                       @RequestParam(value = "answer") String answer, Model model)
    {
        Survey survey = getSurvey(surveyID);
        Form form = getForm(formID);

        Question question = survey.getQuestions().get(questionID-1);

        TextAnswer textAnswer = new TextAnswer();
        textAnswer.setAnswer(answer);

        form.addAnswer(textAnswer, questionID);
        survey.addForm(form);

        formRepo.save(form);
        surveyRepo.save(survey);

        model.addAttribute("survey", survey);
        model.addAttribute("question", question);
        model.addAttribute("form", form);
        model.addAttribute("answer", answer);

        return "surveyViewAnswerText";
    }

    @RequestMapping(value = "/")
    public String mainPage() {
        return "home";
    }

    @RequestMapping(value = "/textQuestion")
    public String addTextQuestion() {
        return "addText";
    }

    @RequestMapping(value = "/mcQuestion")
    public String addMCQuestion() {
        return "addMC";
    }

    @RequestMapping(value = "/numberQuestion")
    public String addNumberQuestion() {
        return "addNumber";
    }

    @RequestMapping(value = "/surveyViewCloseSurvey")
    public String surveyViewCloseSurvey() {
        return "surveyViewCloseSurvey";
    }

    @RequestMapping(value = "/surveyViewGetAggregate")
    public String surveyViewGetAggregate() {
        return "surveyViewGetAggregate";
    }

    @RequestMapping(value = "/viewSurveys")
    public String viewSurveys(Model m) {
        List<Survey> allSurveys = new ArrayList<>();
        for(Survey s: surveyRepo.findAll()){
            allSurveys.add(s);
        }

        for(int i = 0; i < allSurveys.size(); i++){
            m.addAttribute(allSurveys);
        }

        return "allsurveys";
    }

    @RequestMapping(value = "/submitAnswers", method = RequestMethod.GET)
    @ResponseBody
    public void submitAnswers(HttpServletRequest request) {
        Integer formID = Integer.parseInt(request.getParameter("formID"));

        // Iterate over all parameters in the request
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            // Check if the parameter is related to a question
            if (paramName.startsWith("answer_") || paramName.startsWith("rangeQuestion_") || paramName.equals("textQuestion")) {
                Integer questionID = Integer.parseInt(request.getParameter("questionID"));
                String answer = request.getParameter(paramName);
                String questionType = request.getParameter("questionType");

                // Print or handle the answer as needed
                System.out.println("FormID: " + formID);
                System.out.println("QuestionID: " + questionID);
                System.out.println("Answer: " + answer);
                System.out.println("QuestionType: " + questionType);

                // Handle the answer based on question type (MC, Range, Text)
                switch (questionType) {
                    case "MC":
                        handleMCAnswer(formID, questionID, answer);
                        break;
                    case "Range":
                        handleRangeAnswer(formID, questionID, answer);
                        break;
                    case "Text":
                        handleTextAnswer(formID, questionID, answer);
                        break;
                    // Add more cases for other question types if needed
                }
            }
        }
    }

    private void handleMCAnswer(Integer formID, Integer questionID, String answer) {
        Form form = formRepo.findByFormID(formID);

        if (!(questionRepo.findByQuestionId(questionID) instanceof MultipleChoiceQuestion)) {
            System.out.println("FAIL NO MC QUESTION");
        } else {
            if ((((MultipleChoiceQuestion) questionRepo.findByQuestionId(questionID)).getChoices()).contains(answer)) {
                form.addAnswer(new MCAnswer(answer), questionID);
                formRepo.save(form);
            }
        }
    }

    private void handleRangeAnswer(Integer formID, Integer questionID, String answer) {
        Form form = formRepo.findByFormID(formID);

        if (!(questionRepo.findByQuestionId(questionID) instanceof NumberRangeQuestion question)) {

            System.out.println("FAIL NOT NUM RANGE");
        } else {
            int number = Integer.parseInt(answer);

            if ((number < question.getMinNumber()) || (number > question.getMaxNumber())) {
                System.out.println("TEMP FAIL OUT OF RANGE");
            } else {
                form.addAnswer(new NumberRangeAnswer(answer), questionID);
                formRepo.save(form);
            }
        }
    }

    private void handleTextAnswer(Integer formID, Integer questionID, String answer) {
        Form form = formRepo.findByFormID(formID);

        //Checks if that question is of type Text, if not get out.
        if (!(questionRepo.findByQuestionId(questionID) instanceof TextQuestion)) {
            //failure temporary log
            System.out.println("FAIL NOT TEXT QUESTION");
        } else {
            form.addAnswer(new TextAnswer(answer), questionID);
            formRepo.save(form);
        }
    }


}
