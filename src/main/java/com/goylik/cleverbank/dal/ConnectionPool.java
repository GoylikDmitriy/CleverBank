package com.goylik.cleverbank.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A singleton class for managing a pool of database connections.
 */
public final class ConnectionPool {
    private static ConnectionPool instance;

    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final int POOL_SIZE = 8;
    private final BlockingDeque<Connection> freeConnections;
    private final BlockingDeque<Connection> busyConnections;

    /**
     * Private constructor to initialize the connection pool.
     * Creates a specified number of database connections and adds them to the pool.
     * Throws a RuntimeException if pool creation fails due to a database access error.
     */
    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        busyConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                freeConnections.offer(connection);
            } catch (SQLException e) {
                throw new RuntimeException("Connections pool can't be created. Database access error.");
            }

            if (freeConnections.isEmpty()) {
                throw new RuntimeException("Connections pool can't be created. Database access error.");
            }
        }
    }

    /**
     * Get the singleton instance of the connection pool.
     *
     * @return The instance of the connection pool.
     */
    public static ConnectionPool getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new ConnectionPool();
            }
        }

        return instance;
    }

    /**
     * Get a database connection from the pool.
     *
     * @return A database connection from the pool.
     * @throws RuntimeException If getting a connection is interrupted.
     */
    public Connection getConnection() {
        try {
            Connection connection = freeConnections.take();
            busyConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        throw new RuntimeException("Ran out of time.");
    }

    /**
     * Release a database connection back to the pool.
     *
     * @param connection The connection to release.
     * @return true if the connection was successfully released, false otherwise.
     */
    public boolean releaseConnection(Connection connection) {
        try {
            if (busyConnections.remove(connection)) {
                freeConnections.put(connection);
                return true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return false;
    }
}
