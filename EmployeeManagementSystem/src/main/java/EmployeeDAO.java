import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
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
        String sql = "INSERT INTO employees (name, job_title) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getJobTitle());
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

    public double getTotalPayForMonthByJobTitle(String jobTitle, int month, int year) {
        String sql = "SELECT SUM(p.amount) FROM PayStatements p " +
                "JOIN Employees e ON p.empid = e.empid " +
                "WHERE e.job_title = ? AND MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jobTitle);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalPayForMonthByDivision(String division, int month, int year) {
        String sql = "SELECT SUM(p.amount) FROM PayStatements p " +
                "JOIN Employees e ON p.empid = e.empid " +
                "WHERE e.division = ? AND MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, division);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}