package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
    
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register").addObject("user", new User());
    }

    @GetMapping("/faq")
    public ModelAndView faq() {
        return new ModelAndView("faq");
    }


    @PostMapping("/login")
    public ModelAndView login(HttpSession session, @RequestParam String email, @RequestParam String password) {
        
        User user = repository.signIn(email, password);
        
        if (user != null) {
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(300);
        }
        
        return new ModelAndView("home")
                .addObject("user", user);
    }
    
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult) {
        
        if (!user.getPassWord().equals(user.getPassWord2())) {
            bindingResult.rejectValue("passWord", "Fel lösenord");
        }
        
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO [dbo].[user] (email, firstname, lastname, password, homeaddress, usertype)" +
                    "VALUES (?,?,?,?,?,?);");
            
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassWord());
            ps.setString(5, user.getHomeAddress());
            ps.setString(6, "student");
            
            int rs = ps.executeUpdate();
            // läs av inten för att se om det funkade?
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "home";
    }
    
    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
        
        if (session.getAttribute("user") != null) {
            return new ModelAndView("home");
        }
        return new ModelAndView("redirect:/");
    }
    
    @GetMapping("/confessions")
    public ModelAndView confessions() {
        List<Confession> confessions = repository.getConfessions();
        
        return new ModelAndView("confessions")
                .addObject("newConfession", new Confession())
                .addObject("confessions", confessions);
    }
    
    @PostMapping("/newConfession")
    public ModelAndView newConfession(@ModelAttribute Confession newConfession) {
        repository.insertConfession(newConfession);
        
        return confessions();
    }
}
