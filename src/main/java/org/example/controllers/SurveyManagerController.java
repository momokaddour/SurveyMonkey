package org.example.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.answers.Answer;
import org.example.answers.MCAnswer;
import org.example.answers.NumberRangeAnswer;
import org.example.answers.TextAnswer;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;
import org.example.questions.TextQuestion;
import org.example.repos.FormRepo;
import org.example.repos.QuestionRepo;
import org.example.repos.SurveyRepo;
import org.example.results.Aggregate;
import org.example.results.Compiler;
import org.example.survey.Form;
import org.example.survey.Survey;
import org.example.survey.SurveyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
    @RequestMapping(value = "/createSurvey", method = PUT)
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
    @RequestMapping(value = "/addTextQuestion", method = PUT)
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
    @RequestMapping(value = "/addMCQuestion", method = PUT)
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
     * in the request as well using the aptly named parameters.
     * @return none
     * @param ID Integer
     * @param questionText String
     * @param max int
     * @param min int
     * */
    @RequestMapping(value = "/addNumRangeQuestion", method = PUT)
    @ResponseBody
    public void addNumRangeQuestion(@RequestParam(value = "surveyID") Integer ID,
                                    @RequestParam(value = "question") String questionText,
                                    @RequestParam(value = "min") int min,
                                    @RequestParam(value = "max") int max)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        survey.addQuestion(new NumberRangeQuestion(questionText, min, max));
        surveyRepo.save(survey);
    }

    /**
     * Closes the survey. As designed, closing the survey allows for the compilation of all results to begin.
     * @return Survey
     * @param ID the ID to identify the survey to close
     * */
    @RequestMapping(value = "/closeSurvey", method = PUT)
    @ResponseBody
    public Aggregate closeSurvey(@RequestParam(value = "surveyID") Integer ID)
    {
        Aggregate aggregate = compiler.compile(surveyRepo.findBySurveyID(ID));
        return aggregate;
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
    @RequestMapping(value = "/createForm", method = PUT)
    @ResponseBody
    public void createForm(@RequestParam(value = "surveyID") Integer ID)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        Form form = new Form();
        survey.addForm(form);
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
    @RequestMapping(value = "/answerMC", method = PUT)
    @ResponseBody
    public void answerMCSurveyQuestion(@RequestParam(value = "formID") Integer ID,
                                       @RequestParam(value = "questionID") Integer qNum,
                                       @RequestParam(value = "answer") String s)
    {
        Form form = formRepo.findByFormID(ID);

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
    @RequestMapping(value = "/answerTextQuestion", method = PUT)
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
    @RequestMapping(value = "/answerNumRange", method = PUT)
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

        Question question1 = new TextQuestion();
        question1.setQuestion("What is the meaning of life?");
        Question question2 = new MultipleChoiceQuestion();
        question2.setQuestion("Which of the following is true?");
        Question question3 = new NumberRangeQuestion();
        question3.setQuestion("How many people are in the world?");

        survey.addQuestion(question1);
        survey.addQuestion(question2);
        survey.addQuestion(question3);

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
        Form form = new Form(survey);
        formRepo.save(form);

        model.addAttribute("formID", form.getFormID());

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
}
