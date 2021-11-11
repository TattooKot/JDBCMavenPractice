package controller;

import junit.framework.TestCase;
import model.Writer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class WriterControllerTest extends TestCase {

    @Spy
    WriterController controller = new WriterController();

    @Test
    public void testGetAllWriters() {
        List<Writer> writers = controller.getAllWriters();
        when(controller.getAllWriters()).thenReturn(writers);
    }

    @Test
    public void testGetWriterById() {
        Writer writer = controller.getWriterById(1);
        when(controller.getWriterById(1)).thenReturn(writer);
    }

    @Test
    public void testCreateWriter() {
        Writer writer = new Writer();
        writer.setFirstName("Name");
        writer.setLastName("Surname");
        writer.setPosts(new ArrayList<>());

        Writer created = controller.createWriter(writer);
        when(controller.createWriter(writer)).thenReturn(created);
    }

    @Test
    public void testUpdateWriter() {
        Writer writer = controller.getWriterById(1);
        writer.setPosts(new ArrayList<>());
        Writer updated = controller.updateWriter(writer);

        when(controller.updateWriter(updated)).thenReturn(updated);
    }

    @Test
    public void testDeleteWriterById() {
        int id = 1;
        doNothing().when(controller).deleteWriterById(id);
        when(controller.getWriterById(id)).thenReturn(null);
    }
}