package controller;

import model.Label;
import repository.jdbc.JDBCTagRepositoryImpl;

import java.util.List;
import java.util.Objects;

public class LabelController {
    private final JDBCTagRepositoryImpl repository = new JDBCTagRepositoryImpl();

    public List<Label> getAllLabels(){
        return repository.getAll();
    }

    public Label getLabelById(int id){
        return repository.getById(id);
    }

    public Label createLabel(String name){
        return repository.create(new Label(name));
    }

    public Label updateLabel(Label label){
        return repository.update(label);
    }

    public boolean deleteLabelById(int id){
        if(Objects.isNull(getLabelById(id))){
            return false;
        }
        repository.deleteById(id);
        return true;
    }



}
