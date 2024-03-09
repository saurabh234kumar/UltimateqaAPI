package com.ultimate.qa.api.hooks;

import io.cucumber.java.After;

import static com.ultimate.qa.api.stepDefs.ProfileStepDef.profileService;

public class Hooks {

    @After
    public void stopProfileService() {
        if (profileService != null) {
            profileService.stopServer();
        }
    }

}
