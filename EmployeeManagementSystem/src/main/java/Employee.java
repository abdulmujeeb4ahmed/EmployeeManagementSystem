import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee {
    private int empid;
    private String name;
    private String ssn;
    private String jobTitle;
    private String division;
    private double salary;
    private List<PayStatement> payStatements;

    // Constructor, getters and setters
    public Employee(int empid, String name, String ssn, String jobTitle, String division, double salary) {
        this.empid = empid;
        this.name = name;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.payStatements = new ArrayList<>();
    }

    public Employee() {

    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void addPayStatement(Date payDate, double amount) {
        this.payStatements.add(new PayStatement(payDate, amount));
    }

    public List<PayStatement> getPayStatements() {
        return payStatements;
    }
}