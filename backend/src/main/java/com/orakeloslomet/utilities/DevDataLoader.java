package com.orakeloslomet.utilities;

import com.orakeloslomet.persistance.models.authentication.Role;
import com.orakeloslomet.persistance.models.authentication.User;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.authentication.RoleRepository;
import com.orakeloslomet.persistance.repositories.authentication.UserRepository;
import com.orakeloslomet.persistance.repositories.queue.PlacementRepository;
import com.orakeloslomet.persistance.repositories.queue.QueueEntityRepository;
import com.orakeloslomet.persistance.repositories.queue.SubjectRepository;
import com.orakeloslomet.utilities.constants.Profiles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
public class DevDataLoader implements CommandLineRunner {

    private final QueueEntityRepository entityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final PlacementRepository placementRepository;

    @Override
    public void run(String... args) {

        log.info("Seeding data...");
        seedUsers();
        seedEntities();
        log.info("Seeding done!");

        printData();
    }

    private void seedEntities() {
        log.info("Seeding Queue Entities");
        final List<Subject> subjects = subjectRepository.findAll();
        final List<Placement> placements = placementRepository.findAll();

        if (subjects.size() == 0 || placements.size() == 0) {
            log.warn("Please seed subjects and placements before seeding queueentities!");
            return;
        }

        final List<QueueEntity> queueEntities = List.of(
                new QueueEntity("Fredrik", subjects.get(1), placements.get(0), "Look what I made ma!", 1),
                new QueueEntity("Ana-Maria", subjects.get(2), placements.get(1), "Notatboken min er større enn din", 2),
                new QueueEntity("Maria", subjects.get(3), placements.get(2), "Que passo?", 1),
                new QueueEntity("Vilde", subjects.get(1), placements.get(3), null, 1),
                new QueueEntity("Miina", subjects.get(2), placements.get(4), null, 2),
                new QueueEntity("Aleksander", subjects.get(3), placements.get(5), "2 kul 4 skul", 1)
        );

        entityRepository.saveAll(queueEntities);
        log.info("Done seeding Queue Entities!");
    }

    private void seedUsers() {
        log.info("Seeding Users");
        final List<Role> roleList = roleRepository.findAll();
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        final User testAdmin = new User("f", "f", encoder.encode("f"));
        testAdmin.setRoles(new HashSet<>(roleList));
        userRepository.save(testAdmin);

        log.info("Done seeding Users!");
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
