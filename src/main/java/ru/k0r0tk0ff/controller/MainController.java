package ru.k0r0tk0ff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.k0r0tk0ff.infrastructure.service.KafkaService;

@Controller
public class MainController {

    private KafkaService service;

    @Autowired
    public MainController(@Qualifier("KafkaService") KafkaService service) {
        this.service = service;
    }

    // http://127.0.0.1:8080/main
    @GetMapping("/main")
    public String mainWithParam(Model model) {
        return "mainPage"; //view
    }

    @GetMapping("/getMessages")
    public String getMessages(Model model) {

        model.addAttribute("messages", service.getMessages());

        return "mainPage"; //view
    }
}
