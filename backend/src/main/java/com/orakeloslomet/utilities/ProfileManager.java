package com.orakeloslomet.utilities;

import com.orakeloslomet.utilities.constants.Profiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@Component
public class ProfileManager {

    private final List<String> activeProfiles;

    public ProfileManager(final Environment environment) {

        this.activeProfiles = Arrays.asList(environment.getActiveProfiles());
    }

    public boolean isProduction() {
        return activeProfiles.contains(Profiles.PROD);
    }

    public boolean isDev() {
        return activeProfiles.contains(Profiles.DEV);
    }

    public boolean isTest() {
        return activeProfiles.contains(Profiles.TEST);
    }
}
