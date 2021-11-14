package service;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ServiceTest {
    private final Utils utils = Mockito.mock(Utils.class);
    private final PreparedStatement statement = Mockito.mock(PreparedStatement.class);

    @Test
    public void testGetStatement() {
        when(utils.getStatement(anyString())).thenReturn(statement);
    }
}