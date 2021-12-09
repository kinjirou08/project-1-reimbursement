Feature: Add New Reimbursement

Scenario: Adding A Reimbursement Successfully
	Given I am the Employee homepage
	When I hover my mouse over the Welcome Employee!
	And I click the Add Reimbursement
	And I choose 3 in the dropdown box
	And I typed "Business Travel Expense" in the description input box
	And I typed "100.50" in the amount input box
	And I uploaded a file
	And I click the submit button
	Then I should see the employee homepage again