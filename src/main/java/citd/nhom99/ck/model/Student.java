package citd.nhom99.ck.model;

public class Student {
    private String studentId;
    private User user;
    private float averageGrade;

    public Student() {
    }

    public Student(String studentId, User user) {
        this.studentId = studentId;
        this.user = user;
    }

    public Student(String studentId, User user, float averageGrade) {
        this.studentId = studentId;
        this.user = user;
        this.averageGrade = averageGrade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user != null ? user : new User();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + user.getUserId() +
                "studentId='" + studentId + " " +
                ", user=" + user.getFullName() +
                "email=" + user.getEmail() +
                ", averageGrade=" + averageGrade +
                '}';
    }
}
