package citd.nhom99.ck.controller;

import citd.nhom99.ck.model.Role;
import citd.nhom99.ck.model.Teacher;
import citd.nhom99.ck.model.User;
import citd.nhom99.ck.model.dao.UserDAO;
import citd.nhom99.ck.model.dao.StudentDAO;
import citd.nhom99.ck.model.dao.TeacherDAO;
import citd.nhom99.ck.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRoleController {
    private UserDAO userDAO;
    private final StudentDAO studentDAO = new StudentDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();

    public AdminRoleController() {
    }

    public AdminRoleController(UserDAO userDAO) {
        this.userDAO = new UserDAO();
    }

    public List getUsersByRole(Role role) {
        List data = new ArrayList<>();
        if (role == Role.STUDENT) {
            System.out.println("Controller: Getting all students");
            data = studentDAO.getAllStudents();
        } else if (role == Role.TEACHER) {
            System.out.println("Controller: Getting all teachers");
            data = teacherDAO.getAllTeachers();
        }
        return data;
    }

    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentCode(rs.getString("student_code"));

        User user = userDAO.getUserById(rs.getInt("user_id"));
        student.setUser(user);

        return student;
    }

    public void addUser(User user) {
        userDAO.addUser(user);
        System.out.println("Controller: Adding user " + user.getUserId());
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
        System.out.println("Controller: Updating user info for user " + user.getUserId());
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
        System.out.println("Controller: Deleting user " + userId);
    }
}
