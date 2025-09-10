package citd.nhom99.ck.utils;

import citd.nhom99.ck.model.Role;

public class Helper {
    public static boolean isAdmin(Role role) {
        return role == Role.ADMIN;
    }

    public static String codeGenerate(int id, Role role) {
        String code = "";
        switch (role) {
            case STUDENT:
                code = "HS" + String.format("%06d", id);
                break;
            case TEACHER:
                code = "GV" + String.format("%06d", id);
                break;
        }
        return code;
    }
}
