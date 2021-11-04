package repository.jdbc;

import model.Label;
import repository.TagRepository;
import service.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JDBCTagRepositoryImpl implements TagRepository {
    private final Connection connection = DBConnection.getConnection();

    @Override
    public List<Label> getAll() {
        checkConnection();
        assert connection != null;

        List<Label> labels = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from labels");

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                labels.add(new Label(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labels;
    }

    @Override
    public Label getById(Integer label_id) {
        checkConnection();
        assert connection != null;

        try(PreparedStatement statement = connection.prepareStatement(
                            "select name from labels where id = ?")){
            statement.setInt(1, label_id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                String name = rs.getString("name");
                return new Label(label_id, name);
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Label create(Label label) {
        checkConnection();
        assert connection != null;

        try(PreparedStatement statement = connection.prepareStatement(
                "insert into labels(name) values (?)")) {
            statement.setString(1, label.getName());
            statement.execute();

            ResultSet rs = statement.executeQuery("SELECT * FROM labels ORDER BY ID DESC LIMIT 1");
            rs.next();
            if(rs.getString("name").equals(label.getName())){
                return new Label(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Label update(Label label) {
        checkConnection();
        assert connection != null;

        if(Objects.isNull(getById(label.getId()))){
            return null;
        }

        try(PreparedStatement statement = connection.prepareStatement(
                "update labels set name = ? where id = ?")) {
            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());

            statement.execute();
            return label;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        checkConnection();
        assert connection != null;

        //remove from labels
        try(PreparedStatement statement = connection.prepareStatement(
                "delete from labels where id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from post_labels
        try(PreparedStatement statement = connection.prepareStatement(
                "delete from posts_labels where label_id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkConnection() {
        if (Objects.isNull(connection)) {
            throw new RuntimeException("Something wrong with db connection...");
        }
    }
}
