package citd.nhom99.ck.dao.impl;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.dao.ClassroomDAO;
import citd.nhom99.ck.dao.StudentDAO;
import citd.nhom99.ck.dao.TeacherDAO;
import citd.nhom99.ck.model.Classroom;
import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAOImpl implements ClassroomDAO {

    private final TeacherDAO teacherDAO = new TeacherDAOImpl();
    private final StudentDAO studentDAO = new StudentDAOImpl();

    @Override
    public void addClassroom(Classroom classroom) {
        String sql = "INSERT INTO classrooms(class_id, class_name, gvcn_id) VALUES(?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classroom.getClassId());
            pstmt.setString(2, classroom.getClassName());
            pstmt.setString(3, classroom.getTeacherId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Classroom getClassroomById(String classroomId) {
        String sql = "SELECT * FROM classrooms WHERE class_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classroomId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractClassroomFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        String sql = "SELECT * FROM classrooms";
        List<Classroom> classrooms = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                classrooms.add(extractClassroomFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return classrooms;
    }

    @Override
    public void updateClassroom(Classroom classroom) {
        String sql = "UPDATE classrooms SET class_name = ?, gvcn_id = ? WHERE class_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classroom.getClassName());
            pstmt.setString(2, classroom.getTeacherId());
            pstmt.setString(3, classroom.getClassId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteClassroom(String classroomId) {
        String sql = "DELETE FROM classrooms WHERE class_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classroomId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Classroom extractClassroomFromResultSet(ResultSet rs) throws SQLException {
        String classId = rs.getString("class_id");
        String className = rs.getString("class_name");
        String gvcnId = rs.getString("gvcn_id");

        Teacher gvcn = teacherDAO.getTeacherById(gvcnId);
        // The constructor for Classroom requires students, but we load them separately to avoid circular dependencies.
        // We can load them here, or the service layer can be responsible for it.
        // For now, we will load them here.
        ArrayList<Student> students = getStudentsForClassroom(classId);

        return new Classroom(classId, className, gvcn, students);
    }

    private ArrayList<Student> getStudentsForClassroom(String classId) {
        String sql = "SELECT s.* FROM students s JOIN classroom_student cs ON s.user_id = cs.student_user_id WHERE cs.class_id = ?";
        ArrayList<Student> students = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(studentDAO.getStudentById(rs.getString("student_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }
}
