package org.example;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
public class UppercaseTaskController {

    private static final ResponseEntity<UppercaseTask> NOT_FOUND = ResponseEntity.notFound()
            .build();

    private final UppercaseTaskService uppercaseTaskService;

    private final Method readTaskMethod;

    public UppercaseTaskController(final UppercaseTaskService uppercaseTaskService) {
        this.uppercaseTaskService = uppercaseTaskService;

        try {
            this.readTaskMethod = UppercaseTaskController.class.getMethod("read", String.class);
        } catch (final NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<Void> create(@RequestBody final String text) {
        final String id = this.uppercaseTaskService.create(text);
        final var uri = WebMvcLinkBuilder.linkTo(this.readTaskMethod, id)
                .toUri();

        return ResponseEntity.accepted()
                .location(uri)
                .build();
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<UppercaseTask> read(@PathVariable("id") final String id) {
        return this.uppercaseTaskService.read(id)
                .map(ResponseEntity::ok)
                .orElse(NOT_FOUND);
    }
}
