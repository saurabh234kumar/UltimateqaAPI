Feature: Profile API
  As a user
  I want to be able to create a new profile and retrieve a user's profile

  Scenario: Create a new profile
    Given the profile service is running
    When I create a new profile with username "john_doe", dateOfBirth "1990-01-01T00:00:00Z", gender "MALE", and subscribedMarketing "true"
    Then the profile is created successfully with userId "123"

  Scenario: Retrieve user profile
    Given the profile service is running
    And a profile with userId "123" exists
    When I retrieve the profile for userId "123"
    Then the profile details should be returned with username "john_doe", dateOfBirth "1990-01-01T00:00:00Z", gender "MALE", subscribedMarketing "true", and hasSetupPreference "false"
