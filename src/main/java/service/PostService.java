package service;

import model.Post;
import repository.jdbc.JDBCPostRepositoryImpl;

import java.util.List;

public class PostService {
    private final JDBCPostRepositoryImpl repository;

    public PostService(JDBCPostRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Post> getAll(){
        return repository.getAll();
    }

    public Post getById(int id){
        return repository.getById(id);
    }

    public Post create(Post post){
        return repository.create(post);
    }

    public Post update(Post post){
        return repository.update(post);
    }

    public void deleteById(int id){
        repository.deleteById(id);
    }
}
