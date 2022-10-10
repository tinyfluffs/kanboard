package se.valueguard.kanboard.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    @RequestMapping(value = "/")
    private String Index(Model model) {
        model.addAttribute("title", "Kanboard");
        return "index";
    }
}
