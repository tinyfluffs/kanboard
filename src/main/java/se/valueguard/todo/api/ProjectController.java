package se.valueguard.todo.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.valueguard.todo.jpa.Project;
import se.valueguard.todo.jpa.ProjectRepository;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectRepository repo;

    @GetMapping(value = "/api/project")
    public Flux<Project> list() {
        return Flux.fromIterable(repo.findAll());
    }

    @PostMapping(value = "/api/project")
    public Mono<?> create(@RequestBody Project project) {
        try {
            project = repo.save(project);
        } catch (IllegalArgumentException ex) {
            log.warn("", ex);
            return Mono.error(ex);
        }
        return Mono.just(project);
    }
}
