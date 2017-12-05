package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class AppController {
    
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register").addObject("user",new User());
    }
    
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        
        // Se till att lösen är krypterat
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT firstname, password FROM [dbo].[user] WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            String name = rs.getString("firstname");
            if (rs.getString("password").equals(password)) {
                return new ModelAndView("home").addObject("name", name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("register");
    }
    
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult) {

        if (!user.getPassWord().equals(user.getPassWord2())){
            bindingResult.rejectValue("passWord","Fel lösenord");
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
    
        return "sida";
    }
}
