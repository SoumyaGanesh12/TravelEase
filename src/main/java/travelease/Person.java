package travelease;

public class Person {
    protected String name;
    protected String email;
    protected String phoneNumber;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String n) { this.name = n; }
    public void setEmail(String e) { this.email = e; }
    public void setPhoneNumber(String pn) { this.phoneNumber = pn; }
}
