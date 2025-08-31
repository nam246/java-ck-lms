package citd.nhom99.ck.dao;

import citd.nhom99.ck.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    User getUserById(int userId);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(int userId);

    boolean isUsernameExists(String username);
}
