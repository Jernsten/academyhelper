package academy.academyhelper;

public class Confession {
    private String content;
    private String timeStamp;
    
    public Confession() {
    }
    
    public Confession(String content, String timeStamp) {
        this.content = content;
        this.timeStamp = timeStamp;
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
