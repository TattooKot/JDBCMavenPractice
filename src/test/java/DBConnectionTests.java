import org.junit.Test;
import org.mockito.Mock;
import service.DBConnection;
import service.Requests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;

public class DBConnectionTests {

    public DBConnectionTests() throws SQLException {
    }

    @Test
    public void testConnection() {
        assertNotNull(DBConnection.getConnection());
    }

    @Test
    public void testStatement(){
        assertNotNull(DBConnection.geStatement(Requests.GET_ALL_LABELS.toString()));
    }

    @Mock
    Connection connection = DBConnection.getConnection();

    @Test
    public void mockitoNotNullConnection(){
        assertNotNull(connection);
    }

    @Mock
    Statement statement = connection.prepareStatement(Requests.GET_ALL_LABELS.toString());

    @Test
    public void mockitoNotNullStatement(){
        assertNotNull(statement);
    }

}
