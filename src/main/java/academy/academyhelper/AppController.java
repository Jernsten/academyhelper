package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class AppController {
    
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    
    @Autowired
    Repository repository;
    
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }
    
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        
        User user = repository.signIn(email, password);
        
        return new ModelAndView("home")
                .addObject("user", user);
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String password1, @RequestParam String activationcode, @RequestParam String address) {
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO [dbo].[user] (email, firstname, lastname, password, homeaddress, usertype)" +
                    "VALUES (?,?,?,?,?,?);");
            
            ps.setString(1, email);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.setString(4, password1);
            ps.setString(5, address);
            ps.setString(6, "student");
            
            int rs = ps.executeUpdate();
            // läs av inten för att se om det funkade?
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "sida";
    }
    
    @GetMapping("/home/{user}")
    public ModelAndView home(@PathVariable User user) {
        
        return new ModelAndView("home")
                .addObject("user", user);
    }
}
