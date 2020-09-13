package com.mikadifo.models.db_tables;

public class Operation_Module {

    private int id;
    private int operationId;
    private int moduleId;

    public Operation_Module() { }

    public Operation_Module(int id, int operationId, int moduleId) {
        this.id = id;
        this.operationId = operationId;
        this.moduleId = moduleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

}

