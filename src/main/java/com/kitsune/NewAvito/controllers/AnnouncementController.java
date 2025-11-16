package com.kitsune.NewAvito.controllers;

import com.kitsune.NewAvito.models.*;
import com.kitsune.NewAvito.repo.*;
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
import java.util.Optional;

@Controller
public class AnnouncementController {
    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private RuleRepo ruleRepo;

    @GetMapping("/CreateAnnouncement")
    public String createAnnouncementGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if(login == null){
            return "redirect:Login";
        }
        Integer idUser = (Integer) session.getAttribute("id");
        User user = userRepo.findById(idUser).get();

        if(user.getBan()){
            model.addAttribute("text", "Вы не можете размещать объявления, так как ваша учётная запись заблокирована");
            model.addAttribute("href", request.getHeader("referer"));
            return "Message";
        }

        return "CreateAnnouncement";
    }

    @PostMapping("/CreateAnnouncement")
    public String createAnnouncementPost(@RequestParam String title, @RequestParam String name, @RequestParam String address, @RequestParam String characteristics, @RequestParam String description, @RequestParam String price, @RequestParam String telephone, @RequestParam String other_contacts, @RequestParam String type, @RequestParam String tags, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String login = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        if(login == null) {
            return "redirect:Login";
        }

        User user = userRepo.findById(idUser).get();

        Announcement an = new Announcement();
        an.setIdUsers((Integer)session.getAttribute("id"));
        if(name  == null || name.equals("")){
            String fio = user.getName() + " ";
            if(user.getPatronymic() != null) fio += user.getPatronymic() + " ";
            fio += user.getSurname();
            an.setName(fio);
        }
        else an.setName(name);
        if(address == null || address.equals("")){
            an.setAddress(user.getAddress());
        }
        else an.setAddress(address);
        an.setTitle(title);
        an.setCharacteristics(characteristics);
        an.setDescription(description);
        an.setPrice(Double.parseDouble(price));
        if(telephone == null || address.equals("")){
            an.setTelephone(user.getPhone());
        }
        else an.setTelephone(telephone);
        an.setOtherContacts(other_contacts);
        an.setType(type);
        an.setTags(tags);
        an.setDat(new Date());
        an.setVerifiedByAdmin(true);

        Iterable<Rule> rules = ruleRepo.findAll();
        for (Rule r:rules) {
            switch (r.getType()){
                case "Слова":
                    String text = "";
                    String[] words = r.getText().split(" ");
                    for (int i = 0; i < words.length; i++){
                        if(an.getTags() != null && an.getTags().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в тегах) ";
                        }
                        if(an.getTitle().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в названии) ";
                        }
                        if(an.getOtherContacts().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в дополнительных контактах) ";

                        }
                        if(an.getAddress().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в адресе) ";
                        }
                        if(an.getCharacteristics().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в характеристиках) ";
                        }
                        if(an.getName().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в ФИО) ";
                        }
                        if(an.getDescription().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в описании) ";

                        }
                    }
                    if(!text.equals("")) {
                        model.addAttribute("href", request.getHeader("referer"));
                        model.addAttribute("text", text);
                        return "Message";
                    } else break;
            }
        }

        announcementRepo.save(an);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String announcementInfo(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer idUsers = (Integer)session.getAttribute("id");
        Optional<Announcement> announcement = announcementRepo.findById(id);
        List<Announcement> res = new ArrayList<>();
        announcement.ifPresent(res::add);
        if(session.getAttribute("admin")!=null){
            model.addAttribute("isAdmin", true);
            if(announcement.get().getVerifiedByAdmin() == null || !announcement.get().getVerifiedByAdmin()){
                model.addAttribute("notVerified", true);
            }
        }
        if(idUsers == null || idUsers == res.get(0).getIdUsers()){
            model.addAttribute("thisUsersRecord", true);
        }
        else model.addAttribute("thisUsersRecord", false);

        List<Complaint> complaints = complaintRepo.findAllByIdAnnouncement(id);

        model.addAttribute("complaints", complaints);
        model.addAttribute("announcement", res);
        model.addAttribute("other_contactsNullFalse", !(res.get(0).getOtherContacts() == null || res.get(0).getOtherContacts().equals("")));
        return "Announcement";

    }

    @GetMapping("/AnnouncementUsers")
    public String announcementUsers(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if(login == null) {
            return "redirect:Login";
        }
        Iterable<Announcement> announcements = announcementRepo.findAllByIdUsers((Integer)session.getAttribute("id"));
        model.addAttribute("announcements", announcements);
        return "AnnouncementUsers";
    }

    @GetMapping("/Edit/{id}")
    public String editAnnouncementGet(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Integer idUsers = (Integer) session.getAttribute("id");
        Announcement an = announcementRepo.findById(id).get();
        if(login == null || idUsers != an.getIdUsers()) {
            return "redirect:/Login";
        }

        Integer idUser = (Integer) session.getAttribute("id");
        User user = userRepo.findById(idUser).get();

        if(user.getBan()){
            model.addAttribute("text", "Вы не можете редактировать объявления, так как ваша учётная запись заблокирована");
            model.addAttribute("href", request.getHeader("referer"));
            return "Message";
        }

        Announcement announcement = announcementRepo.findById(id).get();
        model.addAttribute("announcement", announcement);
        return "EditAnnouncement";
    }

    @PostMapping("/Edit/{id}")
    public String editAnnouncementPost(@PathVariable(value = "id") Integer id, @RequestParam String title, @RequestParam String name, @RequestParam String address, @RequestParam String characteristics, @RequestParam String description, @RequestParam String price, @RequestParam String telephone, @RequestParam String otherContacts, @RequestParam String type, @RequestParam String tags, Model model, HttpServletRequest request) {
        Announcement an = announcementRepo.findById(id).get();
        an.setName(name);
        an.setAddress(address);
        an.setTitle(title);
        an.setCharacteristics(characteristics);
        an.setDescription(description);
        an.setPrice(Double.parseDouble(price));
        an.setTelephone(telephone);
        an.setOtherContacts(otherContacts);
        an.setType(type);
        an.setTags(tags);
        an.setDat(new Date());
        Iterable<Rule> rules = ruleRepo.findAll();
        for (Rule r:rules) {
            switch (r.getType()){
                case "Слова":
                    String text = "";
                    String[] words = r.getText().split(" ");
                    for (int i = 0; i < words.length; i++){
                        if(an.getTags() != null && an.getTags().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в тегах) ";
                        }
                        if(an.getTitle().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в названии) ";
                        }
                        if(an.getOtherContacts().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в дополнительных контактах) ";

                        }
                        if(an.getAddress().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в адресе) ";
                        }
                        if(an.getCharacteristics().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text +="В вашем объявлении присутствуют запрещённые слова (в характеристиках) ";
                        }
                        if(an.getName().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в ФИО) ";
                        }
                        if(an.getDescription().replaceAll("[ 1234567890.?<>,#@$%^:;*()_=+~`-]","").toLowerCase().contains(words[i].toLowerCase())){
                            text += "В вашем объявлении присутствуют запрещённые слова (в описании) ";

                        }
                    }
                    if(!text.equals("")) {
                        model.addAttribute("href", request.getHeader("referer"));
                        model.addAttribute("text", text);
                        return "Message";
                    } else break;
            }
        }
        announcementRepo.save(an);
        return "redirect:/AnnouncementUsers";
    }

    @GetMapping("/Delete/{id}")
    public String deleteAnnouncementGet(@PathVariable(value = "id")Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Integer idUsers = (Integer) session.getAttribute("id");
        if(login == null || idUsers != id) {
            return "redirect:/Login";
        }
        return "/DeleteAnnouncement";
    }

    @PostMapping ("/Delete/{id}")
    public String deleteAnnouncementPost(@RequestParam String password, @PathVariable(value = "id") Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Integer idUser = (Integer) session.getAttribute("id");
        if(login == null) {
            return "redirect:/Login";
        }
        User user = userRepo.findById(idUser).get();
        if(!user.getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный пароль");
            return "/DeleteAnnouncement";
        }
        announcementRepo.deleteById(id);
        return "redirect:/AnnouncementUsers";
    }

    @GetMapping("/AnnouncementAdmin")
    public String announcementAdmin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) {
            return "redirect:LoginAdmin";
        }

        Iterable<Announcement> ann = announcementRepo.findAll();
        List<Announcement> announcements = new ArrayList<>();
        for(Announcement a : ann) {
            if(!a.getVerifiedByAdmin() || (a.getPresenceOfComplaints() != null && a.getPresenceOfComplaints()))
                announcements.add(a);
        }

        model.addAttribute("announcements", announcements);
        return "AnnouncementAdmin";
    }

    @GetMapping("/DeleteAnnouncementAdmin/{id}")
    public String deleteAnnouncementAdminGet(@PathVariable(value = "id")Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:/LoginAdmin";
        }
        return "/DeleteAnnouncementAdmin";
    }

    @GetMapping("/{id}/Post")
    public String postGet(@PathVariable(value = "id")Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:/LoginAdmin";
        }
        Announcement announcement = announcementRepo.findById(id).get();
        announcement.setVerifiedByAdmin(true);
        announcementRepo.save(announcement);
        return "redirect:/AnnouncementAdmin";
    }

    @PostMapping ("/DeleteAnnouncementAdmin/{id}")
    public String deleteAnnouncementAdminPost(@RequestParam String password, @PathVariable(value = "id") Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:/LoginAdmin";
        }
        Admin admin = adminRepo.findAllByLogin(adminLogin).get(0);
        if(!admin.getPassword().equals(password)){
            model.addAttribute("wrong", "Неверный пароль");
            return "DeleteAnnouncementAdmin";
        }
        Announcement announcement = announcementRepo.findById(id).get();
        User user = userRepo.findById(announcement.getIdUsers()).get();
        if(user.getBlockedEntries() == null)
            user.setBlockedEntries(1);
        else user.setBlockedEntries(user.getBlockedEntries() + 1);
        if(user.getBlockedEntries() >= 3)
            user.setBan(true);
        userRepo.save(user);
        announcementRepo.deleteById(id);
        return "redirect:/AnnouncementAdmin";
    }

    @PostMapping ("/{id}")
    public String complaint(@RequestParam String text, @PathVariable(value = "id") Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer idUser = (Integer) session.getAttribute("id");

        Iterable<Complaint> complaints = complaintRepo.findAll();
        for(Complaint c : complaints){
            if(c.getIdUser() == idUser) {
                model.addAttribute("text", "Вы больше не можете отправлять жалобы на это объявление :(");
                model.addAttribute("href", "/" + id);
                return "Message";
            }
        }

        Complaint complaint = new Complaint();
        complaint.setDate(new Date());
        if(idUser != null)
            complaint.setIdUser(idUser);
        complaint.setIdAnnouncement(id);
        complaint.setText(text);

        complaintRepo.save(complaint);

        Announcement announcement = announcementRepo.findById(id).get();
        announcement.setPresenceOfComplaints(true);
        if(announcement.getNumberOfComplaints() == null) announcement.setNumberOfComplaints(1);
        else announcement.setNumberOfComplaints(announcement.getNumberOfComplaints() + 1);
        announcementRepo.save(announcement);

        model.addAttribute("text", "Ваша жалоба была успешно отправлена администратору :)");
        model.addAttribute("href", "/" + id);

        return "Message";
    }

    @GetMapping("/{id}/Clear")
    public String clear(@PathVariable(value = "id")Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adminLogin = (String) session.getAttribute("admin");
        if(adminLogin == null) {
            return "redirect:/LoginAdmin";
        }

        List<Complaint> complaints = complaintRepo.findAllByIdAnnouncement(id);
        for (Complaint c:complaints) {
            complaintRepo.delete(c);
        }

        Announcement announcement = announcementRepo.findById(id).get();
        announcement.setVerifiedByAdmin(true);
        announcement.setNumberOfComplaints(0);
        announcement.setPresenceOfComplaints(false);
        announcementRepo.save(announcement);

        return "redirect:/AnnouncementAdmin";
    }
}
