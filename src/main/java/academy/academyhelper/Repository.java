package academy.academyhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
            
            if (!rs.next()) { // Om det inte gick att logga in
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
                
                user = new User(id, firstname, lastname, email, password, homeaddress, usertype, program, usertype);
            } else {
                return user;
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
                int id = rs.getInt("ID");
                String content = rs.getString("content");
                Timestamp timeStamp = rs.getTimestamp("timestamp");
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m - d/L");
                String time = timeStamp.toLocalDateTime().format(dtf);
                
                confessions.add(new Confession(id, content, time));
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
                Timestamp timeStamp = rs.getTimestamp("graduation");
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
                String time = timeStamp.toLocalDateTime().format(dtf);
                
                programList.add(new Program(id, program, time));
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
    
    public void deleteConfession(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM [dbo].[confession] WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Article> getNews() {
        List<Article> news = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[news] ORDER BY [Timestamp] DESC ";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String author = rs.getString("author");
                String content = rs.getString("content");
                Timestamp timeStamp = rs.getTimestamp("timestamp");
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m - d/L");
                String time = timeStamp.toLocalDateTime().format(dtf);
                
                Article article = new Article(id, author, content, time);
                news.add(article);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return news;
    }
    
    public void makeNews(NewArticle newArticle) {
        String sql = "INSERT INTO [dbo].[news] (author, content) VALUES (?,?)";
        
        try (Connection conn = dataSource.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newArticle.getAuthor());
            ps.setString(2, newArticle.getContent());
            
            int rs = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addProgram(String name, String date) {
        
        String sql = "INSERT INTO [dbo].[program] (name, graduation) VALUES (?,?)";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, date);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int daysLeft(User user) {
        
        int program = user.getProgram();
        int days = 1;
        
        String sql = "SELECT graduation FROM [dbo].[program] WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, program);
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            Timestamp ts = rs.getTimestamp("graduation");
            
            LocalDateTime graduation = ts.toLocalDateTime();
            LocalDate then = graduation.toLocalDate();
            LocalDate now = LocalDate.now();
            
            days += Period.between(now, then).getDays();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return days;
    }
    
    public void deleteProgram(String programtodelete) {
        int programID = Integer.parseInt(programtodelete);
        
        String sql = "DELETE FROM [dbo].[program] WHERE id = ?";
        String sql2 = "DELETE FROM [dbo].[user] WHERE program = ?";
        
        try (Connection conn = dataSource.getConnection()) {
            // Radera program
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, programID);
            ps.executeUpdate();
            
            // Radera alla användare på det programmet
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, programID);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        
        String sql = "Select * From [dbo].[user]";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String userType = rs.getString("usertype");
                int userId = rs.getInt("id");
                
                userList.add(new User(userId, firstName, lastName, userType));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userList;
    }
    
    public void deleteUser(int usertodelete) {
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM [dbo].[user] WHERE id = ?");
            ps.setInt(1, usertodelete);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Topic> getTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[topic] ORDER BY [id] DESC ";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                
                Topic topic = new Topic(id, name);
                topics.add(topic);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return topics;
    }
    
    public List<Post> getPosts(int topicId) {
        List<Post> postList = new ArrayList<>();
        //String sql = "SELECT * FROM [dbo].[forum] WHERE [TopicId] = ? ORDER BY [TIMESTAMP ] DESC ";
        
        String sql = "SELECT id" +
                ", userid" +
                ", content" +
                ", [Timestamp]" +
                ", likes" +
                ", topicid" +
                ", (SELECT firstname FROM dbo.[user] as du1 WHERE df.userid = du1.id) as Firstname" +
                ", (SELECT lastname FROM dbo.[user] as du2 Where df.userid = du2.id) as Lastname " +
                "FROM DBO.forum as df " +
                "WHERE [TopicId] = ? ORDER BY [Timestamp] DESC ";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, topicId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                int userId = rs.getInt("UserId");
                String content = rs.getString("Content");
                Timestamp timeStamp = rs.getTimestamp("Timestamp");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m - d/L");
                String time = timeStamp.toLocalDateTime().format(dtf);
                int likes = rs.getInt("Likes");
                String name = rs.getString("firstname") + " " + rs.getString("lastname");
                
                // (int id, int userId, String content, String timestamp, int likes, int topicId)
                Post post = new Post(id, userId, content, time, likes, topicId, name);
                postList.add(post);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return postList;
    }
    
    public void insertPost(Post newPost, User user, int topicId) {
        String sql = "INSERT INTO [dbo].[forum] (userid, content, topicid) VALUES (?,?,?)";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getUserID());
            ps.setString(2, newPost.getContent());
            ps.setInt(3, topicId);
            
            int rs = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}