package repository.jdbc;

import model.Label;
import model.Post;
import model.PostStatus;
import repository.PostRepository;
import service.Service;
import service.Requests;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class JDBCPostRepositoryImpl implements PostRepository {
    private final Service service = new Service();
    private final JDBCLabelRepositoryImpl labelRepository = new JDBCLabelRepositoryImpl();

    @Override
    public List<Post> getAll() {

        List<Post> posts = new ArrayList<>();
        try(PreparedStatement postStatement = service.getStatement(
                Requests.GET_ALL_POSTS.toString()))
        {
            ResultSet postResult = postStatement.executeQuery();
            while(postResult.next()){
                Post post = getPostFromResultSet(postResult);

                if(Objects.isNull(post)){
                    throw new RuntimeException();
                }
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post getById(Integer id) {

        try(PreparedStatement statement = service.getStatement
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

        try(PreparedStatement createPostStatement = service.getStatement(
                Requests.CREATE_NEW_POST.toString());
            PreparedStatement lastOneStatement = service.getStatement(
                    Requests.GET_LAST_POST.toString());
            PreparedStatement labelsStatement = service.getStatement(
                    Requests.ADD_POST_LABEL.toString()))
        {

            createPostStatement.setString(1, post.getContent());
            createPostStatement.setDate(2, new java.sql.Date(post.getCreated().getTime()));
            createPostStatement.setDate(3, new java.sql.Date(post.getUpdated().getTime()));
            createPostStatement.setString(4, post.getStatus().name());
            createPostStatement.execute();

            ResultSet lastPostRS = lastOneStatement.executeQuery();
            lastPostRS.next();

            int postId = lastPostRS.getInt("id");

            for(Label label : post.getLabels()){
                labelsStatement.setInt(1, postId);
                labelsStatement.setInt(2, label.getId());
                labelsStatement.execute();
            }

            return getPostFromResultSet(lastPostRS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    public Post update(Post post) {
        try(PreparedStatement updateStatement = service.getStatement(
                Requests.UPDATE_POST_BY_ID.toString());
            PreparedStatement deletePostLabelsStatement = service.getStatement(
                    Requests.REMOVE_POST_LABELS.toString());
            PreparedStatement labelsStatement = service.getStatement(
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
        try(PreparedStatement statement = service.getStatement(
                Requests.REMOVE_POST.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from post_labels
        try(PreparedStatement statement = service.getStatement(
                Requests.REMOVE_POST_FROM_POST_LABELS.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from writers_posts
        try(PreparedStatement statement = service.getStatement(
                Requests.REMOVE_POST_FROM_WRITERS_POSTS.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Label> getLabelList(int id){
        List<Label> labels = new ArrayList<>();

        try(PreparedStatement tagsStatement =
                    service.getStatement(
                            Requests.GET_ALL_POST_LABELS.toString()))
        {
            tagsStatement.setInt(1, id);
            ResultSet tagsResult = tagsStatement.executeQuery();

            while (tagsResult.next()) {
                Label label = labelRepository.getById(tagsResult.getInt("label_id"));
                if(Objects.isNull(label)){
                    return null;
                }
                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    private Post getPostFromResultSet(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        String content = resultSet.getString("content");
        Date created = resultSet.getDate("created");
        Date updated = resultSet.getDate("updated");
        PostStatus status = PostStatus.valueOf(resultSet.getString("postStatus"));

        List<Label> labels = getLabelList(id);

        if(Objects.isNull(labels)){
            return null;
        }

        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setCreated(created);
        post.setUpdated(updated);
        post.setStatus(status);
        post.setLabels(labels);

        return post;
    }
}
