import org.junit.Test;
import service.DBConnection;
import service.Requests;

import static org.junit.Assert.assertNotNull;

public class DBConnectionTests {

    @Test
    public void testConnection() {
        assertNotNull(DBConnection.getConnection());
    }

    @Test
    public void testStatement(){
        assertNotNull(DBConnection.geStatement(Requests.GET_ALL_LABELS.toString()));
    }

}
