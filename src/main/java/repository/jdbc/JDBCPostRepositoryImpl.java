package repository.jdbc;

import model.Label;
import model.Post;
import model.PostStatus;
import repository.PostRepository;
import service.Requests;
import service.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCPostRepositoryImpl implements PostRepository {
    private final Utils utils = new Utils();

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try(PreparedStatement statement = utils.getStatement(
                Requests.GET_ALL_POSTS.toString())) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                posts.add(getPostFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post getById(Integer id) {

        try(PreparedStatement statement = utils.getStatement
                (Requests.GET_POST_BY_ID.toString()))
        {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return getPostFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post create(Post post) {

        try(PreparedStatement createPostStatement = utils.getStatement(
                Requests.CREATE_NEW_POST.toString());
            PreparedStatement labelsStatement = utils.getStatement(
                    Requests.ADD_POST_LABEL.toString()))
        {

            createPostStatement.setString(1, post.getContent());
            createPostStatement.setDate(2, new java.sql.Date(post.getCreated().getTime()));
            createPostStatement.setDate(3, new java.sql.Date(post.getUpdated().getTime()));
            createPostStatement.setString(4, post.getStatus().name());
            createPostStatement.execute();

            int postId = getAll().get(getAll().size()-1).getId();

            for(Label label : post.getLabels()){
                labelsStatement.setInt(1, postId);
                labelsStatement.setInt(2, label.getId());
                labelsStatement.execute();
            }

            return getAll().get(postId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    public Post update(Post post) {
        try(PreparedStatement updateStatement = utils.getStatement(
                Requests.UPDATE_POST_BY_ID.toString());
            PreparedStatement deletePostLabelsStatement = utils.getStatement(
                    Requests.REMOVE_POST_LABELS.toString());
            PreparedStatement labelsStatement = utils.getStatement(
                    Requests.ADD_POST_LABEL.toString()))
        {
            //update in posts
            updateStatement.setString(1, post.getContent());
            updateStatement.setDate(2, new java.sql.Date(new Date().getTime()));
            updateStatement.setString(3, post.getStatus().toString());
            updateStatement.setInt(4, post.getId());
            updateStatement.execute();

            //remove old labels
            deletePostLabelsStatement.setInt(1, post.getId());
            deletePostLabelsStatement.execute();

            //add new labels
            for(Label label : post.getLabels()){
                labelsStatement.setInt(1, post.getId());
                labelsStatement.setInt(2, label.getId());
                labelsStatement.execute();
            }

            return getById(post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        //remove from posts
        try(PreparedStatement statement = utils.getStatement(
                Requests.REMOVE_POST.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from post_labels
        try(PreparedStatement statement = utils.getStatement(
                Requests.REMOVE_POST_FROM_POST_LABELS.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from writers_posts
        try(PreparedStatement statement = utils.getStatement(
                Requests.REMOVE_POST_FROM_WRITERS_POSTS.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Post getPostFromResultSet(ResultSet rs) throws SQLException{
        Post post = new Post();
        int postId = rs.getInt("posts.id");
        post.setId(postId);
        post.setContent(rs.getString("content"));
        post.setCreated(rs.getDate("created"));
        post.setUpdated(rs.getDate("updated"));
        post.setStatus(PostStatus.valueOf(rs.getString("postStatus")));
        List<Label> labels = new ArrayList<>();

        if(rs.getInt("l.id") != 0){
            labels.add(new Label(rs.getInt("l.id"), rs.getString("name")));
            while(rs.next()){
                if(rs.getInt("posts.id") != postId){
                    rs.previous();
                    break;
                }else{
                    labels.add(new Label(rs.getInt("l.id"), rs.getString("name")));
                }
            }
        }
        post.setLabels(labels);
        return post;
    }
}
