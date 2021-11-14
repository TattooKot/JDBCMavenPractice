package service;

import model.Label;
import repository.jdbc.JDBCLabelRepositoryImpl;

import java.util.List;

public class LabelService {
    private final JDBCLabelRepositoryImpl repository;

    public LabelService(JDBCLabelRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Label> getAll(){
        return repository.getAll();
    }

    public Label getById(int id){
        return repository.getById(id);
    }

    public Label create(Label label){
        return repository.create(label);
    }

    public Label update(Label label){
        return repository.update(label);
    }

    public void deleteById(int id){
        repository.deleteById(id);
    }
}
