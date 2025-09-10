package citd.nhom99.ck.model.dao;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.model.Classroom;
import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.utils.QueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {

    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public void addClassroom(Classroom classroom) {
        QueryHelper.insertClassroom(classroom);
    }

    public Classroom getClassroomById(int id) {
        String sql = "SELECT * FROM classrooms WHERE class_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractClassroomFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

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

    private Classroom extractClassroomFromResultSet(ResultSet rs) throws SQLException {

        int classId = rs.getInt("class_id");
        String className = rs.getString("class_name");
        int gvcnId = rs.getInt("gvcn_id");

        return new Classroom(classId, className, gvcnId);
    }

    public void updateClassroom(Classroom classroom) {
//        String sql = "UPDATE classrooms SET class_name = ?, gvcn_id = ? WHERE class_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, classroom.getClassName());
//            pstmt.setString(2, classroom.getTeacherId());
//            pstmt.setString(3, classroom.getClassId());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }

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

    private ArrayList<Student> getStudentsForClassroom(String classId) {
        String sql = "SELECT s.* FROM students s JOIN classroom_student cs ON s.user_id = cs.student_user_id WHERE cs.class_id = ?";
        ArrayList<Student> students = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(studentDAO.getStudentByCode(rs.getString("student_code")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }
}