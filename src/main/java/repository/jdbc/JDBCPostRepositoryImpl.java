package repository.jdbc;

import model.Label;
import model.Post;
import model.PostStatus;
import repository.PostRepository;
import service.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JDBCPostRepositoryImpl implements PostRepository {
    private final Connection connection = DBConnection.getConnection();
    private final JDBCLabelRepositoryImpl labelRepository = new JDBCLabelRepositoryImpl();

    @Override
    public List<Post> getAll() {
        checkConnection();
        assert connection != null;

        List<Post> posts = new ArrayList<>();
        try(Statement postStatement = connection.createStatement()){
            ResultSet postResult = postStatement.executeQuery("select * from posts");

            while(postResult.next()){
                Post post = getPostFromResultSet(postResult);

                if(Objects.isNull(post)){
                    return null;
                }

                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post getById(Integer postId) {
        checkConnection();
        assert connection != null;

        try(PreparedStatement statement = connection.prepareStatement("select * from posts where id = ?")) {
            statement.setInt(1, postId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Post post = getPostFromResultSet(resultSet);

            if(Objects.isNull(post)){
                return null;
            }
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post create(Post post) {
        checkConnection();
        assert connection != null;

        try(PreparedStatement statement = connection.prepareStatement(
                "insert into posts(content, created, updated, postStatus) VALUES (?,?,?,?)");
            PreparedStatement getIdStatement = connection.prepareStatement(
                "SELECT * FROM posts ORDER BY ID DESC LIMIT 1");
            PreparedStatement labelsStatement = connection.prepareStatement(
                    "insert into posts_labels values(?, ?)"))
        {

            statement.setString(1, post.getContent());
            statement.setDate(2, new java.sql.Date(post.getCreated().getTime()));
            statement.setDate(3, new java.sql.Date(post.getUpdated().getTime()));
            statement.setString(4, post.getStatus().name());
            statement.execute();

            ResultSet idRS = getIdStatement.executeQuery();
            idRS.next();

            int postId = idRS.getInt("id");

            for(Label label : post.getLabels()){
                labelsStatement.setInt(1, postId);
                labelsStatement.setInt(2, label.getId());
                labelsStatement.execute();
            }

            ResultSet resultSet = statement.executeQuery("SELECT * FROM posts ORDER BY ID DESC LIMIT 1");
            resultSet.next();

            return getPostFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    private void checkConnection() {
        if (Objects.isNull(connection)) {
            throw new RuntimeException("Something wrong with db connection...");
        }
    }

    private List<Label> getLabelList(int id){
        checkConnection();
        assert connection != null;

        List<Label> labels = new ArrayList<>();

        try(PreparedStatement tagsStatement = connection.prepareStatement("select label_id from posts_labels where post_id = ?")) {
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
