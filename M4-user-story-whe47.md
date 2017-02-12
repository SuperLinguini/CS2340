##Title: As a local environmentalist, I want to be able to submit a water report so that I can contribute to saving the environment.  

##Tasks:
- [ ] Create a welcome screen
- [ ] Create a login screen
- [ ] Create a registration screen
- [ ] Create the main screen
- [ ] Create controller classes.
- [ ] Implement the transition between welcome screen and login/registration screen.
- [ ] Implement login and registration functionalities.  

##Acceptance Scenarios:

- [ ] GIVEN a user opens the application and sees the welcome screen, WHEN the user taps the “Login” or “Register” button, THEN the corresponding screen shows up.
- [ ] GIVEN a user is in the login/registration screen, WHEN the user taps the “Cancel” button, THEN the system returns to the welcome screen.
- [ ] GIVEN a new user has typed in a valid username and a valid password in the registration screen, WHEN the user taps the “Finish” button, THEN a new account is created and the user is automatically logged in.
- [ ] GIVEN a new user has typed in an invalid username in the registration screen, WHEN the user taps the password field, THEN the system displays an error message.
- [ ] GIVEN a new user has typed in a valid username but an invalid password in the registration screen, WHEN the user taps the "Finish" button, THEN the system displays an error message.
- [ ] GIVEN a registered user has typed in an existing username and the correct password, WHEN the user taps the “Log In” button, THEN the main page shows up.
- [ ] GIVEN a registered user has typed in an existing username and an incorrect password, WHEN the user taps the “Log In” button, THEN the system displays an error message.
- [ ] GIVEN a registered user has typed in the username incorrectly, WHEN the user taps the password field, THEN the system displays an error message.
- [ ] GIVEN a user is logged into the system, WHEN the user taps the “Back” button, THEN the user is logged out and the system displays the welcome screen.  
 
##Done Done Criteria:
    * All the acceptance scenarios are tested.
