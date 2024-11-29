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

    // Additional methods for insert, update, and delete can be added similarly.
}
