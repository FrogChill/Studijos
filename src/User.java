import java.util.List;

public class User {
    private String name;
    private String surname;
    private String role;
    private List<String> permissions;

    public User(String name, String surname, String role, List<String> permissions) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
