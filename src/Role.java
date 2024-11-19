import java.util.List;

public class Role {
    private String roleName;
    private List<Permission> permissions;

    public Role(String roleName, List<Permission> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    // Check if this role has a specific permission
    public boolean hasPermission(String permissionName) {
        for (Permission permission : permissions) {
            if (permission.getPermissionName().equals(permissionName)) {
                return true;
            }
        }
        return false;
    }
}
