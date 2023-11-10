package org.example;

import org.example.repos.SurveyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class SurveyManagerController {

    @Autowired
    private SurveyRepo surveyRepo;

    private SurveyManager surveyManager;

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
    public void addTextQuestion(@RequestParam(value = "SurveyID") Integer ID,
                                @RequestParam(value = "Question") String questionText)
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
    public void addMcQuestion(@RequestParam(value = "SurveyID") Integer ID,
                              @RequestParam(value = "Question") String questionText,
                              @RequestParam(value = "Options") String options)
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
    public void addNumRangeQuestion(@RequestParam(value = "SurveyID") Integer ID,
                                    @RequestParam(value = "Question") String questionText,
                                    @RequestParam(value = "Min") int min,
                                    @RequestParam(value = "max") int max)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        survey.addQuestion(new NumberRangeQuestion(questionText, min, max));
        surveyRepo.save(survey);
    }

    /**
     * Closes the survey. As designed, closing the survey allows for the compilation of all results to begin.
     * @return none
     * @param ID the ID to identify the survey to close
     * */
    @RequestMapping(value = "/closeSurvey", method = PUT)
    @ResponseBody
    public void closeSurvey(@RequestParam(value = "SurveyID") Integer ID)
    {
        Survey survey = surveyRepo.findBySurveyID(ID);
        surveyManager.compile(ID);
    }

    /**
     * A simple get that returns a JSON object of the survey given an ID.
     * @return none
     * @param ID the ID to identify the server
     * */
    @RequestMapping(value = "/getSurvey", method = GET)
    @ResponseBody
    public Survey getSurvey(@RequestParam(value = "ID") Integer ID)
    {
        //Save the survey given the specified ID.
        Survey survey = surveyRepo.findBySurveyID(ID);
        return survey;
    }

    /*TODO:TO ADD FORM AND ANSWERING ONCE THAT IS MERGED*/
}
