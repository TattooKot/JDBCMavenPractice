package repository.jdbc;

import model.Post;
import model.Writer;
import repository.WriterRepository;
import service.Requests;
import service.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCWriterRepositoryImpl implements WriterRepository {
    private final Service service = new Service();
    private final JDBCPostRepositoryImpl postRepository = new JDBCPostRepositoryImpl();

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();

        try(PreparedStatement statement = service.getStatement(Requests.GET_ALL_WRITERS.toString())){
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                writers.add(getWriterFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writers;
    }

    @Override
    public Writer getById(Integer id) {
        try(PreparedStatement statement = service.getStatement(
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
        try(PreparedStatement preparedStatement = service.getStatement(
                    Requests.CREATE_NEW_WRITER.toString());
            PreparedStatement addWritersPostStatement = service.getStatement(
                    Requests.ADD_WRITER_POST.toString()))
        {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.execute();

            int lastId = getAll().get(getAll().size()-1).getId();
            writer.setId(lastId);

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
        try(PreparedStatement updateWriterStatement = service.getStatement(
                Requests.UPDATE_WRITER.toString());
            PreparedStatement removeWritersPostStatement = service.getStatement(
                    Requests.REMOVE_WRITER_POSTS_FROM_WRITERS_POSTS.toString());
            PreparedStatement addWritersPostStatement = service.getStatement(
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
        try(PreparedStatement deleteFromWritersStatement = service.getStatement(
                Requests.REMOVE_WRITER.toString());
            PreparedStatement removeWritersPostStatement = service.getStatement(
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

    private Writer getWriterFromResultSet(ResultSet rs) throws SQLException{
        Writer writer = new Writer();

        int writerId = rs.getInt("id");
        writer.setId(writerId);
        writer.setFirstName(rs.getString("firstName"));
        writer.setLastName(rs.getString("lastName"));

        List<Post> posts = new ArrayList<>();

        if(rs.getInt("post_id") != 0){
            do{
                if(rs.getInt("id") != writerId){
                    rs.previous();
                    break;
                } else{
                    posts.add(
                            postRepository.getById(
                                    rs.getInt("post_id")));
                }
            }
            while(rs.next());
        }

        writer.setPosts(posts);
        return writer;
    }
}
