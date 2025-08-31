package citd.nhom99.ck.dao;

import citd.nhom99.ck.model.Classroom;
import java.util.List;

public interface ClassroomDAO {
    void addClassroom(Classroom classroom);
    Classroom getClassroomById(String classroomId);
    List<Classroom> getAllClassrooms();
    void updateClassroom(Classroom classroom);
    void deleteClassroom(String classroomId);
}
