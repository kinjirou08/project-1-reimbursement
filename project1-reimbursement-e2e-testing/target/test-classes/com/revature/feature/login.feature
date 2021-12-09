Feature: Login

Scenario Outline: Successful Employee login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the employee homepage
	
	Examples:
		| username | password |
		| "employee01" | "password" |
		| "employee02" | "password" |

Scenario Outline: Successful Finance Manager login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the finance manager homepage
	
	Examples:
		| username | password |
		| "fm01" | "p4ssw0rd" |
		| "kinjirou08" | "p4ssw0rd" |	
		
		