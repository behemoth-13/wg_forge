package by.alexei.afanasyeu.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao implements AutoCloseable {
    protected Connection con;

    protected BaseDao() throws DaoException {
        try {
            PostgresUtil util = PostgresUtil.getInstance();
            con = util.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Can't get connection", e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
