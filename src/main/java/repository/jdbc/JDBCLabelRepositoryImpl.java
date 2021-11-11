package repository.jdbc;

import model.Label;
import repository.TagRepository;
import service.DBConnection;
import service.Requests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JDBCLabelRepositoryImpl implements TagRepository {

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.GET_ALL_LABELS.toString()))
        {
            ResultSet rs = statement.executeQuery();
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
        try(PreparedStatement statement = DBConnection.getStatement(
                            Requests.GET_LABEL_BY_ID.toString())){
            statement.setInt(1, label_id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                String name = rs.getString("name");
                return new Label(label_id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Label create(Label label) {
        try(PreparedStatement createStatement = DBConnection.getStatement(
                Requests.CREATE_NEW_LABEL.toString());
            PreparedStatement lastLabelStatement = DBConnection.getStatement(
                    Requests.GET_LAST_LABEL.toString()))
        {
            createStatement.setString(1, label.getName());
            createStatement.execute();

            ResultSet rs = lastLabelStatement.executeQuery();
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
        if(Objects.isNull(getById(label.getId()))){
            return null;
        }

        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.UPDATE_LABEL.toString())) {
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
        //remove from labels
        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.REMOVE_LABEL.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from post_labels
        try(PreparedStatement statement = DBConnection.getStatement(
                Requests.REMOVE_LABEL_FROM_POST_LABELS.toString()))
        {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
