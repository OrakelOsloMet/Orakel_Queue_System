package com.orakeloslomet.web.controllers.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.services.queue.PlacementService;
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

import static java.util.Objects.requireNonNull;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:48
 */

@CrossOrigin
@RestController
@RequestMapping(URLs.PLACEMENT_BASE_URL)
public class PlacementController {

    private final PlacementService placementService;
    private final Bucket bucket;

    public PlacementController(final PlacementService placementService) {
        this.placementService = requireNonNull(placementService);

        final Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<PlacementDTO>> getPlacements() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(placementService.findAll());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlacementDTO> postPlacement(@RequestBody final PlacementDTO placementDTO) {
        if (bucket.tryConsume(1)) {
            if (FieldValidator.validateForNulls(placementDTO)) {
                return ResponseEntity.ok(placementService.save(placementDTO));
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PutMapping("edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlacementDTO> editPlacement(@RequestBody final PlacementDTO placementDTO, @PathVariable Long id) {
        if (bucket.tryConsume(1)) {
            if (FieldValidator.validateForNulls(placementDTO)) {
                return ResponseEntity.ok(placementService.update(placementDTO, id));
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePlacement(@PathVariable final Long id) {
        placementService.deleteById(id);
    }
}
