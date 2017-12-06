package academy.academyhelper;

public class Program {
    private int id;
    private String name;
    
    public Program(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Program() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
