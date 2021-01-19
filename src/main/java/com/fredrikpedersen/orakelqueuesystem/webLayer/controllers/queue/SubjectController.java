package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Subject;
import com.fredrikpedersen.orakelqueuesystem.dto.SubjectDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.SubjectService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@CrossOrigin
@RestController
@RequestMapping(URLs.SUBJECT_BASE_URL)
public class SubjectController {

    private final SubjectService subjectService;
    private final Bucket bucket;

    public SubjectController(final SubjectService subjectService) {

        this.subjectService = subjectService;

        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(subjectService.findAll());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("current")
    public ResponseEntity<List<SubjectDTO>> getSubjectsCurrentSemester() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(subjectService.findSubjectsCurrentSemester());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
