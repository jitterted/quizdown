# Quiz Down the Markdown-style Quiz App

## Ubiquitous Language

Defined by "Quiz Maker":

    * Quiz - 1 or more Questions
    
    * MC - Multiple-choice (type of question)
    
    * MC Choice - one of the options for an MC
    
    * MC Answer - correct choice for an MC (might be more than one)
    
    * MC Question - Text (description), 1 or more Choices, 1 or more Answers
    
    * FIB - Fill-in-the blank (type of question)
    
    * FIB Answer - (case-insensitive) text of the correct response
    
    * FIB Question - Text of the question, the Answer

Used by "Quiz Taker":

    * QuizTaker - Linked to User, contains list of QuizResponses

    * QuizResponse - Link to the Quiz Source, Contains the Taker's responses to the Quiz's Questions 

    * QuestionResponse(s) - Taker's choice (ChoiceResponse for MC/SC) or text (TextResponse for FIB) for each Question

    * Results: how did you do, i.e., for each question: correct, incorrect

        * Allow option to reveal the correct answer (if they don't want to try again)

## Features

### Minimum Quiz Needs

[ ] Learner tracking:

    [ ] Authentication: so we know who you are (User), find associated TestTaker

    [ ] Authorization: can you access quizzes at all (are you a customer)

    [ ] For each TestTaker: contains multiple Quizzes (see above)

    [ ] Profile page: view quizzes taken and results and quizzes available to take

### Future

[ ] Question type: Match

[ ] Single-choice

Test-Taking:

[ ] Move back/forward through Questions

[ ] Pre-reqs: can't take quiz D before taking quiz C


## Updates/Maintenance

[ ] De-lombokify the code

[ ] Update packages according to my "current" Hex Arch thinking
    [ ] Inbound/Outbound adapters
    [ ] Ports go under Application

[ ] Get rid of H2 usage, instead use Postgres + Testcontainers

[ ] Incorporate Flyway migration scripts for Postgres schema

[ ] Replace simple-forms.css/Bulma with TailwindCSS