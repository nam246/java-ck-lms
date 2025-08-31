package citd.nhom99.ck.dao;

import citd.nhom99.ck.model.Teacher;
import citd.nhom99.ck.model.User;

import java.util.List;

public interface TeacherDAO {
    void addTeacher(User teacher);

    Teacher getTeacherById(String teacherId);

    List<Teacher> getAllTeachers();

    void updateTeacher(Teacher teacher);

    void deleteTeacher(String teacherId);
}
