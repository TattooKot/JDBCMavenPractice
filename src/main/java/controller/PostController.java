package controller;

import model.Post;
import repository.jdbc.JDBCPostRepositoryImpl;

import java.util.List;

public class PostController {
    private final JDBCPostRepositoryImpl repository = new JDBCPostRepositoryImpl();

    public List<Post> getAllPosts(){
        return repository.getAll();
    }

    public Post getPostById(int id){
        return repository.getById(id);
    }

    public Post createPost(Post post){
        return repository.create(post);
    }
}
