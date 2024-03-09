package com.ultimate.qa.api.stepDefs;

import com.ultimate.qa.api.mock.ProfileService;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class ProfileStepDef {
    public static ProfileService profileService;
    private Response createProfileResponse;
    private Response retrieveProfileResponse;

    @Given("the profile service is running")
    public void startProfileService() {
        profileService = new ProfileService();
        profileService.startServer();
    }

    @When("I create a new profile with username {string}, dateOfBirth {string}, gender {string}, and subscribedMarketing {string}")
    public void createNewProfile(String username, String dateOfBirth, String gender, String subscribedMarketing) {
        profileService.stubCreateProfile(123);
        createProfileResponse = profileService.createNewProfile(username, dateOfBirth, gender, Boolean.parseBoolean(subscribedMarketing));
    }

    @Then("the profile is created successfully with userId {string}")
    public void verifyProfileCreated(String userId) {
        createProfileResponse.then().statusCode(200);
        Assert.assertEquals(userId, createProfileResponse.jsonPath().getString("userId"));
    }

    @Given("a profile with userId {string} exists")
    public void stubProfile(String userId) {
        profileService.stubRetrieveProfile(Integer.parseInt(userId), "john_doe", "1990-01-01T00:00:00Z", "MALE", true, false);
    }

    @When("I retrieve the profile for userId {string}")
    public void retrieveUserProfile(String userId) {
        retrieveProfileResponse = profileService.retrieveUserProfile(Integer.parseInt(userId));
    }

    @Then("the profile details should be returned with username {string}, dateOfBirth {string}, gender {string}, subscribedMarketing {string}, and hasSetupPreference {string}")
    public void verifyUserProfile(String username, String dateOfBirth, String gender, String subscribedMarketing, String hasSetupPreference) {
        retrieveProfileResponse.then().statusCode(200);
        Assert.assertEquals(username, retrieveProfileResponse.jsonPath().getString("username"));
        Assert.assertEquals(dateOfBirth, retrieveProfileResponse.jsonPath().getString("dateOfBirth"));
        Assert.assertEquals(gender, retrieveProfileResponse.jsonPath().getString("gender"));
        Assert.assertEquals(subscribedMarketing, retrieveProfileResponse.jsonPath().getString("subscribedMarketing"));
        Assert.assertEquals(hasSetupPreference, retrieveProfileResponse.jsonPath().getString("hasSetupPreference"));
    }
}
