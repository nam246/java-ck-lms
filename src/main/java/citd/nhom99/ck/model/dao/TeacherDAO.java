package citd.nhom99.ck.model.dao;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.model.Teacher;
import citd.nhom99.ck.model.User;
import citd.nhom99.ck.utils.QueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    private final UserDAO userDAO = new UserDAO();

    public void addTeacher(User teacher) {
        System.out.println(teacher.toString());
        QueryHelper.insertTeacher(teacher);
    }

    public Teacher getTeacherById(int teacherId) {
        String sql = "SELECT * FROM teachers WHERE teacher_code = ?";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractTeacherFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Teacher> getAllTeachers() {
        String sql = "SELECT * FROM teachers";
        List<Teacher> teachers = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                teachers.add(extractTeacherFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teachers;
    }

    public void updateTeacher(Teacher teacher) {
        // First, update the user in the users table
        userDAO.updateUser(teacher.getUser());

        // Then, update the teacher in the teachers table
        String sql = "UPDATE teachers SET subject_id = ? WHERE teacher_code = ?";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacher.getSubjectId());
            pstmt.setString(2, teacher.getTeacherCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTeacher(int userId) {
        userDAO.deleteUser(userId);
    }

    private Teacher extractTeacherFromResultSet(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTeacherCode(rs.getString("teacher_code"));
        teacher.setSubjectId(rs.getInt("subject_id"));

        // Get the associated user
        User user = userDAO.getUserById(rs.getInt("user_id"));
        teacher.setUser(user);

        return teacher;
    }
}