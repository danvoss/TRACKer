package com.dvoss.controllers;

import com.dvoss.entities.Meet;
import com.dvoss.entities.User;
import com.dvoss.services.MeetRepository;
import com.dvoss.services.UserRepository;
import com.dvoss.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by Dan on 6/23/16.
 */
@Controller
public class TrackerController {
    @Autowired
    UserRepository users;
    @Autowired
    MeetRepository meets;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        Iterable<Meet> m = meets.findAll();
        for (Meet meet : m) {
            if (username != null && username.equals(meet.getUser().getName())) {
                meet.setOwner(true);
            }
            else {
                meet.setOwner(false);
            }
            model.addAttribute("isOwner", meet.isOwner());
        }

//        //order list properly after updates using Collections.sort:
//        ArrayList<Meet> meetList = new ArrayList<>();
//        for (Meet meet : m) {
//            meetList.add(meet);
//        }
//        Collections.sort(meetList);
//
//        //or using a stream:
//        meetList = meetList
//                .stream()
//                .sorted((m1, m2) -> Integer.compare(m2.getId(), m1.getId()))
//                .collect(Collectors.toCollection(ArrayList<Meet>::new));

        //or simply let sql do it:
        model.addAttribute("username", username);
        model.addAttribute("meets", meets.findByOrderByIdDesc());
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findByName(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password.");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(HttpSession session, String date, String location, String division, String gender, String winner, String comments) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findByName(username);
        Meet meet = new Meet(LocalDate.parse(date), location, division, gender, winner, comments, user);
        meets.save(meet);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(HttpSession session, Integer id) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        meets.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/update", method = RequestMethod.GET)
    public String update(Model model, Integer id) {
        Meet m = meets.findOne(id);
        model.addAttribute("date", m.getDate());
        model.addAttribute("location", m.getLocation());
        model.addAttribute("division", m.getDivision());
        model.addAttribute("gender", m.getGender());
        model.addAttribute("winner", m.getWinner());
        model.addAttribute("comments", m.getComments());
        model.addAttribute("id", m.getId());
        return "update";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String edit(HttpSession session, String date, String location, String division, String gender, String winner, String comments, Integer id) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findByName(username);
        Meet m = new Meet(id, LocalDate.parse(date), location, division, gender, winner, comments, user);
        meets.save(m);
        return "redirect:/";
    }


}
