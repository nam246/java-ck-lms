package citd.nhom99.ck.utils;

import citd.nhom99.ck.config.DBConfig;
import citd.nhom99.ck.model.Gender;
import citd.nhom99.ck.model.Role;
import citd.nhom99.ck.model.Student;
import citd.nhom99.ck.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query {
    public Query() {
    }

    public static void addUserByRole(User newUser, Role role, String sql) {
        Connection conn = null;
        try {
            conn = DBConfig.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Thêm user trước
            String addUserSql = "INSERT INTO users(username, password, full_name, phone_number, email, gender, role) VALUES(?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(addUserSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, newUser.getUsername());
                pstmt.setString(2, newUser.getPassword());
                pstmt.setString(3, newUser.getFullName());
                pstmt.setString(4, newUser.getPhoneNumber());
                pstmt.setString(5, newUser.getEmail());
                pstmt.setString(6, newUser.getGender().name());
                pstmt.setString(7, role.name());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            newUser.setUserId(generatedKeys.getInt(1));
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
            }

            // Sau đó thêm teacher
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "GV" + newUser.getUserId());
                pstmt.setInt(2, newUser.getUserId());
                pstmt.setString(3, "");
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit transaction nếu thành công
            System.out.println("New user added successfully with ID:" + newUser.getUserId());

        } catch (SQLException e) {
            // Rollback nếu có lỗi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    System.err.println("Error during rollback: " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Failed to add teacher: " + e.getMessage(), e);
        } finally {
            // Restore auto-commit và đóng connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public static void deleteUser(int userId) {
        try (Connection conn = DBConfig.getConnection();) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTeacher(int userId) {

    }
}
