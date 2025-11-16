package com.kitsune.NewAvito.controllers;

import com.kitsune.NewAvito.models.User;
import com.kitsune.NewAvito.repo.UserRepo;
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
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/Login")
    public String loginGet(Model model) {
        return "Login";
    }

    @GetMapping("/Registration")
    public String registrationGet(Model model) {
        return "Registration";
    }

    @PostMapping("/Login")
    public String loginPost(@RequestParam String login, @RequestParam String password, Model model, HttpServletRequest request){
        List<User> listUsers = userRepo.findAllByLogin(login);
        if(listUsers.isEmpty() || !listUsers.get(0).getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный логин или пароль");
            return "Login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("login", login);
        session.setAttribute("id", listUsers.get(0).getId());
        return "redirect:/";
    }

    @PostMapping("/Registration")
    public String registrationPost(@RequestParam String patronymic, @RequestParam String name, @RequestParam String address, @RequestParam String surname, @RequestParam String login, @RequestParam String password1, @RequestParam String phone, @RequestParam String password2, @RequestParam String email, Model model, HttpServletRequest request) {
        List<User> listUsers = userRepo.findAllByLogin(login);
        if(!listUsers.isEmpty()){
            model.addAttribute("wrong", "Такой логин уже существует");
            return "Registration";
        }
        if(!password1.equals(password2)){
            model.addAttribute("wrong", "Пароли не совпадают");
            return "Registration";
        }

        HttpSession session = request.getSession();
        session.setAttribute("login", login);

        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setPatronymic(patronymic);
        user.setLogin(login);
        user.setPhone(phone);
        user.setSurname(surname);
        user.setPassword(password1);
        user.setEmail(email);
        user.setBan(false);
        user.setBlockedEntries(0);

        user = userRepo.save(user);

        session.setAttribute("id", user.getId());

        return "redirect:/";
    }

    @GetMapping("/Profile")
    public String something(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String login = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        if(login == null) {
            return "redirect:Login";
        }
        User user = userRepo.findById(idUser).get();

        String fio = user.getName() + " ";
        if(user.getPatronymic() != null) fio += user.getPatronymic() + " ";
        fio += user.getSurname();

        if(user.getBan() == null || !user.getBan()) {
            model.addAttribute("ban", false);
        }
        else model.addAttribute("ban", true);
        if(user.getBlockedEntries() == null) model.addAttribute("blockedEntries", 0);
        else model.addAttribute("blockedEntries", user.getBlockedEntries());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("address", user.getAddress());
        model.addAttribute("fio", fio);

        return "Profile";
    }

    @PostMapping("/EditProfile")
    public String editProfileGet(@RequestParam String patronymic, @RequestParam String name, @RequestParam String address, @RequestParam String surname, @RequestParam String login, @RequestParam String phone, @RequestParam String email, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginSession = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        User user = userRepo.findById(idUser).get();
        if(!login.equals(loginSession)) {
            List<User> listUsers = userRepo.findAllByLogin(login);
            if(!listUsers.isEmpty()){
                model.addAttribute("wrong", "Такой логин уже существует");
                return "redirect:Registration";
            }
            session.setAttribute("login", login);
            user.setLogin(login);
        }
        user.setName(name);
        user.setPatronymic(patronymic);
        user.setAddress(address);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);
        userRepo.save(user);
        return "redirect:Profile";
    }

    @GetMapping("/EditProfile")
    public String editProfilePost(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        if(login == null) {
            return "redirect:Login";
        }
        User user = userRepo.findById(idUser).get();
        model.addAttribute("user", user);
        return "EditProfile";
    }

    @GetMapping("/Exit")
    public String exit(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("id");
        return "redirect:/";
    }

    @GetMapping("/DeleteProfile")
    public String deleteProfileGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if(login == null) {
            return "redirect:Login";
        }
        return "DeleteProfile";
    }

    @PostMapping("/DeleteProfile")
    public String deleteProfilePost(@RequestParam String login, @RequestParam String password, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer idUser = (Integer) session.getAttribute("id");
        User user = userRepo.findById(idUser).get();
        if(!login.equals(user.getLogin()) || !user.getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный логин или пароль");
            return "DeleteProfile";
        }
        userRepo.deleteById(idUser);
        return "redirect:Exit";
    }

    @GetMapping("/EditPassword")
    public String editPasswordGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if(login == null) {
            return "redirect:Login";
        }
        return "EditPassword";
    }

    @PostMapping("/EditPassword")
    public String editPasswordPost(@RequestParam String password1, @RequestParam String password2, @RequestParam String password3, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        if(login == null) {
            return "redirect:Login";
        }
        User user = userRepo.findById(idUser).get();
        if(!user.getPassword().equals(password1)){
            model.addAttribute("wrong", "Неверный пароль");
            return "EditPassword";
        }
        if(!password2.equals(password3)){
            model.addAttribute("wrong", "Пароли не совпадают");
            return "EditPassword";
        }
        user.setPassword(password2);
        userRepo.save(user);
        return "redirect:Profile";
    }

    @GetMapping("/AllUsers")
    public String allUsers(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "AllUsers";
    }

    @GetMapping("/Block/{id}")
    public String block(@PathVariable(value = "id")Integer id, HttpServletRequest request){
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:/LoginAdmin";
        }
        User user = userRepo.findById(id).get();
        user.setBan(true);
        userRepo.save(user);
        return "redirect:/AllUsers";
    }

    @GetMapping("/Unlock/{id}")
    public String unlock(@PathVariable(value = "id")Integer id, HttpServletRequest request){
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:/LoginAdmin";
        }
        User user = userRepo.findById(id).get();
        user.setBan(false);
        userRepo.save(user);
        return "redirect:/AllUsers";
    }
}
