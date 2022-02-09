package com.orakeloslomet.utilities;

import com.orakeloslomet.persistance.models.authentication.ERole;
import com.orakeloslomet.persistance.models.authentication.Role;
import com.orakeloslomet.persistance.models.authentication.User;
import com.orakeloslomet.persistance.repositories.authentication.RoleRepository;
import com.orakeloslomet.persistance.repositories.authentication.UserRepository;
import com.orakeloslomet.utilities.constants.Profiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author Fredrik Pedersen
 * @since 07/02/2022 at 17:04
 */

@Slf4j
@Component
@Profile({Profiles.PROD, Profiles.SYSTEM_DEV})
public class ProdDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final String adminPassword;
    private final String adminUsername;
    private final String adminEmail;

    public ProdDataLoader(final UserRepository userRepository, final RoleRepository roleRepository,
                          @Value("${admin.password}") final String adminPassword,
                          @Value("${admin.username}") final String adminUsername,
                          @Value("${admin.mail}") final String adminEmail) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.adminPassword = adminPassword;
        this.adminUsername = adminUsername;
        this.adminEmail = adminEmail;
    }


    @Override
    public void run(String... args) throws Exception {
        seedAdminIfNotPresent();
    }

    @Transactional
    protected void seedAdminIfNotPresent() {
        log.info("Checking for existing admin user");
        final Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();
        final Optional<User> adminUser = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(adminRole))
                .findFirst();

        if (adminUser.isEmpty()) {
            log.info("No existing admin found, creating new with default credentials from server");

            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final User defaultAdmin = new User(adminUsername, adminEmail, encoder.encode(adminPassword));
            final List<Role> roles = roleRepository.findAll();
            defaultAdmin.setRoles(new HashSet<>(roles));

            userRepository.save(defaultAdmin);
            log.info("Admin created");
            return;
        }

        log.info("Admin already exists");
    }
}