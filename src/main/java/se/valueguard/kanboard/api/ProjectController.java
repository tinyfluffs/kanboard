package se.valueguard.kanboard.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.valueguard.kanboard.jpa.Project;
import se.valueguard.kanboard.jpa.ProjectRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectRepository repo;

    @GetMapping(value = "/api/project")
    public ResponseEntity<List<Project>> list() {
        return ResponseEntity.ok(
                StreamSupport.stream(repo.findAll().spliterator(), false).toList()
        );
    }

    @PostMapping(value = "/api/project")
    public ResponseEntity<?> create(@RequestBody Project project) {
        try {
            project = repo.save(project);
        } catch (IllegalArgumentException ex) {
            log.warn("", ex);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(project);
    }
}
