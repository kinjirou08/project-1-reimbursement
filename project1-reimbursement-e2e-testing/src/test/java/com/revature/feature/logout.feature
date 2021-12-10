Feature: Logout

Scenario: Logout Successfully from Employee Homepage
	Given Im at the Employee Homepage
	And I clicked the Logout button
	Then I should be redirected to login page
	
Scenario: Logout Successfully from Finance Homepage
	Given Im at the Finance Manager Homepage
	And I clicked the Finance Manager Logout button
	Then I should be redirected to login page