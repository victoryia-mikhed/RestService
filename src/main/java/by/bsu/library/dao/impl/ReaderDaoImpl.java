package by.bsu.library.dao.impl;

import by.bsu.library.dao.ReaderDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReaderDaoImpl implements ReaderDao{

    private static final String SQL_READER_ID = "READER_ID";
    private static final String SQL_SELECT_READER_BY_ID = "SELECT READER_ID, NAME, PASSWORD FROM READERS WHERE READER_ID=?";
    private static final String SQL_SELECT_READER_BY_NAME = "SELECT READER_ID, NAME, PASSWORD FROM READERS WHERE NAME=?";
    private static final String SQL_SELECT_READER_BY_NAME_PASSWORD = "SELECT READER_ID, NAME, PASSWORD FROM READERS WHERE NAME=? AND PASSWORD=?";
    private static final String SQL_INSERT_READER = "INSERT INTO READERS (NAME, PASSWORD) VALUES (?,?)";
    private static final String SQL_UPDATE_READER = "UPDATE READERS SET NAME=?, PASSWORD=? WHERE READER_ID=?";
    private static final String SQL_DELETE_READER = "DELETE FROM READERS WHERE READER_ID=?";

    @Autowired
    private DataSource dataSource;

    @Override
    public Long insert(Reader object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT_READER, new String[]{SQL_READER_ID})) {
            ps.setString(1, object.getName());
            ps.setString(2, object.getPassword());
            if (ps.executeUpdate() == 0) {
                return null;
            }
            resultSet = ps.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Exception while inserting reader", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean update(Reader object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_READER)) {
            ps.setString(1, object.getName());
            ps.setString(2, object.getPassword());
            ps.setLong(2, object.getReaderID());
            if (ps.executeUpdate() == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating reader", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Reader getReaderByName(String name) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_READER_BY_NAME)) {
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            return initReader(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getElementById", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_READER)) {
            ps.setLong(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting reader", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Reader getReaderByNamePassword(String name, String password) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_READER_BY_NAME_PASSWORD)) {
            ps.setString(1, name);
            ps.setString(2, password);
            resultSet = ps.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            return initReader(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getElementById", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Reader getElementById(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_READER_BY_ID)) {
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            return initReader(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getElementById", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private Reader initReader(ResultSet resultSet) throws SQLException {
        Reader reader = new Reader();
        reader.setReaderID(resultSet.getLong(1));
        reader.setName(resultSet.getString(2));
        reader.setPassword(resultSet.getString(3));
        return reader;
    }
}
