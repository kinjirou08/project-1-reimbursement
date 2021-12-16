# About the Project
- To develop an Expense Reimbursement System where there are two main Users: A Finance Manager and an Employee.
    1. A Finance Manager should be able to **Login**, **Filter a Reimbursement By Status (Pending, Approved, Rejected)**, **View All Reimbursement made my the Employee**, and **Approve or Deny a reimbursement**.
    2. An Employee should be able to **Login**, **Submit a new Reimbursement**, and be able to **Upload an image of the receipt** for the new Reimbursement.

# Technical Requirements
- Postgres as database
- Javalin Framework
- Use of HTML, CSS, and JavaScript to develop front-end view
- Fetch API to communicate between HTTP Requests and HTTP Response
- Passwords must be hashed and secured in the database
- Logback
- JUnit 5 (Jupiter) for Unit Testing, Service Layer should achieve 80% - 90% code coverage.
- Selenium test for all major functionality **(logging in as a user, logging out, submitting a reimbursement, logging in as a finance manager, approving a reimbursement, denying a reimbursement)** should be accomplished.

# Technologies Used
- DBeaver version 21.1.5
    - PostgreSQL 10 version 42.2.18
- Javalin version 4.0.1
- Logback version 1.2.6
- Tika version 1.18 (For getting file type)
- SonarCloud.io
    - JaCoCo version 0.8.7 (For Code Coverage)
- Bulma version 0.9.3


### For Testing
- JUnit 5 (Jupiter) API version 5.8.1
- Mockito version 4.0.0
- Selenium version 4.1.0
- Cucumber version 7.1.0

# Features
1. As an Employee:
    - Successfully logging in
    - Successfully logging out
    - Adding a Reimbursement is functional
    - Viewing of Reimbursement History is functional
    - Successfully uploading an Image for receipt
    - Viewing of an Image after submitting a new Reimbursement

2. As a Finance Manager:
    - Successfully logging in
    - Successfully logging out
    - Approving or Denying a Request is functional
    - Filtering a Reimbursement Request by Status
        - Pending Filter implemented
        - Approved Filter implemented
        - Rejected Filter implemented
        - All Request Status implemented

3. Added feature outside of MVP (Minimum Viable Product)
    - Adding a Reimbursement in Finance Manager homepage
    - Creation of new Account/User (Sign up page)


