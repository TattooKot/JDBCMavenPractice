package service;

import model.Writer;
import repository.jdbc.JDBCWriterRepositoryImpl;

import java.util.List;

public class WriterService {
    public WriterService(JDBCWriterRepositoryImpl repository) {
        this.repository = repository;
    }

    private final JDBCWriterRepositoryImpl repository;

    public List<Writer> getAll(){
        return repository.getAll();
    }

    public Writer getById(int id){
        return repository.getById(id);
    }

    public Writer create(Writer writer){
        return repository.create(writer);
    }

    public Writer update(Writer writer){
        return repository.update(writer);
    }

    public void deleteById(int id){
        repository.deleteById(id);
    }
}
