package com.goylik.cleverbank.repository;

import com.goylik.cleverbank.dal.ConnectionPool;
import com.goylik.cleverbank.entity.BaseEntity;
import com.goylik.cleverbank.repository.config.QueryConfig;
import com.goylik.cleverbank.repository.config.impl.IdQueryConfig;
import com.goylik.cleverbank.repository.exception.DalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base repository class that provides basic CRUD operations for entities.
 *
 * @param <E> The type of the entity extending BaseEntity.
 */
public abstract class BaseRepository<E extends BaseEntity> implements Repository<Long, E> {
    protected final ConnectionPool pool = ConnectionPool.getInstance();

    protected final String SQL_SELECT_BY_ID = "SELECT * FROM " + getTableName() + " WHERE id=?";
    protected final String SQL_SELECT_ALL = "SELECT * FROM " + getTableName();
    protected final String SQL_DELETE_BY_ID = "DELETE FROM " + getTableName() + " WHERE id=?";
    protected final String SQL_DELETE_ALL = "DELETE FROM " + getTableName();

    /**
     * Executes an update query with the provided configuration.
     *
     * @param query  The SQL query to execute.
     * @param config The configuration for the query.
     * @return id of updated entity.
     * @throws SQLException If a database access error occurs.
     */
    protected Long executeUpdate(String query, QueryConfig config) throws SQLException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            config.setParameters(statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Long id = -1L;
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

            return id;
        }
    }

    /**
     * Executes a query and returns the result set with the provided configuration.
     *
     * @param query  The SQL query to execute.
     * @param config The configuration for the query.
     * @return The result set from the executed query.
     * @throws SQLException If a database access error occurs.
     */
    protected List<E> executeQuery(String query, QueryConfig config) throws SQLException, DalException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            config.setParameters(statement);
            ResultSet resultSet = statement.executeQuery();
            List<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(create(resultSet));
            }

            return entities;
        }
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id The ID of the entity.
     * @return The retrieved entity.
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public E findById(Long id) throws DalException {
        try {
            E entity = null;
            List<E> entities = executeQuery(SQL_SELECT_BY_ID, new IdQueryConfig(id));
            if (!entities.isEmpty()) {
                entity = entities.get(0);
            }

            return entity;
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting " + getTableName() + " by id.", e);
        }
    }

    /**
     * Retrieves all entities.
     *
     * @return A list of all retrieved entities.
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public List<E> findAll() throws DalException {
        try {
            return executeQuery(SQL_SELECT_ALL, s -> {});
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting all " + getTableName() + "s.", e);
        }
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity.
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public void delete(Long id) throws DalException {
        try {
            executeUpdate(SQL_DELETE_BY_ID, new IdQueryConfig(id));
        }
        catch (SQLException e) {
            throw new DalException("Exception while deleting " + getTableName() + ".", e);
        }
    }

    /**
     * Deletes an entity by entity.
     *
     * @param entity The entity to delete.
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public void delete(E entity) throws DalException {
        delete(entity.getId());
    }

    /**
     * Deletes all entities.
     *
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public void deleteAll() throws DalException {
        try {
            executeUpdate(SQL_DELETE_ALL, s -> {});
        }
        catch (SQLException e) {
            throw new DalException("Exception while deleting all " + getTableName() + "s.", e);
        }
    }

    /**
     * Counts the number of entities in the repository.
     *
     * @return The count of entities.
     * @throws DalException If an error occurs during data access.
     */
    @Override
    public Long count() throws DalException {
        String query = "SELECT COUNT(*) FROM " + getTableName();
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DalException("Can't get count of " + getTableName(), e);
        }

        return 0L;
    }

    /**
     * Creates an entity from the provided result set.
     *
     * @param resultSet The result set containing entity data.
     * @return The created entity.
     * @throws DalException If an error occurs during creating entity.
     */
    protected abstract E create(ResultSet resultSet) throws DalException;

    /**
     * Gets the table name associated with the repository.
     *
     * @return The table name.
     */
    protected abstract String getTableName();
}