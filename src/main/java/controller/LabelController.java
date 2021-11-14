package controller;

import model.Label;
import repository.jdbc.JDBCLabelRepositoryImpl;
import service.LabelService;

import java.util.List;
import java.util.Objects;

public class LabelController {
    private final LabelService service = new LabelService(new JDBCLabelRepositoryImpl());

    public List<Label> getAllLabels(){
        return service.getAll();
    }

    public Label getLabelById(int id){
        return service.getById(id);
    }

    public Label createLabel(String name){
        return service.create(new Label(name));
    }

    public Label updateLabel(Label label){
        return service.update(label);
    }

    public boolean deleteLabelById(int id){
        if(Objects.isNull(getLabelById(id))){
            return false;
        }
        service.deleteById(id);
        return true;
    }



}
