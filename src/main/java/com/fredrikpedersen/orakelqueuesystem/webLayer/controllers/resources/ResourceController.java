package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.resources;

import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(URLs.RESOURCES_BASE_URL)
public class ResourceController {

    @GetMapping(value = URLs.USER_GUIDE_URL, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getUserGuideAndDocumentation() throws IOException {

        ClassPathResource resource = new ClassPathResource("/documentation/UserGuideAndDocumentation.pdf");

        return ResponseEntity
                .ok()
                .contentLength(resource.contentLength())
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
