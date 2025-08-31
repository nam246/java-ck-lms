package citd.nhom99.ck.model;

import java.util.ArrayList;

public class Classroom {
    private String classId;
    private String className;
    private Teacher GVCN;
    private ArrayList<Teacher> teachers;
    private ArrayList<Student> students;

    public Classroom(String classId, String className, Teacher GVCN, ArrayList<Student> students) {
        this.classId = classId;
        this.className = className;
        this.GVCN = GVCN;
        this.students = students;
    }

    public Classroom(String classId, String className, Teacher GVCN, ArrayList<Teacher> teachers, ArrayList<Student> students) {
        this.classId = classId;
        this.className = className;
        this.GVCN = GVCN;
        this.teachers = teachers;
        this.students = students;
    }

    // Getters and setters
    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getTeacherId() {
        return GVCN.getTeacherId();
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void addStudent(String studentId) {

    }

    public void removeStudent(String studentId) {

    }

    public String toString() {
        return "Lớp" + getClassName() + ": " + "GVCN: " + GVCN.getTeacherId() + " Mon chu nhiem: " + GVCN.getSubject();
    }
}