package com.mikadifo.models;

public enum SQL_Sintax {
    SELECT_ALL("SELECT * FROM ?"),
    SELECT_BY_COLUMN("SELECT * FROM ? WHERE ?"),
    SELECT_COLUMNS("SELECT ? FROM ?"),
    INSERT("INSERT INTO ? (?) VALUES(?)"),
    UPDATE("UPDATE ? SET ? WHERE ?"),
    DELETE("DELETE FROM ? WHERE ?");
    
    private String sintax;
    
    private SQL_Sintax(String sintax) {
        this.sintax = sintax;
    }

    public String getSintax() {
        return sintax;
    }
}
