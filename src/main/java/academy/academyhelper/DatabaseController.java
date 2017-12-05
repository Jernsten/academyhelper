package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class DatabaseController {
    
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @ResponseBody
    @GetMapping("/dbtest")
    public String dbtest() {
    
        try (Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement("Select 1+1");
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            
            if (rs.getInt(1) == 2)
                return "<h1>DataBase is working</h1>";
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "<h1>Not working</h1>";
    }
}
