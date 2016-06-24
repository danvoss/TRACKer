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
        model.addAttribute("username", username);

        Iterable<Meet> m = meets.findAll();
        model.addAttribute("meets", m);
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
    public String create(HttpSession session, String date, String location, String division, String gender, String winner, String comments ) {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Meet meet = new Meet(LocalDate.parse(date), location, division, gender, winner, comments, user);
        meets.save(meet);
        return "redirect:/";
    }


}
