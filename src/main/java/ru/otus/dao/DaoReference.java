package ru.otus.dao;

import ru.otus.exception.BisunessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoReference<E> extends AbstractDaoCrud<E> {
    private String nameTable;
    private RowMapper<E> mapper;

    public DaoReference(DataSource ds, String nameTable, RowMapper<E> mapper) {
        super(ds);
        this.nameTable = nameTable;
        this.mapper = mapper;
    }

    @Override
    public E get(long id) {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " +
                    nameTable +
                    " WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapper.rowProccess(resultSet);
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }

    @Override
    public List<E> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(E entity) {
        throw new UnsupportedOperationException();
    }

    public interface RowMapper<E> {
        E rowProccess(ResultSet resultSet);
    }
}
