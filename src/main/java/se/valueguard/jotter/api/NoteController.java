package se.valueguard.jotter.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.valueguard.jotter.jpa.Note;
import se.valueguard.jotter.jpa.NoteRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    private final NoteRepository repo;

    @GetMapping(value = "/api/note")
    public Flux<Note> list() {
        return Flux.fromIterable(repo.findAll());
    }

    @PostMapping(value = "/api/note")
    public Mono<Note> create(@RequestBody Note note) {
        try {
            note = repo.save(note);
        } catch (IllegalArgumentException ex) {
            log.warn("", ex);
            return Mono.error(ex);
        }
        return Mono.just(note);
    }

    @DeleteMapping(value = "/api/note/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Optional<Note> opNote = repo.findById(id);
        opNote.ifPresent(repo::delete);
        return ResponseEntity.ok(id);
    }
}
