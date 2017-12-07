package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            
            if(!rs.next()) { // Om det inte gick att logga in
                return user;
            }
            
            String pw = rs.getString("password");
            
            if (pw.equals(password)) {
                
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String homeaddress = rs.getString("homeaddress");
                String usertype = rs.getString("usertype");
                int program = rs.getInt("program");
                
                user = new User(id, firstname, lastname, email, password, homeaddress, usertype, program);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public List<Confession> getConfessions() {
        List<Confession> confessions = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[confession] ORDER BY Timestamp DESC";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn
                    .prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String content = rs.getString("content");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm - d/M");
                String tid = timestamp.toLocalDateTime().format(dtf);
                
                // Bättre variabelnamn!
                
                confessions.add(new Confession(content, tid));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return confessions;
    }
    
    public void insertConfession(Confession newConfession) {
        String sql = "INSERT INTO [dbo].[confession] (content) VALUES (?)";
        
        try (Connection conn = dataSource.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newConfession.getContent());
            int rs = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void registerUser(@Valid User user, String accountType) {
        
        String sql = "INSERT INTO [dbo].[user] (email, firstname, lastname, password, homeaddress, usertype, program) " +
                "VALUES (?,?,?,?,?,?,?);";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassWord());
            ps.setString(5, user.getHomeAddress());
            ps.setString(6, accountType);
            ps.setInt(7, user.getProgram());
            
            int rs = ps.executeUpdate();
            // läs av inten för att se om det funkade?
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Program> getProgramList() {
        List<Program> programList = new ArrayList<>();
        
        String sql = "Select * From dbo.program";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String program = rs.getString("name");
                
                programList.add(new Program(id, program));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return programList;
    }
    
    public boolean emailExists(String email) {
        String sql = "SELECT email FROM [dbo].[user] WHERE email = ?";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}