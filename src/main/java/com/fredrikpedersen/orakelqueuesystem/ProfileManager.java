package com.fredrikpedersen.orakelqueuesystem;

import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ProfileManager {

    private final List<String> activeProfiles;

    public ProfileManager(final Environment environment) {

        this.activeProfiles = Arrays.asList(environment.getActiveProfiles());
    }

    public boolean isProduction() {
        return activeProfiles.contains(Constants.PRODUCTION_PROFILE);
    }

    public boolean isDev() {
        return activeProfiles.contains(Constants.DEV_PROFILE);
    }

    public boolean isTest() {
        return activeProfiles.contains(Constants.TEST_PROFILE);
    }
}
