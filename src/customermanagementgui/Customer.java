package customermanagementgui;

/**
 *
 * @author Joshua Farrell
 */
public class Customer {
    private int id;             /* Set and Get */
    private String name;        
    private String email;
    private String mobile;
    
    /* Default constructor */
    public Customer() {
    }
    
    /* Setters and getters */
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getMobile() {
        return mobile;
    }
}