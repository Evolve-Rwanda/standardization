package com.example.springauth.models.app;


public class RolePrivilegeMap {


    private static int ID_COUNT = 0;
    private int id;
    private RoleModel role;
    private PrivilegeModel privilege;
    private String createdAt;

    public RolePrivilegeMap() {
    }

    public RolePrivilegeMap(RoleModel role, PrivilegeModel privilege, String createdAt) {
        this.id = ID_COUNT;
        this.role = role;
        this.privilege = privilege;
        this.createdAt = createdAt;
        ID_COUNT++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public PrivilegeModel getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeModel privilege) {
        this.privilege = privilege;
    }
}
