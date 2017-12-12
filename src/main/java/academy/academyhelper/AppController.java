package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;
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
    public String login(HttpSession session, @RequestParam String email, @RequestParam String password) {
        
        User user = repository.signIn(email, password);
        
        if (user == null) {
            return "/login";
        } else {
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(300);
        }
        
        return "redirect:/home";
    }
    
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register")
                .addObject("user", new User())
                .addObject("programList", repository.getProgramList());
    }
    
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, @RequestParam String userType, Model model) {
        
        String accountType = null;
        
        switch (userType) {
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
        
        if (user.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", "Skriv din mailadress.");
        }
        
        if (repository.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "Det finns redan en användare registrerad med denna email-address");
        }
        
        if (user.getProgram() == 0) {
            bindingResult.rejectValue("program", "Välj en klass");
        }
        
        if (!(user.getUserType().equals("AcademyStudent") || user.getUserType().equals("AcademyTe@cher") || user.getUserType().equals("Academy@dmin"))) {
            bindingResult.rejectValue("userType", "Fel aktiveringskod");
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
    public String home(HttpSession session, Model model) {
        
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        
        List<Article> news = repository.getNews().subList(0, 2);
        session.setAttribute("news", news);
        
        session.setAttribute("daysLeft", repository.daysLeft((User) session.getAttribute("user")));
        
        model.addAttribute("newArticle", new NewArticle()); // bara för admin
        
        return "home";
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
    
    @GetMapping("/deleteConfession/{id}")
    public String deleteConfession(@PathVariable int id) {
        
        repository.deleteConfession(id);
        
        return "redirect:/confessions";
    }
    
    @PostMapping("/newConfession")
    public String newConfession(@ModelAttribute Confession newConfession) {
        
        String confession = newConfession.getContent().trim();
        
        if (!confession.equals("")) {
            newConfession.setContent(confession);
            repository.insertConfession(newConfession);
        }
        
        
        return "redirect:/confessions";
    }
    
    @GetMapping("/forgot")
    public ModelAndView forgot() {
        return new ModelAndView("forgot");
    }
    
    @PostMapping("/forgot")
    public ModelAndView sendEmail() {
        return new ModelAndView("emailsent");
    }
    
    @PostMapping("/makeNews")
    public String makeNews(@ModelAttribute NewArticle newArticle) {
        repository.makeNews(newArticle);
        return "redirect:/home";
    }
    
    @GetMapping("/admin")
    public String administration(Model model) {
        model.addAttribute("programList", repository.getProgramList());
        model.addAttribute("userList", repository.getUserList());
        return "/admin";
    }
    
    @PostMapping("/addprogram")
    public String addProgram(@RequestParam String name, @RequestParam String date) {
        repository.addProgram(name, date);
        
        return "redirect:/admin";
    }
    
    @PostMapping("/deleteprogram")
    public String deleteProgram(@RequestParam String programtodelete) {
        repository.deleteProgram(programtodelete);
        
        return "redirect:/admin";
    }
    
    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam int usertodelete) {
        repository.deleteUser(usertodelete);
        
        return "redirect:/admin";
    }
}
