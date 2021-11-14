package service;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceTest {
    private final ResultSet rs = mock(ResultSet.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);

    @Test
    public void testGetStatement() throws SQLException {
        Service serviceUnderTest = new Service();
        when(statement.executeQuery(Requests.GET_ALL_LABELS.toString())).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        PreparedStatement statementUnderTest = serviceUnderTest.getStatement(
                Requests.GET_ALL_LABELS.toString());

        ResultSet rsUnderTest = statementUnderTest.executeQuery();

        assertEquals(rs.next(), rsUnderTest.next());
    }
}