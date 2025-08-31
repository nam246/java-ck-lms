package citd.nhom99.ck.model;

import java.util.ArrayList;

public class Teacher {

    private String teacherId;
    private User user;
    private String subject;
    private ArrayList<Classroom> classrooms;

    public Teacher() {
    }

    public Teacher(String teacherId, User user, String subject) {
        this.teacherId = teacherId;
        this.user = user;
        this.subject = subject;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(ArrayList<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    @Override
    public String toString() {
        return "Teacher: " +
                "teacherId='" + teacherId  +
                ", user=" + user.getFullName() +
                ", subject='" + subject;
    }
}
