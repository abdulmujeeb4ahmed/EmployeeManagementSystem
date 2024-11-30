import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();
    private static final ReportGenerator reportGenerator = new ReportGenerator();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee by SSN");
            System.out.println("3. Search Employee by Name");
            System.out.println("4. Update Employee");
            System.out.println("5. Generate Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    searchEmployeeBySsn();
                    break;
                case 3:
                    searchEmployeeByName();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateReports() {
        System.out.println("Generate Reports");
        System.out.println("1. Full-time Employee Information with Pay Statement History");
        System.out.println("2. Total Pay for Month by Job Title");
        System.out.println("3. Total Pay for Month by Division");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                reportGenerator.generateFullTimeEmployeeReport();
                break;
            case 2:
                System.out.print("Enter job title: ");
                String jobTitle = scanner.nextLine();
                System.out.print("Enter month (1-12): ");
                int month = scanner.nextInt();
                System.out.print("Enter year: ");
                int year = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                reportGenerator.generateTotalPayForMonthByJobTitle(jobTitle, month, year);
                break;
            case 3:
                System.out.print("Enter division: ");
                String division = scanner.nextLine();
                System.out.print("Enter month (1-12): ");
                int monthDiv = scanner.nextInt();
                System.out.print("Enter year: ");
                int yearDiv = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                reportGenerator.generateTotalPayForMonthByDivision(division, monthDiv, yearDiv);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee job title: ");
        String jobTitle = scanner.nextLine();

        Employee employee = new Employee();
        employee.setName(name);
        employee.setJobTitle(jobTitle);

        employeeDAO.addEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    private static void searchEmployeeByName() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        List<Employee> employees = employeeDAO.getEmployeesByName(name);
        if (!employees.isEmpty()) {
            System.out.println("Employees found:");
            for (Employee employee : employees) {
                System.out.println(employee.getName() + " - " + employee.getJobTitle());
            }
        } else {
            System.out.println("No employees found with the name: " + name);
        }
    }

    private static void searchEmployeeById() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Employee employee = EmployeeDAO.getEmployeeById(id);
        if (employee != null) {
            System.out.println("Employee found: " + employee.getName() + " - " + employee.getJobTitle());
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void searchEmployeeBySsn() {
        System.out.print("Enter employee SSN: ");
        String ssn = scanner.nextLine();

        Employee employee = employeeDAO.getEmployeeBySsn(ssn);
        if (employee != null) {
            System.out.println("Employee found: " + employee.getName() + " - " + employee.getJobTitle());
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Employee employee = EmployeeDAO.getEmployeeById(id);
        if (employee != null) {
            System.out.print("Enter new name (current: " + employee.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new job title (current: " + employee.getJobTitle() + "): ");
            String jobTitle = scanner.nextLine();
            System.out.print("Enter new SSN (current: " + employee.getSsn() + "): ");
            String ssn = scanner.nextLine();
            System.out.print("Enter new division (current: " + employee.getDivision() + "): ");
            String division = scanner.nextLine();
            System.out.print("Enter new salary (current: " + employee.getSalary() + "): ");
            double salary = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            employee.setName(name);
            employee.setJobTitle(jobTitle);
            employee.setSsn(ssn);
            employee.setDivision(division);
            employee.setSalary(salary);

            employeeDAO.updateEmployee(employee);
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

}