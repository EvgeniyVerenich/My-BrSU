package by.brsucd.mybrsu;
public class User {

    private String email;
    private int id;
    private String password;
    private UserStatus status;

    public User(){}

    public User(int id, UserStatus status){
        this.id = id;
        this.status = status;
    }

    public User(String email, UserStatus status){
        this.status = status;
        this.email = email;
    }

    public User(int id, String email, String password, UserStatus status){
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
    }
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
