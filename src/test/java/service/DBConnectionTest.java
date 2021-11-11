package service;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DBConnectionTest  {


    @Test
    public void testGetConnection() {
        assertNotNull(DBConnection.getConnection());
    }

    @Test
    public void testGetStatement() {
        assertNotNull(DBConnection.getStatement(Requests.GET_ALL_LABELS.toString()));
    }
}