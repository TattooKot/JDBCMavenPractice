package controller;

import model.Writer;
import repository.jdbc.JDBCWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final JDBCWriterRepositoryImpl repository = new JDBCWriterRepositoryImpl();

    public List<Writer> getAllWriters(){
        return repository.getAll();
    }

    public Writer getWriterById(int id){
        return repository.getById(id);
    }

    public Writer createWriter(Writer writer){
        return repository.create(writer);
    }

    public Writer updateWriter(Writer writer){
        return repository.update(writer);
    }

    public void deleteWriterById(int id){
        repository.deleteById(id);
    }
}
