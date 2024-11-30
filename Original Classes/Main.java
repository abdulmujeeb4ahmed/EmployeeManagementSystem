public class Main {
    public static void main(String[] args) {
        // Example: Get all employees
        EmployeeDAO dao = new EmployeeDAO();
        for (Employee emp : dao.getAllEmployees()) {
            System.out.println(emp.getName() + " - " + emp.getJobTitle());
        }

        // Example: Get a specific employee by ID
        Employee employee = dao.getEmployeeById(1);
        if (employee != null) {
            System.out.println("Found employee: " + employee.getName());
        } else {
            System.out.println("Employee not found!");
        }
    }
}
