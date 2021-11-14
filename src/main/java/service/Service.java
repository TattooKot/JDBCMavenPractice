package service;

import java.sql.PreparedStatement;

public class Service {
    private final Utils utils = new Utils();

    public PreparedStatement getStatement(String sql) {
        return utils.getStatement(sql);
    }
}
