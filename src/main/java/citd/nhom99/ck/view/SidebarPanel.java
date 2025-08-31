package citd.nhom99.ck.view;

import citd.nhom99.ck.model.Role;
import citd.nhom99.ck.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SidebarPanel extends JPanel {
    public SidebarPanel(DashboardFrame parent, User user) {
        setLayout(new GridLayout(8, 1, 0, 10));
        setBackground(new Color(50, 50, 70));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(welcomeLabel);

        addMenuButton("Trang chủ", parent);

        if (user.getRole() == Role.ADMIN) {
            addMenuButton("Quản lý Học sinh", parent);
            addMenuButton("Quản lý Giáo viên", parent);
            addMenuButton("Quản lý Lớp học", parent);
        } else if (user.getRole() == Role.TEACHER) {
            addMenuButton("Lớp học của tôi", parent);
            addMenuButton("Sinh viên của tôi", parent);
        } else if (user.getRole() == Role.STUDENT) {
            addMenuButton("Lớp học của tôi", parent);
            addMenuButton("Điểm số", parent);
        }

        addMenuButton("Tài khoản", parent);
        addMenuButton("Đăng xuất", parent, () -> {
            parent.dispose();
            new LoginFrame(new citd.nhom99.ck.controller.LMSController()).setVisible(true);
        });
    }

    private void addMenuButton(String text, DashboardFrame parent) {
        addMenuButton(text, parent, null);
    }

    private void addMenuButton(String text, DashboardFrame parent, Runnable action) {
        JButton btn = new JButton(text);
        btn.addActionListener(e -> {
            if (action != null) {
                action.run();
            } else {
                parent.updateMainContent(text);
            }
        });
        add(btn);
    }
}