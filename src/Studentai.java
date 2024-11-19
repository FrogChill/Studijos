public class Studentai {
    private int StudentID;
    private String Stud_name;
    private String Stud_surname;
    public Studentai(int StudentID, String Stud_name, String Stud_surname) {
        this.StudentID = StudentID;
        this.Stud_name = Stud_name;
        this.Stud_surname = Stud_surname;
    }
    public int getStudentID() {
        return StudentID;
    }
    public String getStud_name() {
        return Stud_name;
    }
    public String getStud_surname() {
        return Stud_surname;
    }
    public String toString() {
        return "Student{" +
                "studentId=" + StudentID +
                ", firstName='" + Stud_name + '\'' +
                ", lastName='" + Stud_surname + '\'' +
                '}';
    }
}
