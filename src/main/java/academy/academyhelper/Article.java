package academy.academyhelper;

public class Article {
    private int id;
    private String author;
    private String content;
    private String timeStamp;
    
    public Article(int id, String author, String content, String timeStamp) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.timeStamp = timeStamp;
    }
    
    public Article() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }
    
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
