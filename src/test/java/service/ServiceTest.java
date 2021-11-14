package service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.PreparedStatement;

import static org.junit.Assert.assertNotNull;

public class ServiceTest {
    private final Utils utils = Mockito.mock(Utils.class);
    private Service service;

    @Before
    public void setUp(){
        service = new Service(utils);
    }
    @Test
    public void testGetStatement() {
        PreparedStatement statement = service.getStatement(Requests.GET_ALL_LABELS.toString());
        assertNotNull(statement);
    }
}