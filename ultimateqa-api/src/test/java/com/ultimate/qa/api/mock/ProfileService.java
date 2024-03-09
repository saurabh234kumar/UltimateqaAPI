package com.ultimate.qa.api.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

public class ProfileService {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private final WireMockServer wireMockServer;

    public ProfileService() {
        this.wireMockServer = new WireMockServer(PORT);
    }

    public void startServer() {
        wireMockServer.start();
        RestAssured.baseURI = "http://" + HOST + ":" + PORT;
    }

    public void stopServer() {
        wireMockServer.stop();
    }

    public void stubCreateProfile(int userId) {
        wireMockServer.stubFor(WireMock.post("/v1/profile")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\": " + userId + "}")));
    }

    public void stubRetrieveProfile(int userId, String username, String dateOfBirth, String gender, boolean subscribedMarketing, boolean hasSetupPreference) {
        wireMockServer.stubFor(WireMock.get("/v1/profile/" + userId)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\": " + userId + ", \"username\": \"" + username + "\", \"dateOfBirth\": \"" + dateOfBirth + "\", \"gender\": \"" + gender + "\", \"subscribedMarketing\": " + subscribedMarketing + ", \"hasSetupPreference\": " + hasSetupPreference + "}")));
    }

    public Response createNewProfile(String username, String dateOfBirth, String gender, boolean subscribedMarketing) {
        JSONObject request = new JSONObject();
        request.put("username", username);
        request.put("dateOfBirth", dateOfBirth);
        request.put("gender", gender);
        request.put("subscribedMarketing", subscribedMarketing);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request.toString())
                .post("/v1/profile");
    }

    public Response retrieveUserProfile(int userId) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/v1/profile/" + userId);
    }
}
