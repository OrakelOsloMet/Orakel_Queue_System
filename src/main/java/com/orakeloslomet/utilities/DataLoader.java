package com.orakeloslomet.utilities;

import com.orakeloslomet.persistance.models.authentication.ERole;
import com.orakeloslomet.persistance.models.authentication.Role;
import com.orakeloslomet.persistance.models.authentication.User;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.QueueEntityRepository;
import com.orakeloslomet.persistance.repositories.SubjectRepository;
import com.orakeloslomet.persistance.repositories.authentication.RoleRepository;
import com.orakeloslomet.persistance.repositories.authentication.UserRepository;
import com.orakeloslomet.utilities.constants.Profiles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@Component
@AllArgsConstructor
@Profile({Profiles.DEV, Profiles.TEST})
public class DataLoader implements CommandLineRunner {

    private final QueueEntityRepository entityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public void run(String... args) {
        log.info("Seeding data...");
        List<Subject> allSubjects = seedSubjects();
        seedEntities(allSubjects);
        seedRoles();
        seedUsers();
        log.info("Seeding done!");
    }

    private void seedEntities(final List<Subject> allSubjects) {
        log.info("Seeding Queue Entities");
        QueueEntity queueEntity1 = new QueueEntity("Fredrik", allSubjects.get(1).getName(), "1", "Jeg er kul 8)", 1, true);
        QueueEntity queueEntity2 = new QueueEntity("Ana-Maria", allSubjects.get(2).getName(), "2", "Jeg er kul 8)", 2, false);
        QueueEntity queueEntity3 = new QueueEntity("Maria", allSubjects.get(3).getName(), "2", "Jeg er kul 8)",1, false);
        QueueEntity queueEntity4 = new QueueEntity("Thomas", allSubjects.get(1).getName(), "3", "Jeg er kul 8)",1, true);
        QueueEntity queueEntity5 = new QueueEntity("Joakim", allSubjects.get(2).getName(), "4", "Jeg er kul 8)",2, false);
        QueueEntity queueEntity6 = new QueueEntity("Chris", allSubjects.get(3).getName(), "5", "Jeg er kul 8)", 1, false);
        queueEntity4.markAsDone();
        queueEntity5.markAsDone();
        queueEntity6.markAsDone();

        entityRepository.save(queueEntity1);
        entityRepository.save(queueEntity2);
        entityRepository.save(queueEntity3);
        entityRepository.save(queueEntity4);
        entityRepository.save(queueEntity5);
        entityRepository.save(queueEntity6);
        log.info("Done seeding Queue Entities!");
    }

    private void seedUsers() {
        log.info("Seeding Users");
        List<Role> roleList = roleRepository.findAll();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User testAdmin = new User("f", "f", encoder.encode("f"));
        testAdmin.setRoles(new HashSet<>(roleList));
        userRepository.save(testAdmin);

        log.info("Done seeding Users!");
    }

    private List<Subject> seedSubjects() {
        log.info("Seeding Semesters");
        ArrayList<Subject> allSubjects = new ArrayList<>();
        ArrayList<String> autumnSubjects = new ArrayList<>(Arrays.asList("Programmering", "Diskret Matte",
                "Web Utvikling", "Prototyping", "Algoritmer og Datastrukturer", "Matte 2000", "Sytemutvikling", "MMI",
                "Web Applikasjoner", "Apputvikling"));

        ArrayList<String> springSubjects = new ArrayList<>(Arrays.asList("Databaser", "Webprogrammering",
                "Internet of Things", "Matte 1000", "Visualisering", "Testing av Programvare", "Fysikk og Kjemi",
                "Datanettverk og Skytjenester", "Operativsystemer"));

        autumnSubjects.forEach(subject -> {
            Subject subjectObject = new Subject(subject, ESemester.AUTUMN);
            allSubjects.add(subjectObject);
            subjectRepository.save(subjectObject);
        });

        springSubjects.forEach(subject -> {
            Subject subjectObject = new Subject(subject, ESemester.SPRING);
            allSubjects.add(subjectObject);
            subjectRepository.save(subjectObject);
        });

        return allSubjects;
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
