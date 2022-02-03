package com.orakeloslomet.utilities;

import com.orakeloslomet.persistance.models.authentication.ERole;
import com.orakeloslomet.persistance.models.authentication.Role;
import com.orakeloslomet.persistance.models.authentication.User;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.PlacementRepository;
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
import java.util.stream.Collectors;

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
    private final PlacementRepository placementRepository;

    @Override
    public void run(String... args) {
        log.info("Seeding data...");
        seedSubjects();
        seedRoles();
        seedUsers();
        seedPlacements();
        seedEntities();
        log.info("Seeding done!");

        printData();

    }

    private void seedEntities() {
        log.info("Seeding Queue Entities");
        final List<String> subjects = subjectRepository.findAll().stream().map(Subject::getName).collect(Collectors.toList());
        final List<Placement> placements = placementRepository.findAll();

        if (subjects.size() == 0 || placements.size() == 0) {
            log.warn("Please seed subjects and placements before seeding queueentities!");
            return;
        }

        final List<QueueEntity> queueEntities = List.of(
                new QueueEntity("Fredrik", subjects.get(1), placements.get(0), "Look what I made ma!", 1, true),
                new QueueEntity("Ana-Maria", subjects.get(2), placements.get(1), "Notatboken min er større enn din", 2, false),
                new QueueEntity("Maria", subjects.get(3), placements.get(2), "Que passo?", 1, false),
                new QueueEntity("Vilde", subjects.get(1), placements.get(3), null, 1, true),
                new QueueEntity("Miina", subjects.get(2), placements.get(4), null, 2, false),
                new QueueEntity("Aleksander", subjects.get(3), placements.get(5), "2 kul 4 skul", 1, false)
        );

        entityRepository.saveAll(queueEntities);
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

    private void seedSubjects() {
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

    private void seedPlacements() {
        log.info("Seeding Placements");
        final int datatorgSeats = 26;
        final int groupRooms = 5;

        final List<Placement> placements = new ArrayList<>();

        for (int i = 1; i <= datatorgSeats; i++) {
            placements.add(new Placement("Datatorget", i));
        }

        for (int i = 1; i <= groupRooms; i++) {
            placements.add(new Placement("Grupperom", i));
        }

        placementRepository.saveAll(placements);
        log.info("Done seeding placements!");
    }

    private void printData() {
        log.info("#----- Queue Entities ------#");
        entityRepository.findAll().forEach(queueEntity -> log.info(String.valueOf(queueEntity)));

        log.info("#----- Subjects ------#");
        subjectRepository.findAll().forEach(subject -> log.info(String.valueOf(subject)));

        log.info("#----- Placements ------#");
        placementRepository.findAll().forEach(placement -> log.info(String.valueOf(placement)));
    }
}
