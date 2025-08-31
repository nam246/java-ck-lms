package citd.nhom99.ck.dao;

import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.User;

import java.util.List;

public interface StudentDAO {
    void addStudent(User student);

    Student getStudentById(String studentId);

    List<Student> getAllStudents();

    void updateStudent(Student student);

    void deleteStudent(int studentId);
}
