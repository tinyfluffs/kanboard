package se.valueguard.kanboard.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import se.valueguard.kanboard.jpa.Project;

@Controller
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    private final WebClient wc;

    WebController(Environment env) {
        String proto = env.getProperty("server.protocol", "http");
        String host = env.getProperty("server.host", "localhost");
        String port = env.getProperty("server.port", "8080");
        this.wc = WebClient.create(String.join("", new String[]{proto, "://", host, ":", port}));
    }

    @RequestMapping(value = "/")
    private String Index(Model model) {
        model.addAttribute("title", "Kanboard");
        Flux<Project> projects = wc.get().uri("/api/project").retrieve().bodyToFlux(Project.class);
        model.addAttribute("projects", projects.collectList().block());
        return "index";
    }
}
