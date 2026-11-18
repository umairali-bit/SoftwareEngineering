package day1;

public class BankCustomer {

    private String name;
    private String email;
    private Long customerId;

    public BankCustomer(String name, String email, Long customerId) {
        this.name = name;
        this.email = email;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void displayCustomerInfo() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Customer ID: " + customerId);
    }

    public static void main(String[] args) {
        BankCustomer b = new BankCustomer("Walter White", "walter@example.com", 2L);

        b.displayCustomerInfo();
    }
}
