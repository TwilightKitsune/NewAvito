package com.kitsune.NewAvito.controllers;

import com.kitsune.NewAvito.models.Announcement;
import com.kitsune.NewAvito.repo.AnnouncementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    private AnnouncementRepo announcementRepo;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterable<Announcement> announcements = announcementRepo.findAll();
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Всё");
        if(session.getAttribute("admin") != null) {
            model.addAttribute("isAdmin", true);
        }
        session.setMaxInactiveInterval(-1);

        return "Home";
    }

    @GetMapping("/Transport")
    public String transport(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Transport");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Транспорт");
        return "Home";
    }

    @GetMapping("/House")
    public String house(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("House");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Дом");
        return "Home";
    }

    @GetMapping("/Electronics")
    public String electronics(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Electronics");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Электроника");
        return "Home";
    }

    @GetMapping("/Work")
    public String work(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Work");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Работа");
        return "Home";
    }

    @GetMapping("/Hobby")
    public String hobby(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Hobby");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Хобби");
        return "Home";
    }

    @GetMapping("/Things")
    public String things(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Things");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Вещи");
        return "Home";
    }

    @GetMapping("/Animals")
    public String animals(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Animals");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Животные");
        return "Home";
    }

    @GetMapping("/Services")
    public String services(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Services");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Услуги");
        return "Home";
    }

    @GetMapping("/Other")
    public String other(Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAllByType("Other");
        model.addAttribute("announcements", announcements);
        model.addAttribute("Type", "Другое");
        return "Home";
    }

    @PostMapping("/")
    public String search(@RequestParam String search, Model model) {

        Iterable<Announcement> announcements = announcementRepo.findAll();
        List<Announcement> res = new ArrayList<>();
        if(search != null && !search.equals("")) {
            String[] words = search.split(" ");
            for (Announcement a : announcements) {
                for (int i = 0; i < words.length; i++) {
                    if (a.getTitle().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(a);
                    if(a.getTags() != null && a.getTags().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(a);
                }
            }
        }

        model.addAttribute("announcements", res);

        return "Home";
    }
}
