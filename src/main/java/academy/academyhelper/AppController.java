package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Controller
public class AppController {
    
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    
    @Autowired
    Repository repository;
    
    @GetMapping("/faq")
    public ModelAndView faq(HttpSession session) {
        
        if (session.getAttribute("user") == null) {
            // Denna if-sats kollar om användaren är inloggad
            return new ModelAndView("redirect:/");
        }
        
        return new ModelAndView("faq");
    }
    
    
    @PostMapping("/login")
    public ModelAndView login(HttpSession session, @RequestParam String email, @RequestParam String password) {
        
        User user = repository.signIn(email, password);
        
        if (user == null) {
            return new ModelAndView("login");
        } else {
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(300);
        }
        
        return new ModelAndView("home")
                .addObject("user", user);
    }
    
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register")
                .addObject("user", new User())
                .addObject("programList", repository.getProgramList());
    }
    
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, @RequestParam String activationcode, Model model) {
        
        String accountType = null;
        
        switch (activationcode) {
            case "AcademyStudent":
                accountType = "Student";
                break;
            case "AcademyTe@cher":
                accountType = "Teacher";
                break;
            case "Academy@dmin":
                accountType = "Admin";
                break;
        }
        
        if (!user.getPassWord().equals(user.getPassWord2())) {
            bindingResult.rejectValue("passWord", "Fel lösenord");
        }
        
        if (repository.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "Det finns redan en användare registrerad med denna email-address");
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("programList", repository.getProgramList());
            return "register";
        } else {
            repository.registerUser(user, accountType);
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
        
        if (session.getAttribute("user") != null) {
            return new ModelAndView("home");
        }
        
        return new ModelAndView("redirect:/");
    }
    
    @GetMapping("/confessions")
    public ModelAndView confessions(HttpSession session) {
        
        if (session.getAttribute("user") == null) {
            // Denna if-sats kollar om användaren är inloggad
            return new ModelAndView("redirect:/");
        }
        
        List<Confession> confessions = repository.getConfessions();
        
        return new ModelAndView("confessions")
                .addObject("newConfession", new Confession())
                .addObject("confessions", confessions);
    }
    
    @PostMapping("/newConfession")
    public String newConfession(@ModelAttribute Confession newConfession) {
        repository.insertConfession(newConfession);
        
        return "redirect:/confessions";
    }
    
    @GetMapping("/forgot")
    public ModelAndView forgot(){
        return new ModelAndView("forgot");
    }
    
    @PostMapping("/forgot")
    public ModelAndView sendEmail(){
        return new ModelAndView("emailsent");
    }
}
