package io.github.ldhai99.easyTransaction;

import java.sql.Connection;

public class DbBase {

    protected Connection con;
    public DbBase() {

    }
    public DbBase(Connection con) {
        this.con = con;

    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }


}
