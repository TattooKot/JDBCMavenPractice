package controller;

import model.Post;
import repository.jdbc.JDBCPostRepositoryImpl;
import service.PostService;

import java.util.List;

public class PostController {
    private final PostService service = new PostService(new JDBCPostRepositoryImpl());

    public List<Post> getAllPosts(){
        return service.getAll();
    }

    public Post getPostById(int id){
        return service.getById(id);
    }

    public Post createPost(Post post){
        return service.create(post);
    }

    public Post updatePost(Post post){
        return service.update(post);
    }

    public void deletePostById(int id){
        service.deleteById(id);
    }
}
