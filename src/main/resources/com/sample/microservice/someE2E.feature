Feature: some name

  @E2E
  Scenario: Happt path -some description
  Given I have a token for the user
  And the user is initiaiized
  When I call the api
  Then the response is 200