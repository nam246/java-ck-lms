package citd.nhom99.ck.model;

public class StudentGrade {

    private int id;
    private double regularGrade;
    private double midtermGrade;
    private double finalGrade;
    private double averageGrade;
    private String classified;
    private int academicYear;
    private int studentId;
    private Student student;
    private int subjectId;
    private Subject subject;

    public StudentGrade() {
    }

    public StudentGrade(double regularGrade, double midtermGrade, double finalGrade, double averageGrade, String classified, int academicYear, int studentId, Subject subject) {
        this.regularGrade = regularGrade;
        this.midtermGrade = midtermGrade;
        this.finalGrade = finalGrade;
        this.averageGrade = averageGrade;
        this.classified = classified;
        this.academicYear = academicYear;
        this.studentId = studentId;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(double regularGrade) {
        this.regularGrade = regularGrade;
    }

    public double getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(double midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getClassified() {
        return classified;
    }

    public void setClassified(String classified) {
        this.classified = classified;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "StudentGrade{" + "id=" + id + ", regularGrade=" + regularGrade + ", midtermGrade=" + midtermGrade + ", finalGrade=" + finalGrade + ", averageGrade=" + averageGrade + ", classified=" + classified + ", academicYear=" + academicYear + ", studentId=" + studentId + ", student=" + student + ", subjectId=" + subjectId + ", subject=" + subject + '}';
    }
}
