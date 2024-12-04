import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private List<Employee> employees;

    public EmployeeDAO() {
        employees = getAllEmployees();
    }
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("empid"),
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("job_title"),
                        rs.getString("division"),
                        rs.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name = ?, job_title = ?, ssn = ?, division = ?, salary = ? WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getJobTitle());
            stmt.setString(3, employee.getSsn());
            stmt.setString(4, employee.getDivision());
            stmt.setDouble(5, employee.getSalary());
            stmt.setInt(6, employee.getEmpid());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, ssn, job_title, division, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSsn());
            stmt.setString(3, employee.getJobTitle());
            stmt.setString(4, employee.getDivision());
            stmt.setDouble(5, employee.getSalary());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Employee getEmployeeById(int empid) {
        String sql = "SELECT * FROM Employees WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("empid"),
                            rs.getString("name"),
                            rs.getString("ssn"),
                            rs.getString("job_title"),
                            rs.getString("division"),
                            rs.getDouble("salary")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(new Employee(
                            rs.getInt("empid"),
                            rs.getString("name"),
                            rs.getString("ssn"),
                            rs.getString("job_title"),
                            rs.getString("division"),
                            rs.getDouble("salary")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee getEmployeeBySsn(String ssn) {
        String sql = "SELECT * FROM Employees WHERE ssn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ssn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("empid"),
                            rs.getString("name"),
                            rs.getString("ssn"),
                            rs.getString("job_title"),
                            rs.getString("division"),
                            rs.getDouble("salary")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getFullTimeEmployeesWithPayHistory() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, p.pay_date, p.amount FROM Employees e " +
                "JOIN PayStatements p ON e.empid = p.empid WHERE e.employment_type = 'Full-time'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("empid"),
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("job_title"),
                        rs.getString("division"),
                        rs.getDouble("salary")
                );
                // Assuming Employee class has a method to add pay statements
                employee.addPayStatement(rs.getDate("pay_date"), rs.getDouble("amount"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public double getTotalPayByJobTitle(String jobTitle) {
        String sql = "SELECT SUM(salary) AS totalPay FROM Employees WHERE job_title = ?";
        double totalPay = 0.0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jobTitle);
            System.out.println("Executing query: " + stmt); // Debug statement
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalPay = rs.getDouble("totalPay");
            } else {
                System.out.println("No results found for Job Title: " + jobTitle); // Debug statement
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception for Job Title: " + jobTitle);
            e.printStackTrace();
        }

        return totalPay;
    }

    public double getTotalPayByDivision(String divisionName) {
        String sql = "SELECT SUM(salary) AS totalPay FROM Employees WHERE division = ?";
        double totalPay = 0.0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, divisionName);
            System.out.println("Executing query: " + stmt); // Debug statement
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalPay = rs.getDouble("totalPay");
            } else {
                System.out.println("No results found for division: " + divisionName); // Debug statement
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception for division: " + divisionName);
            e.printStackTrace();
        }

        return totalPay;
    }

    public void updateSalaryByPercentage(double percentage, double minSalary, double maxSalary) {
        String sql = "UPDATE Employees SET salary = salary + (salary * ? / 100) " +
                "WHERE salary >= ? AND salary < ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, percentage);
            stmt.setDouble(2, minSalary);
            stmt.setDouble(3, maxSalary);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Updated salaries for " + rowsUpdated + " employees.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}