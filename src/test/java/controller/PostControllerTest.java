package controller;

import model.Post;
import model.PostStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PostControllerTest {

    @Spy
    PostController controller = new PostController();


    @Test
    public void testGetAllPosts() {
        List<Post> postList = controller.getAllPosts();
        when(controller.getAllPosts()).thenReturn(postList);
    }

    @Test
    public void testGetPostById() {
        Post post = controller.getPostById(3);
        when(controller.getPostById(3))
                .thenReturn(post);

    }

    @Test
    public void testGetPostById_null() {
        when(controller.getPostById(15000))
                .thenReturn(null);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setContent(anyString());
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setStatus(PostStatus.UNDER_REVIEW);
        post.setLabels(new ArrayList<>());

        Post created = controller.createPost(post);
        when(controller.createPost(post))
                .thenReturn(created);
    }


    @Test
    public void testUpdatePost() {
        Post post = controller.getPostById(4);
        post.setStatus(PostStatus.ACTIVE);
        Post updatedPost = controller.updatePost(post);

        when(controller.updatePost(updatedPost)).thenReturn(updatedPost);
    }

    @Test
    public void testDeletePostById() {
        int id = 9;
        doNothing().when(controller).deletePostById(id);
        when(controller.getPostById(id)).thenReturn(null);
    }
}