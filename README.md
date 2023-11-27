# SurveyMonkey
Overview: SYSC 4806 Project For Group 25 relating to the implementation of a Mini-SurveyMonkey. This system will allow for surveyors to make flexible surveys. This system will also allow for the compilation of results in various forms. Surveyors can request to add multiple choice questions, number range questions, and text questions. Forms are then opened for each survey where questions are answered. Once the survey is closed, results are compiled and aggregated. 

Build Status: As of 2023-11-27, the functionality of a working basic front end for the previously developed survey backend is working. This is done via the program being deployed to an Azure server which can be communicated with by utilizing HTTP requests. This includes the ability to create and answer surveys through the Azure website. The ability to traverse the website is done via buttons, and adding quesitons/answers can be done via text inputs. (NOTE: The aggregation has still yet to be fine tuned to display the different display formats)

Next Steps:
- Full UI with functioning interactible components.
- Different aggregate methods (Pie Chart, Histogram etc...) to be displayed.
- Enhance compilation algorithm.
- Increase Junit coverage. 

Code Style: Primarily coded in Java v.17. Was primarily coded utilizing key concepts from SYSC 4806 and SYSC 3110 to ensure modular design is efficient and that MVC is utilized when it comes to applying Spring to the service. 

Tests: JUnit4 tests were setup to test the HTTP requests using a mock MVC. 

How to Use:
/*Make a new Survey*/
PUT http://localhost:8080/createSurvey

/*Add a text question*/
PUT http://localhost:8080/addTextQuestion?surveyID=1&question=What is your name

/*Add an MC question*/
PUT http://localhost:8080/addMCQuestion?surveyID=1&question=Pick a colour&options=blue!green!red!purple

/*Add a number range question*/
PUT http://localhost:8080/addNumRangeQuestion?surveyID=1&question=Pick a number between 12 and 19&min=12&max=19

/*Make a form for the survey*/
PUT http://localhost:8080/startForm?surveyID=1

/*Answer MC question*/
PUT http://localhost:8080/answerMC?formID=1&questionID=2&answer=blue

/*Answer the text question*/
PUT http://localhost:8080/answerTextQuestion?formID=1&questionID=1&answer=Mohamed

/*Answer the number range question*/
PUT http://localhost:8080/answerNumRange?formID=1&questionID=3&answer=15

/*Close survey and compile*/
PUT http://localhost:8080/closeSurvey?surveyID=1

/*Returns the survey and displays all the forms*/
GET http://localhost:8080/getSurvey?surveyID=1

/*Get the form as is*/
GET http://localhost:8080/getForm?formID=1

Credits:

- Mohamed Kaddour -> Controller for main HTTP requests
- Bilal Chaudhry -> Testing and Azure setup
- Akshay Vashisht -> Templates/View + controller for views
- Kousha Motazedian -> Back-end design (Survey focus)
- Matthew Parker -> Back-end design (Answer focus)

Despite focusing on specific classes, each member contributed equally to the overall functionailty of the program.

License: Copyright 2023, SurveyMonkey for SYSC 4806 Project Carleton University, released with no intent of distribution.
