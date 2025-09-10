package citd.nhom99.ck.controller;

import citd.nhom99.ck.model.dao.UserDAO;
import citd.nhom99.ck.model.User;
import citd.nhom99.ck.view.DashboardFrame;
import citd.nhom99.ck.view.LoginFrame;

public class AppController {
    private final UserDAO userDAO;
    private LoginFrame loginFrame;
    private DashboardFrame dashboardFrame;

    public AppController() {
        this.userDAO = new UserDAO();
    }

    public void start() {
        this.loginFrame = new LoginFrame(this);
        this.loginFrame.setVisible(true);
    }

    public void login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            loginFrame.dispose();
            this.dashboardFrame = new DashboardFrame(user, this);
            this.dashboardFrame.setVisible(true);
        } else {
            loginFrame.showLoginError();
        }
    }

    public void logout() {
        dashboardFrame.dispose();
        start(); // Show the login frame again
    }

    public void addUser(User user) {
        userDAO.addUser(user);
        System.out.println("Controller: Adding user " + user.getUserId());
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
        System.out.println("Controller: Updating user info for user " + user.getUserId());
    }
}