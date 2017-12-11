package academy.academyhelper;

public class Confession {
    private int id;
    private String content;
    private String timeStamp;
    
    public Confession(int id, String content, String timeStamp) {
        this.id = id;
        this.content = content;
        this.timeStamp = timeStamp;
    }
    
    public Confession() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
