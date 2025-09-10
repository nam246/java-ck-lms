package citd.nhom99.ck.controller;

import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.dao.StudentDAO;

import java.util.List;

public class StudentController {
    private StudentDAO studentDAO = new StudentDAO();

    public StudentController() {
    }

    public List<Student> getAllStudents() {
        return this.studentDAO.getAllStudents();
    }

    public void deleteStudent(int id) {
        if (id <= 0) {
            System.out.println("Controller: Invalid student id");
            return;
        }
        studentDAO.deleteStudent(id);
        System.out.println("Controller: Deleting student " + id);
    }


}
