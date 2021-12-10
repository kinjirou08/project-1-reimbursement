Feature: Accept/Reject Reimbursement

Scenario: Successfully Accepting a Reimbursement
	Given I am at the Finance Manager HomePage
	When I hover my mouse over the Welcome Manager!
	And I click the Approve or Reject Reimbursement
	And I input 5 in the id input box
	And I click the search button
	And I chose "Rejected" in the dropdown box
	And I click the fm submit button
	Then I should see the finance manager again