package repository.jdbc;

import model.Post;
import model.Writer;
import repository.WriterRepository;
import service.DBConnection;
import service.Requests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCWriterRepositoryImpl implements WriterRepository {

    private final JDBCPostRepositoryImpl postRepository = new JDBCPostRepositoryImpl();

    @Override
    public List<Writer> getAll() {
        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.GET_ALL_WRITERS.toString()))
        {
            ResultSet rs = statement.executeQuery();
            List<Writer> writers = new ArrayList<>();

            while(rs.next()){
                writers.add(getWriterFromResultSet(rs));
            }

            return writers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    public Writer getById(Integer id) {
        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.GET_WRITER_BY_ID.toString()))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return getWriterFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer create(Writer writer) {
        try(PreparedStatement preparedStatement = DBConnection.getStatement(
                    Requests.CREATE_NEW_WRITER.toString());
            PreparedStatement lastWriterStatement = DBConnection.getStatement(
                    Requests.GET_LAST_WRITER.toString());
            PreparedStatement addWritersPostStatement = DBConnection.getStatement(
                    Requests.ADD_WRITER_POST.toString()))
        {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.execute();

            ResultSet lastWriterRS = lastWriterStatement.executeQuery();
            lastWriterRS.next();
            writer.setId(lastWriterRS.getInt("id"));

            for(Post post : writer.getPosts()){
                addWritersPostStatement.setInt(1, writer.getId());
                addWritersPostStatement.setInt(2, post.getId());
                addWritersPostStatement.execute();
            }

            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        try(PreparedStatement updateWriterStatement = DBConnection.getStatement(
                Requests.UPDATE_WRITER.toString());
            PreparedStatement removeWritersPostStatement = DBConnection.getStatement(
                    Requests.REMOVE_WRITER_POSTS_FROM_WRITERS_POSTS.toString());
            PreparedStatement addWritersPostStatement = DBConnection.getStatement(
                    Requests.ADD_WRITER_POST.toString()))
        {
            //update in writers
            updateWriterStatement.setString(1, writer.getFirstName());
            updateWriterStatement.setString(2, writer.getLastName());
            updateWriterStatement.setInt(3, writer.getId());
            updateWriterStatement.execute();

            //remove all posts
            removeWritersPostStatement.setInt(1, writer.getId());
            removeWritersPostStatement.execute();

            //add new posts
            for(Post post : writer.getPosts()){
                addWritersPostStatement.setInt(1, writer.getId());
                addWritersPostStatement.setInt(2, post.getId());
                addWritersPostStatement.execute();
            }

            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        try(PreparedStatement deleteFromWritersStatement = DBConnection.getStatement(
                Requests.REMOVE_WRITER.toString());
            PreparedStatement removeWritersPostStatement = DBConnection.getStatement(
                    Requests.REMOVE_WRITER_POSTS_FROM_WRITERS_POSTS.toString()))
        {
            deleteFromWritersStatement.setInt(1, id);
            deleteFromWritersStatement.execute();

            removeWritersPostStatement.setInt(1,id);
            removeWritersPostStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Post> getPostListById(int id){
        List<Post> posts = new ArrayList<>();
        try(PreparedStatement postsIdStatement = DBConnection.getStatement(
                Requests.GET_ALL_WRITERS_POSTS.toString()))
        {
            postsIdStatement.setInt(1, id);
            ResultSet rs = postsIdStatement.executeQuery();

            while(rs.next()){
                int postId = rs.getInt("post_id");
                Post post = postRepository.getById(postId);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    private Writer getWriterFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        List<Post> posts = getPostListById(id);

        Writer writer = new Writer();
        writer.setId(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setPosts(posts);

        return writer;
    }
}
