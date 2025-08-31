package citd.nhom99.ck.dao.impl;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.dao.TeacherDAO;
import citd.nhom99.ck.dao.UserDAO;
import citd.nhom99.ck.model.Role;
import citd.nhom99.ck.model.Teacher;
import citd.nhom99.ck.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import citd.nhom99.ck.utils.Query;

public class TeacherDAOImpl implements TeacherDAO {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public void addTeacher(User teacher) {
        System.out.println(teacher.toString());
        String sql = "INSERT INTO teachers(teacher_id, user_id, subject) VALUES(?, ?, ?)";
        Query.addUserByRole(teacher, Role.TEACHER, sql);
    }

    @Override
    public Teacher getTeacherById(String teacherId) {
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacherId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractTeacherFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
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

    @Override
    public void updateTeacher(Teacher teacher) {
        // First, update the user in the users table
        userDAO.updateUser(teacher.getUser());

        // Then, update the teacher in the teachers table
        String sql = "UPDATE teachers SET subject = ? WHERE teacher_id = ?";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacher.getSubject());
            pstmt.setString(2, teacher.getTeacherId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTeacher(String teacherId) {
        // Get the user_id before deleting the teacher
        Teacher teacher = getTeacherById(teacherId);
        if (teacher != null) {
            // Delete the teacher from the teachers table
            String sql = "DELETE FROM teachers WHERE teacher_id = ?";
            try (Connection conn = DBConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, teacherId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Delete the user from the users table
            if (teacher.getUser() != null) {
                userDAO.deleteUser(teacher.getUser().getUserId());
            }
        }
    }

    private Teacher extractTeacherFromResultSet(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(rs.getString("teacher_id"));
        teacher.setSubject(rs.getString("subject"));

        // Get the associated user
        User user = userDAO.getUserById(rs.getInt("user_id"));
        teacher.setUser(user);

        return teacher;
    }
}
