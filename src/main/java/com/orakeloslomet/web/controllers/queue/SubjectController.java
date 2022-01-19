package com.orakeloslomet.web.controllers.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.services.queue.SubjectService;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.validation.FieldValidator;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDTO> postSubject(@RequestBody final SubjectDTO subjectDTO) {
        if (bucket.tryConsume(1)) {
            if (FieldValidator.validateForNulls(subjectDTO)) {
                return ResponseEntity.ok(subjectService.createNew(subjectDTO));
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PutMapping("edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDTO> editSubject(@RequestBody final SubjectDTO subjectDTO, @PathVariable Long id) {
        if (bucket.tryConsume(1)) {
            if (FieldValidator.validateForNulls(subjectDTO)) {
                return ResponseEntity.ok(subjectService.edit(subjectDTO, id));
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSubject(@PathVariable final Long id) {subjectService.deleteById(id);}

}
