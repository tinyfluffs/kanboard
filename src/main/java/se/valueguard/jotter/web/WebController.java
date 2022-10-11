package se.valueguard.jotter.web;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.valueguard.jotter.jpa.Note;

@Controller
public class WebController {

    private final WebClient wc;

    WebController(Environment env) {
        String proto = env.getProperty("server.protocol", "http");
        String host = env.getProperty("server.host", "localhost");
        String port = env.getProperty("server.port", "8080");
        this.wc = WebClient.create(String.join("", new String[]{proto, "://", host, ":", port}));
    }

    @RequestMapping(value = "/")
    private String Index(Model model) {
        Flux<Note> notes = wc.get().uri("/api/note").retrieve().bodyToFlux(Note.class);
        model.addAttribute("notes", notes.collectList().block());
        return "index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private String Create(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);

        this.wc.post()
                .uri("/api/note")
                .body(Mono.just(note), Note.class)
                .retrieve()
                .bodyToMono(Note.class).block();

        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    private String Delete(@PathVariable Long id) {
        this.wc.delete()
                .uri(Strings.concat("/api/note/", id.toString()))
                .retrieve()
                .toEntity(Long.class)
                .block();

        return "redirect:/";
    }
}
