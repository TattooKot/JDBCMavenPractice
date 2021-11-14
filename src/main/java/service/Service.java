package service;

import java.sql.PreparedStatement;

public class Service {


    private final Utils utils;

    public Service(Utils utils) {
        this.utils = utils;
    }

    public PreparedStatement getStatement(String sql) {
        return utils.getStatement(sql);
    }
}
