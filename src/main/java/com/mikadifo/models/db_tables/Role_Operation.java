package com.mikadifo.models.db_tables;

public class Role_Operation {

    public final String TABLE = "\"Role_Operations\"";
    public final String COLUMNS = "role_id, operation_module_id";
    public final String ATTRIBUTES = "?, ?";

    private int id;
    private short roleId;
    private int operationModuleId;

    public Role_Operation() { }

    public Role_Operation(int id, short roleId, int operationModuleId) {
        this.id = id;
        this.roleId = roleId;
        this.operationModuleId = operationModuleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
    }

    public int getOperationModuleId() {
        return operationModuleId;
    }

    public void setOperationModuleId(int operationModuleId) {
        this.operationModuleId = operationModuleId;
    }

}

