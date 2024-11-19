public class Admin {
    private int AdminID;
    private String AdminName;
    private String AdminSurname;
    public Admin(int AdminID, String AdminName, String AdminSurname) {
        this.AdminID = AdminID;
        this.AdminName = AdminName;
        this.AdminSurname = AdminSurname;
    }
    public int getAdminID() {
        return AdminID;
    }
    public String getAdminName() {
        return AdminName;
    }
    public String getAdminSurname() {
        return AdminSurname;
    }
    public String toString() {
        return "Admin{" +
                "AdID=" + AdminID +
                ", Adname='" + AdminName + '\'' +
                ", AdSurname='" + AdminSurname + '\'' +
                '}';
    }
}
