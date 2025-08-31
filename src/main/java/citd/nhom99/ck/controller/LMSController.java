package citd.nhom99.ck.controller;

import citd.nhom99.ck.dao.UserDAO;
import citd.nhom99.ck.dao.StudentDAO;
import citd.nhom99.ck.dao.impl.StudentDAOImpl;
import citd.nhom99.ck.dao.impl.UserDAOImpl;
import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.User;

import java.util.List;

public class LMSController {
    private final UserDAO userDAO;

    public LMSController() {
        this.userDAO = new UserDAOImpl();
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<Student> getAllStudents() {
        StudentDAO StudentDAO = new StudentDAOImpl();
        return StudentDAO.getAllStudents();
    }
}