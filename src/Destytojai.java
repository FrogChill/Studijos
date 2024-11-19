public class Destytojai {
    private int destytojoID;
    private String destytojo_vardas;
    private String destytojo_pavarde;
    public Destytojai(int destytojoID, String destytojo_vardas, String destytojo_pavarde) {
        this.destytojoID = destytojoID;
        this.destytojo_vardas = destytojo_vardas;
        this.destytojo_pavarde = destytojo_pavarde;
}

    public int getdestytojoID() {
        return destytojoID;
    }
    public String getdestytojo_vardas() {
        return destytojo_vardas;
    }
    public String getdestytojo_pavarde() {
        return destytojo_pavarde;
    }
    public String toString() {
        return "Teacher{" +
                "TeacherID=" + destytojoID +
                ", firstName='" + destytojo_vardas + '\'' +
                ", lastName='" + destytojo_pavarde + '\'' +
                '}';
    }
}

