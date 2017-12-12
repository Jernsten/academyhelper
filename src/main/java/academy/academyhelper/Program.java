package academy.academyhelper;

public class Program {
    private int id;
    private String name;
    private String graduation;
    
    public Program() {
    }
    
    public Program(int id, String name, String graduation) {
        this.id = id;
        this.name = name;
        this.graduation = graduation;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGraduation() {
        return graduation;
    }
    
    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }
}
