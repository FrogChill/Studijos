public class Dalykas {
    private int idDalykas;
    private String dalykoPavadinimas;

    public Dalykas(int idDalykas, String dalykoPavadinimas) {
        this.idDalykas = idDalykas;
        this.dalykoPavadinimas = dalykoPavadinimas;
    }

    public int getIdDalykas() {
        return idDalykas;
    }

    public String getDalykoPavadinimas() {
        return dalykoPavadinimas;
    }

    @Override
    public String toString() {
        return "Dalykai{" +
                "idDalykas=" + idDalykas +
                ", dalykoPavadinimas='" + dalykoPavadinimas + '\'' +
                '}';
    }
}