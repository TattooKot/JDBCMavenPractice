package service;

import model.Writer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.jdbc.JDBCWriterRepositoryImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WriterServiceTest {
    private final JDBCWriterRepositoryImpl repository = Mockito.mock(JDBCWriterRepositoryImpl.class);
    private WriterService service;

    @Before
    public void setUp(){
        service = new WriterService(repository);
    }

    @Test
    public void testGetAll() {
        Writer writer = getWriterForTest();
        List<Writer> writerList = Collections.singletonList(writer);
        when(repository.getAll()).thenReturn(writerList);

        List<Writer> serviceWriters = service.getAll();
        verify(repository).getAll();
        assertNotNull(serviceWriters);
        assertFalse(serviceWriters.isEmpty());
        assertEquals(serviceWriters, writerList);
    }

    @Test
    public void testGetById() {
        Writer writer = getWriterForTest();
        when(repository.getById(1)).thenReturn(writer);
        Writer byId = service.getById(1);

        verify(repository).getById(1);
        assertNotNull(byId);
        assertEquals(writer, byId);
    }

    @Test
    public void testCreate() {
        Writer writer = getWriterForTest();
        when(repository.create(writer)).thenReturn(writer);
        Writer created = service.create(writer);

        verify(repository).create(writer);
        assertNotNull(created);
        assertEquals(writer,created);
    }

    @Test
    public void testUpdate() {
        Writer writer = getWriterForTest();
        when(repository.update(writer)).thenReturn(writer);
        Writer updated = service.update(writer);

        verify(repository).update(writer);
        assertNotNull(updated);
        assertEquals(writer,updated);
    }

    @Test
    public void testDeleteById() {
        service.deleteById(1);
        verify(repository).deleteById(1);
    }

    public Writer getWriterForTest(){
        Writer writer = new Writer();
        writer.setId(1);
        writer.setFirstName("Name");
        writer.setLastName("LastName");
        return writer;
    }
}