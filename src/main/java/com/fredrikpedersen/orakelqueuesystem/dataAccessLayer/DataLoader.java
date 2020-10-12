package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication.ERole;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication.Role;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication.User;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESubject;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.authentication.RoleRepository;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.authentication.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
@Profile({"dev", "test"})
public class DataLoader implements CommandLineRunner {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.mail}")
    private String adminMail;

    private final QueueEntityRepository entityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataLoader(final QueueEntityRepository queueEntityRepository, final UserRepository userRepository, final RoleRepository roleRepository) {
        this.entityRepository = queueEntityRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Seeding data...");
        seedEntities();
        seedRoles();
        seedUsers();
        log.info("Seeding done!");
    }

    private void seedEntities() {
        log.info("Seeding Queue Entities");
        QueueEntity queueEntity1 = new QueueEntity("Fredrik", ESubject.PROGRAMMING, 1, true);
        QueueEntity queueEntity2 = new QueueEntity("Ana-Maria", ESubject.DISCRETE_MATHEMATICS, 2, false);
        QueueEntity queueEntity3 = new QueueEntity("Maria", ESubject.WEB_DEVELOPMENT, 1, false);

        entityRepository.save(queueEntity1);
        entityRepository.save(queueEntity2);
        entityRepository.save(queueEntity3);
        log.info("Done seeding Queue Entities!");
    }

    private void seedUsers() {
        log.info("Seeding Users");
        List<Role> roleList = roleRepository.findAll();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User testAdmin = new User(adminUsername, adminMail, encoder.encode(adminPassword));
        testAdmin.setRoles(new HashSet<>(roleList));
        userRepository.save(testAdmin);

        log.info("Done seeding Users!");
    }


    private void seedRoles() {
        log.info("Seeding Roles");
        Role user = new Role();
        user.setName(ERole.ROLE_USER);

        Role admin = new Role();
        admin.setName(ERole.ROLE_ADMIN);

        roleRepository.save(user);
        roleRepository.save(admin);
        log.info("Done seeding roles!");
    }
}
