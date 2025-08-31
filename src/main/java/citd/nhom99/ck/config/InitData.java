package citd.nhom99.ck.config;

import citd.nhom99.ck.model.Gender;
import citd.nhom99.ck.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitData {

    /**
     * Inserts sample data into the database only if the database is empty.
     */
    public static void seedDatabaseIfEmpty() {
        if (isDatabaseEmpty()) {
            System.out.println("Database is empty. Seeding with sample data...");
            insertSampleData();
        } else {
            System.out.println("Database already contains data. Skipping seeding.");
        }
    }

    private static boolean isDatabaseEmpty() {
        String sql = "SELECT COUNT(user_id) FROM users";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            // This might happen if the table doesn't exist yet, which is fine.
            // We assume it's empty.
            return true;
        }
        return false;
    }

    private static void insertSampleData() {
        String insertUserSQL = "INSERT INTO users(username, password, full_name, phone_number, email, gender, role) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String insertTeacherSQL = "INSERT INTO teachers(user_id, teacher_id, subject) VALUES(?, ?, ?)";
        String insertStudentSQL = "INSERT INTO students(user_id, student_id, average_grade) VALUES(?, ?, ?)";
        String insertClassroomSQL = "INSERT INTO classrooms(class_name, gvcn_id) VALUES(?, ?)";
        String insertClassroomStudentSQL = "INSERT INTO classroom_student(class_id, student_user_id) VALUES(?, ?)";

        try (Connection conn = DBConfig.getConnection()) {
            // Disable auto-commit for transaction
            conn.setAutoCommit(false);

            try (PreparedStatement userPstmt = conn.prepareStatement(insertUserSQL);
                 PreparedStatement teacherPstmt = conn.prepareStatement(insertTeacherSQL);
                 PreparedStatement studentPstmt = conn.prepareStatement(insertStudentSQL);
                 PreparedStatement classroomPstmt = conn.prepareStatement(insertClassroomSQL);
                 PreparedStatement classroomStudentPstmt = conn.prepareStatement(insertClassroomStudentSQL)) {

                // Admin
                userPstmt.setString(1, "admin");
                userPstmt.setString(2, "admin");
                userPstmt.setString(3, "Admin");
                userPstmt.setString(4, "090111222");
                userPstmt.setString(5, "admin@edu.vn");
                userPstmt.setString(6, Gender.MALE.name());
                userPstmt.setString(7, Role.ADMIN.name());
                userPstmt.addBatch();

                // Teacher 1
                userPstmt.setString(1, "gv.an");
                userPstmt.setString(2, "password123");
                userPstmt.setString(3, "Nguyễn Văn An");
                userPstmt.setString(4, "090111222");
                userPstmt.setString(5, "an.nv@edu.vn");
                userPstmt.setString(6, Gender.MALE.name());
                userPstmt.setString(7, Role.TEACHER.name());
                userPstmt.addBatch();

                teacherPstmt.setInt(1, 2);
                teacherPstmt.setString(2, "GV001");
                teacherPstmt.setString(3, "Toán cao cấp");
                teacherPstmt.addBatch();

                // Teacher 2
                userPstmt.setString(1, "gv.binh");
                userPstmt.setString(2, "password123");
                userPstmt.setString(3, "Trần Thị Bình");
                userPstmt.setString(4, "090333444");
                userPstmt.setString(5, "binh.tt@edu.vn");
                userPstmt.setString(6, Gender.FEMALE.name());
                userPstmt.setString(7, Role.TEACHER.name());
                userPstmt.addBatch();

                teacherPstmt.setInt(1, 3);
                teacherPstmt.setString(2, "GV002");
                teacherPstmt.setString(3, "Lập trình Java");
                teacherPstmt.addBatch();

                // Student 1
                userPstmt.setString(1, "sv.cuong");
                userPstmt.setString(2, "password123");
                userPstmt.setString(3, "Lê Văn Cường");
                userPstmt.setString(4, "091555666");
                userPstmt.setString(5, "cuong.lv@student.edu.vn");
                userPstmt.setString(6, Gender.MALE.name());
                userPstmt.setString(7, Role.STUDENT.name());
                userPstmt.addBatch();

                studentPstmt.setInt(1, 4);
                studentPstmt.setString(2, "SV001");
                studentPstmt.setDouble(3, 8.5);
                studentPstmt.addBatch();

                // Student 2
                userPstmt.setString(1, "sv.dung");
                userPstmt.setString(2, "password123");
                userPstmt.setString(3, "Mai Thị Dung");
                userPstmt.setString(4, "091777888");
                userPstmt.setString(5, "dung.mt@student.edu.vn");
                userPstmt.setString(6, Gender.FEMALE.name());
                userPstmt.setString(7, Role.STUDENT.name());
                userPstmt.addBatch();

                studentPstmt.setInt(1, 5);
                studentPstmt.setString(2, "SV002");
                studentPstmt.setDouble(3, 7.2);
                studentPstmt.addBatch();

                // Execute batch inserts for users, teachers, students
                userPstmt.executeBatch();
                teacherPstmt.executeBatch();
                studentPstmt.executeBatch();

                // Insert Classrooms
                classroomPstmt.setString(1, "Lớp Toán cao cấp 2025");
                classroomPstmt.setInt(2, 2); // GV An
                classroomPstmt.addBatch();

                classroomPstmt.setString(1, "Lớp Lập trình Java 2025");
                classroomPstmt.setInt(2, 3); // GV Binh
                classroomPstmt.addBatch();

                classroomPstmt.executeBatch();

                // Assign students to classrooms
                // Assign student Cuong (user_id 4) to class 1
                classroomStudentPstmt.setInt(1, 1);
                classroomStudentPstmt.setInt(2, 4);
                classroomStudentPstmt.addBatch();

                // Assign student Dung (user_id 5) to class 2
                classroomStudentPstmt.setInt(1, 2);
                classroomStudentPstmt.setInt(2, 5);
                classroomStudentPstmt.addBatch();

                classroomStudentPstmt.executeBatch();

                // Commit the transaction
                conn.commit();
                System.out.println("Sample data inserted successfully.");

            } catch (SQLException e) {
                // Rollback in case of an error
                conn.rollback();
                System.err.println("Error inserting sample data. Transaction was rolled back.");
//                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}