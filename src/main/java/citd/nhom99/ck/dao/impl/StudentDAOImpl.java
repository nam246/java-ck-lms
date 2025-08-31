package citd.nhom99.ck.dao.impl;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.dao.StudentDAO;
import citd.nhom99.ck.dao.UserDAO;
import citd.nhom99.ck.model.Role;
import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.User;
import citd.nhom99.ck.utils.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public void addStudent(User student) {
        System.out.println(student.toString());
        String sql = "INSERT INTO students (student_id, user_id, average_grade) VALUES(?, ?, ?)";
        Query.addUserByRole(student, Role.STUDENT, sql);
    }

    @Override
    public Student getStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) {
        userDAO.updateUser(student.getUser());

        String sql = "UPDATE students SET average_grade = ? WHERE student_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, student.getAverageGrade());
            pstmt.setString(2, student.getStudentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteStudent(int userId) {
        Query.deleteUser(userId);
    }

    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setAverageGrade(rs.getFloat("average_grade"));

        User user = userDAO.getUserById(rs.getInt("user_id"));
        student.setUser(user);

        return student;
    }
}
