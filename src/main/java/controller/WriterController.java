package controller;

import model.Writer;
import repository.jdbc.JDBCWriterRepositoryImpl;
import service.WriterService;

import java.util.List;

public class WriterController {
    private final WriterService repository = new WriterService(new JDBCWriterRepositoryImpl());

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
