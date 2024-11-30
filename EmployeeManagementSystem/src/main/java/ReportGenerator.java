import java.util.List;

public class ReportGenerator {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void generateFullTimeEmployeeReport() {
        List<Employee> employees = employeeDAO.getFullTimeEmployeesWithPayHistory();
        System.out.println("Full-time Employee Information with Pay Statement History:");
        for (Employee employee : employees) {
            System.out.println(employee);
            // Assuming Employee class has a method to get pay statements
            for (PayStatement payStatement : employee.getPayStatements()) {
                System.out.println("  Pay Date: " + payStatement.getPayDate() + ", Amount: " + payStatement.getAmount());
            }
        }
    }

    public void generateTotalPayForMonthByJobTitle(String jobTitle, int month, int year) {
        double totalPay = employeeDAO.getTotalPayForMonthByJobTitle(jobTitle, month, year);
        System.out.println("Total Pay for " + jobTitle + " in " + month + "/" + year + ": " + totalPay);
    }

    public void generateTotalPayForMonthByDivision(String division, int month, int year) {
        double totalPay = employeeDAO.getTotalPayForMonthByDivision(division, month, year);
        System.out.println("Total Pay for " + division + " Division in " + month + "/" + year + ": " + totalPay);
    }
}