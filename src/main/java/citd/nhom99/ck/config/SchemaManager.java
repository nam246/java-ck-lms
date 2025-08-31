package citd.nhom99.ck.config;

import java.sql.Connection;
/* Các phương thức của Connection:
 * Quản lý kết nối
 * - createStatement(): tạo câu lệnh giúp chạy câu lệnh SQL tĩnh
 * - prepareStatement(String sql): tạo câu lệnh giúp chạy câu lệnh SQL có tham số
 * Giao dịch (transaction)
 * Tạo statement để thực thi SQL
 * Lấy thông tin về DB
 * */
import java.sql.SQLException;
import java.sql.Statement;
/* Các phương thức của Statement:
 * - executeQuery(String sql): Thực thi câu lệnh SELECT và trả về ResultSet.
 * - executeUpdate(String sql): Thực thi INSERT, UPDATE, DELETE hoặc DDL (CREATE, DROP), trả về số dòng bị ảnh hưởng.
 * - execute(String sql): Thực thi câu lệnh SQL bất kỳ, trả về boolean: true nếu có ResultSet, false nếu không.
 * */

public class SchemaManager {

    private static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                full_name TEXT NOT NULL,
                phone_number TEXT,
                email TEXT UNIQUE,
                gender TEXT CHECK(gender IN ('MALE', 'FEMALE')),
                role TEXT NOT NULL CHECK(role IN ('STUDENT', 'TEACHER', 'ADMIN'))
            );
            """;

    private static final String CREATE_STUDENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS students (
                user_id INTEGER PRIMARY KEY,
                student_id TEXT NOT NULL,
                average_grade REAL DEFAULT 0.0,
                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
            );
            """;

    private static final String CREATE_TEACHERS_TABLE = """
            CREATE TABLE IF NOT EXISTS teachers (
                user_id INTEGER PRIMARY KEY,
                teacher_id TEXT NOT NULL,
                subject TEXT,
                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
            );
            """;

    private static final String CREATE_CLASSROOMS_TABLE = """
            CREATE TABLE IF NOT EXISTS classrooms (
                class_id INTEGER PRIMARY KEY AUTOINCREMENT,
                class_name TEXT NOT NULL,
                gvcn_id INTEGER,
                FOREIGN KEY (gvcn_id) REFERENCES teachers(user_id)
            );
            """;

    private static final String CREATE_CLASSROOM_STUDENT_TABLE = """
            CREATE TABLE IF NOT EXISTS classroom_student (
                class_id INT NOT NULL,
                student_user_id INTEGER NOT NULL,
                PRIMARY KEY (class_id, student_user_id),
                FOREIGN KEY (class_id) REFERENCES classrooms(class_id) ON DELETE CASCADE,
                FOREIGN KEY (student_user_id) REFERENCES students(user_id) ON DELETE CASCADE
            );
            """;


    public static void initializeDatabase() {
        System.out.println("Initializing database schema based on models...");
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            // Execute each CREATE TABLE statement
            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_STUDENTS_TABLE);
            stmt.execute(CREATE_TEACHERS_TABLE);
            stmt.execute(CREATE_CLASSROOMS_TABLE);
            stmt.execute(CREATE_CLASSROOM_STUDENT_TABLE);

            System.out.println("Database schema initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database schema:");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }
}
