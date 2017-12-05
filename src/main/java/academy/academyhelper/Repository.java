package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Repository {
    
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    
    public User signIn(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM [dbo].[user] WHERE email = ?";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            
            String pw = rs.getString("password");
            
            if (pw.equals(password)) {
                
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String homeaddress = rs.getString("homeaddress");
                String usertype = rs.getString("usertype");
                String klass = rs.getString("klass");
                // int userID, String firstName, String lastName, String email, String passWord, String homeAdress
                user = new User(id, firstname, lastname, email, password, homeaddress);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
