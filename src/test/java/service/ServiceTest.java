package service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class ServiceTest {
    private final Utils utils = Mockito.mock(Utils.class);
    private Service service;

    @Before
    public void setUp(){
        service = new Service(utils);
    }
    @Test
    public void testGetStatement() {
        service.getStatement(Requests.GET_ALL_LABELS.toString());
        verify(utils).getStatement(Requests.GET_ALL_LABELS.toString());
    }
}