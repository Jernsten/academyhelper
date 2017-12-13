package academy.academyhelper;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String timestamp;
    private int likes;
    private int topicId;
    private String name;
    
    public Post() {
    }
    
    public Post(int id, int userId, String content, String timestamp, int likes, int topicId, String name) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
        this.likes = likes;
        this.topicId = topicId;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public int getTopicId() {
        return topicId;
    }
    
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
