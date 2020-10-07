package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue;

import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.SubjectService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping(URLs.SUBJECT_BASE_URL)
public class SubjectController {

    private SubjectService subjectService;
    private final Bucket bucket;

    public SubjectController(final SubjectService subjectService) {

        this.subjectService = subjectService;

        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    public ResponseEntity<ArrayList<String>> getSubjects() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(subjectService.getSubjectList());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
