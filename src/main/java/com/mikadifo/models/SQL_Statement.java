package com.mikadifo.models;

public interface SQL_Statement {
    
    public boolean selectAll();
    
    public boolean selectById();
    
    public boolean insert();
    
    public boolean update();
    
    public boolean delete();
    
}
