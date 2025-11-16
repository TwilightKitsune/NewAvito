package com.kitsune.NewAvito.controllers;

import com.kitsune.NewAvito.models.Admin;
import com.kitsune.NewAvito.models.Announcement;
import com.kitsune.NewAvito.models.Rule;
import com.kitsune.NewAvito.models.User;
import com.kitsune.NewAvito.repo.AdminRepo;
import com.kitsune.NewAvito.repo.RuleRepo;
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
import java.util.Date;
import java.util.List;

@Controller
public class RuleController {

    @Autowired
    private RuleRepo ruleRepo;

    @Autowired
    AdminRepo adminRepo;

    @GetMapping("/AllRule")
    public String allRuleGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Iterable<Rule> rules = ruleRepo.findAll();
        model.addAttribute("rules", rules);
        return "AllRule";
    }

    @PostMapping("/AllRule")
    public String allRulePost(@RequestParam String search, Model model) {
        Iterable<Rule> rules = ruleRepo.findAll();
        List<Rule> res = new ArrayList<>();
        if(search != null && !search.equals("")) {
            String[] words = search.split(" ");
            for (Rule r : rules) {
                for (int i = 0; i < words.length; i++) {
                    if (r.getId().toString().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(r);
                    if(r.getDate().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(r);
                    if(r.getIdAdmin().toString().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(r);
                    if(r.getType().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(r);
                    if(r.getText().toLowerCase().contains(words[i].toLowerCase()))
                        res.add(r);
                }
            }
        }
        else return "redirect:AllRule";
        model.addAttribute("rules", res);
        return "AllRule";
    }

    @GetMapping("/AddRule")
    public String addRuleGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        return "AddRule";
    }

    @PostMapping("/AddRule")
    public String addRulePost(@RequestParam String text, @RequestParam String type, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        Admin admin = adminRepo.findAllByLogin(adminLogin).get(0);
        Rule rule = new Rule();
        rule.setDate(new Date());
        rule.setText(text);
        rule.setType(type);
        rule.setIdAdmin(admin.getId());
        ruleRepo.save(rule);
        return "redirect:AllRule";
    }

    @GetMapping("/EditRule/{id}")
    public String editRuleGet(@PathVariable(value = "id") Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Rule rule = ruleRepo.findById(id).get();
        model.addAttribute("text", rule.getText());
        return "EditRule";
    }

    @PostMapping("/EditRule/{id}")
    public String editRulePost(@RequestParam String text, @RequestParam String type, @PathVariable(value = "id") Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Rule rule = ruleRepo.findById(id).get();
        rule.setType(type);
        rule.setText(text);
        ruleRepo.save(rule);
        return "redirect:/AllRule";
    }

    @GetMapping("/DeleteRule/{id}")
    public String deleteRuleGet(@PathVariable(value = "id") Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Rule rule = ruleRepo.findById(id).get();
        model.addAttribute("text", rule.getText());
        return "DeleteRule";
    }

    @PostMapping("/DeleteRule/{id}")
    public String deleteRulePost(@RequestParam String password, @PathVariable(value = "id") Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        Admin admin = adminRepo.findAllByLogin(adminLogin).get(0);
        if(!admin.getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный пароль");
            return "DeleteAnnouncementAdmin";
        }
        ruleRepo.deleteById(id);
        return "redirect:/AllRule";
    }
}
