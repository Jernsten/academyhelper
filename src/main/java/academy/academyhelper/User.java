package academy.academyhelper;



import javax.validation.constraints.Size;

public class User {

    private int userID;

    @Size(min=2, max=30)
    private String firstName;
    
    @Size(min=2, max=30)
    private String lastName;
    private String email;
    
    @Size(min=6, max=50)
    private String passWord;
    private String passWord2;
    
    @Size(min=6, max=50)
    private String homeAddress;
    private int program;
    
    public User(int userID, String firstName, String lastName, String email, String passWord, String passWord2, String homeAddress, int program) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.passWord2 = passWord2;
        this.homeAddress = homeAddress;
        this.program = program;
    }
    
    public User() {
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassWord() {
        return passWord;
    }
    
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
    public String getPassWord2() {
        return passWord2;
    }
    
    public void setPassWord2(String passWord2) {
        this.passWord2 = passWord2;
    }
    
    public String getHomeAddress() {
        return homeAddress;
    }
    
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    
    public int getProgram() {
        return program;
    }
    
    public void setProgram(int program) {
        this.program = program;
    }
}