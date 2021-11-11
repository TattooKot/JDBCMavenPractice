package controller;

import model.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LabelControllerTest {

    @Spy
    LabelController controller = new LabelController();


    @Test
    public void testGetAllLabels() {
        List<Label> labelList = controller.getAllLabels();
        when(controller.getAllLabels()).thenReturn(labelList);
    }

    @Test
    public void testGetLabelById() {
        Label label = controller.getLabelById(3);
        when(controller.getLabelById(3))
                .thenReturn(label);

    }

    @Test
    public void testGetLabelById_null() {
        when(controller.getLabelById(15000))
                .thenReturn(null);
    }

    @Test
    public void testCreateLabel() {
        String name = anyString();
        Label label = controller.createLabel(name);
        when(controller.createLabel(name))
                .thenReturn(label);
    }


    @Test
    public void testCreateLabel_null() {
        when(controller.createLabel(null))
                .thenReturn(null);
    }

    @Test
    public void testUpdateLabel() {
        Label label = controller.getLabelById(4);
        label.setName(anyString());
        Label updatedLabel = controller.updateLabel(label);

        when(controller.updateLabel(updatedLabel)).thenReturn(updatedLabel);
    }

    @Test
    public void testDeleteLabelById() {
        when(controller.deleteLabelById(8))
                .thenReturn(true);
    }
}