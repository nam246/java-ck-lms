package citd.nhom99.ck.controller;

import citd.nhom99.ck.model.Classroom;
import citd.nhom99.ck.model.dao.ClassroomDAO;

import java.util.List;

public class ClassroomController {
    ClassroomDAO classroomDAO = new ClassroomDAO();

    public ClassroomController() {
    }

    public List<Classroom> getAllClassrooms() {
        return classroomDAO.getAllClassrooms();
    }

    public Classroom getClassroomById(int id) {
        return this.classroomDAO.getClassroomById(id);
    }
}
