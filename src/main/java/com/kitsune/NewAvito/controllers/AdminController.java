package com.kitsune.NewAvito.controllers;

import com.kitsune.NewAvito.models.Admin;
import com.kitsune.NewAvito.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminRepo adminRepo;

    @GetMapping("/Admin")
    public String admin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        model.addAttribute("login", admin);
        return "Admin";
    }

    @GetMapping("/LoginAdmin")
    public String loginGet(Model model) {
        return "LoginAdmin";
    }

    @PostMapping("/LoginAdmin")
    public String loginAdmin(@RequestParam String login, @RequestParam String password, Model model, HttpServletRequest request) {
        List<Admin> listAdmins = adminRepo.findAllByLogin(login);
        if(listAdmins.isEmpty() || !listAdmins.get(0).getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный логин или пароль");
            return "LoginAdmin";
        }
        HttpSession session = request.getSession();
        session.setAttribute("admin", login);
        return "redirect:Admin";
    }

    @GetMapping("/ExitAdmin")
    public String exitAdmin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return "redirect:/";
    }

    @GetMapping("/DeleteProfileAdmin")
    public String deleteProfileAdminGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        return "DeleteProfileAdmin";
    }

    @PostMapping("/DeleteProfileAdmin")
    public String deleteProfileAdminPost(@RequestParam String login, @RequestParam String password, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginAdmin = (String) session.getAttribute("admin");
        Admin admin = adminRepo.findAllByLogin(loginAdmin).get(0);
        if(!login.equals(admin.getLogin()) || !admin.getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный логин или пароль");
            return "DeleteProfile";
        }
        adminRepo.deleteById(admin.getId());
        return "redirect:ExitAdmin";
    }

    @GetMapping("/AddAdmin")
    public String addAdminGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        return "AddAdmin";
    }

    @PostMapping("/AddAdmin")
    public String addAdminPost(@RequestParam String login, @RequestParam String password, Model model, HttpServletRequest request) {
        List<Admin> listAdmins = adminRepo.findAllByLogin(login);
        if(!listAdmins.isEmpty()){
            model.addAttribute("wrong", "Такой логин уже существует");
            return "Registration";
        }
        Admin admin = new Admin();
        admin.setLogin(login);
        admin.setPassword(password);

        adminRepo.save(admin);

        return "redirect:AllAdmins";
    }

    @GetMapping("/AllAdmins")
    public String allAdmin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Iterable<Admin> admins = adminRepo.findAll();
        model.addAttribute("admins", admins);
        return "AllAdmins";
    }

    @GetMapping("/EditProfileAdmin")
    public String editProfileAdminGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:LoginAdmin";
        }
        model.addAttribute("login", adminLogin);
        return "EditProfileAdmin";
    }

    @PostMapping("/EditProfileAdmin")
    public String editProfileAdminPost(@RequestParam String login, @RequestParam String password1, @RequestParam String password2, @RequestParam String password3, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:LoginAdmin";
        }
        Admin admin = adminRepo.findAllByLogin(adminLogin).get(0);
        if(!admin.getPassword().equals(password1)){
            model.addAttribute("wrong", "Неверный пароль");
            return "EditProfileAdmin";
        }
        if(!password2.equals(password3)){
            model.addAttribute("wrong", "Пароли не совпадают");
            return "EditProfileAdmin";
        }
        if(!login.equals(adminLogin)) {
            List<Admin> listUsers = adminRepo.findAllByLogin(login);
            if(!listUsers.isEmpty()){
                model.addAttribute("wrong", "Такой логин уже существует");
                return "redirect:EditProfileAdmin";
            }
        }
        admin.setLogin(login);
        if(!password2.equals("") && !(password2 == null))
            admin.setPassword(password2);
        adminRepo.save(admin);
        return "redirect:Admin";
    }
}
