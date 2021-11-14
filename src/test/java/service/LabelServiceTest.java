package service;

import model.Label;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.jdbc.JDBCLabelRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class LabelServiceTest {

    private final JDBCLabelRepositoryImpl repository = Mockito.mock(JDBCLabelRepositoryImpl.class);

    private LabelService labelService;

    @Before
    public void setUp(){
        labelService = new LabelService(repository);
    }

    @Test
    public void testGetAll() {
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(4, "Name"));
        labels.add(new Label(6, "Another label"));
        labels.add(new Label(7, "For natashkas post"));

        when(repository.getAll()).thenReturn(labels);
        List<Label> result = labelService.getAll();
        verify(repository, atLeastOnce()).getAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetById() {
        when(repository.getById(4)).thenReturn(new Label("Name"));
        Label byId = labelService.getById(4);
        assertNotNull(byId);
        assertEquals(repository.getById(4).getName(), byId.getName());

    }

    @Test
    public void testGetById_null() {
        given(repository.getById(100)).willReturn(null);
        Label byId = labelService.getById(100);
        assertNull(byId);
        assertNull(repository.getById(100));
    }

    @Test
    public void testCreate() {
        Label label = new Label("New label");
        when(repository.create(label)).thenReturn(label);
        Label created = labelService.create(label);

        verify(repository).create(label);
        assertNotNull(created);
        assertEquals(label, created);
    }

    @Test
    public void testUpdate() {
        Label label = new Label(4, "New Name");
        when(repository.update(label)).thenReturn(label);
        Label updated = labelService.update(label);

        verify(repository).update(label);
        assertNotNull(updated);
        assertEquals(label, updated);
    }

    @Test
    public void testDeleteById() {
        labelService.deleteById(4);
        verify(repository).deleteById(4);
    }
}