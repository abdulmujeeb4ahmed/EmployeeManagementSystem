import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReportGenerator {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void generateFullTimeEmployeeReport(String employeeName) {
        List<Employee> employees = employeeDAO.getEmployeesByName(employeeName);
        if (employees.isEmpty()) {
            System.out.println("No employees found with the name: " + employeeName);
            return;
        }
        for (Employee employee : employees) {
            System.out.println("Employee Information:");
            System.out.println("Name: " + employee.getName());
            System.out.println("SSN: " + employee.getSsn());
            System.out.println("Job Title: " + employee.getJobTitle());
            System.out.println("Division: " + employee.getDivision());
            System.out.println("Salary: " + employee.getSalary());

            double totalSalary = employee.getSalary();
            System.out.println("Pay Statement History:");
            for (PayStatement payStatement : employee.getPayStatements()) {
                System.out.println("  Pay Date: " + payStatement.getPayDate() + ", Amount: " + payStatement.getAmount());
                totalSalary += payStatement.getAmount();
            }
            System.out.println("Total Salary: " + totalSalary);
            System.out.println();
        }
    }

    public void generateTotalPayByJobTitle(String jobTitle) {
        double totalPay = employeeDAO.getTotalPayByJobTitle(jobTitle);
        System.out.println("Total Pay for " + jobTitle + ": " + totalPay);
    }

    public void generateTotalPayByDivision(String divisionName) {
        double totalPay = employeeDAO.getTotalPayByDivision(divisionName);
        System.out.println("Total Salary for " + divisionName + " Division: " + totalPay);
    }

}