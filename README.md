# SurveyMonkey
Overview: SYSC 4806 Project For Group 25 relating to the implementation of a Mini-SurveyMonkey. This system will allow for surveyors to make flexible surveys. This system will also allow for the compilation of results in various forms. Surveyors can request to add multiple choice questions, number range questions, and text questions. Forms are then opened for each survey where questions are answered. Once the survey is closed, results are compiled and aggregated. 

Build Status: As of 2023-12-08, a majority of the functionality is in place for the front-end user interface alongside all required endpoints needed. A surveyor is now able to make full surveys and post them. At that point, other users are then able to answer the survey questions by opening forms. When a surveyor decides they no longer want to continue accepting answers, they're able to close the survey and get .png results for the aggregates.

Code Style: Primarily coded in Java v.17. Was primarily coded utilizing key concepts from SYSC 4806 and SYSC 3110 to ensure modular design is efficient and that MVC is utilized when it comes to applying Spring to the service. 

Tests: JUnit4 tests were setup to test the HTTP requests using a mock MVC. 

How to Use:

- Visit the deployed website at https://surveymonkeyg25.azurewebsites.net/
- To create a brand new survey, click the "Create Survey" button. At this point you will be assigned a survey ID (Remember this!)
- To view, answer, and edit surveys, click the View Surveys button. By specifying the assigned surveyID the user is able to add questions to that survey.
- The user can close their survey by clicking the "Close A Survey" button and specifying the ID.
- Finally, the surveyor can view the aggregates of the survey (the results).

Design:

Relational Database Schema:
![RelationalDBSchema-M3.png](Diagrams%2FRelationalDBSchema-M3.png)

UML Diagram:
![UML-M3.png](Diagrams%2FUML-M3.png)

Credits:

- Mohamed Kaddour -> Controller for main HTTP requests + Compiler + Histogram Aggregate
- Bilal Chaudhry -> Testing and Azure setup + TextAnswerList + Front-End
- Akshay Vashisht -> Templates/View + controller for views
- Kousha Motazedian -> Back-end design (Survey focus) + PieChart Aggregate
- Matthew Parker -> Back-end design (Answer focus) + Front-end design + Website navigation

Despite focusing on specific classes, templates and functionalities, each member contributed equally to the overall functionality of the program.

License: Copyright 2023, SurveyMonkey for SYSC 4806 Project Carleton University, released with no intent of distribution.
