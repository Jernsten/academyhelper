package academy.academyhelper;

public class NewArticle {
    private String author;
    private String content;
    
    public NewArticle(String author, String content) {
        this.author = author;
        this.content = content;
    }
    
    public NewArticle() {
    }
    
    public NewArticle(String author) {
        this.author = author;
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
}
