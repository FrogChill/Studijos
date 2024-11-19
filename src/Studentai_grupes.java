import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Studentai_grupes {

    public Map<String, List<Studentai>> assignGroups(Studentai[] students) {
        Map<String, List<Studentai>> groupedStudents = new HashMap<>();
        groupedStudents.put("Grupe A", new ArrayList<>());
        groupedStudents.put("Grupe B", new ArrayList<>());

        for (Studentai student : students) {
            if (student.getStudentID() % 2 == 0) {
                groupedStudents.get("Grupe B").add(student);
            } else {
                groupedStudents.get("Grupe A").add(student);
            }
        }

        return groupedStudents;
    }
    public static void main(String[] args) {
        Stud_if_priskirimas retrieve = new Stud_if_priskirimas();
        Studentai[] studentai = retrieve.getAllStudents();
        Studentai_grupes priskirimas = new Studentai_grupes();
        Map<String, List<Studentai>> groupedStudents = priskirimas.assignGroups(studentai);

        System.out.println("Grupe A");
        for (Studentai student : groupedStudents.get("Grupe A")) {
            System.out.println(student);
        }
        for (Studentai student : groupedStudents.get("Grupe B")) {
            System.out.println(student);
        }
    }
}
