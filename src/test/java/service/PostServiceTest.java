package service;

import model.Post;
import model.PostStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.jdbc.JDBCPostRepositoryImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    private final JDBCPostRepositoryImpl repository = Mockito.mock(JDBCPostRepositoryImpl.class);
    private PostService service;

    @Before
    public void setUp(){
        service = new PostService(repository);
    }

    @Test
    public void testGetAll() {
        Post post = getPostForTest();

        List<Post> posts = Collections.singletonList(post);

        when(repository.getAll()).thenReturn(posts);
        List<Post> servicePosts = service.getAll();

        verify(repository).getAll();
        assertNotNull(servicePosts);
        assertEquals(posts, servicePosts);
    }

    @Test
    public void testGetById() {
        Post post = getPostForTest();
        when(repository.getById(1)).thenReturn(post);
        Post byId = service.getById(1);
        assertNotNull(byId);
        assertEquals(post,byId);
    }

    @Test
    public void testCreate() {
        Post post = getPostForTest();
        when(repository.create(post)).thenReturn(post);
        Post created = service.create(post);

        verify(repository).create(post);
        assertNotNull(created);
        assertEquals(post,created);
    }

    @Test
    public void testUpdate() {
        Post post = getPostForTest();
        when(repository.update(post)).thenReturn(post);
        Post updated = service.update(post);

        verify(repository).update(post);
        assertNotNull(updated);
        assertEquals(post,updated);
    }

    @Test
    public void testDeleteById() {
        service.deleteById(1);
        verify(repository).deleteById(1);
    }

    private Post getPostForTest(){
        Post post = new Post();
        post.setId(1);
        post.setContent("Post from console\nTest\n");
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setStatus(PostStatus.DELETED);
        return post;
    }
}