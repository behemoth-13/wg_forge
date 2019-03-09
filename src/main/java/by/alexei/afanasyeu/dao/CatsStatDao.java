package by.alexei.afanasyeu.dao;

import by.alexei.afanasyeu.dao.exception.DaoException;
import by.alexei.afanasyeu.domain.StatInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CatsStatDao extends BaseDao {
    private PreparedStatement setStatInfoStmt;

    public CatsStatDao() throws DaoException {
        super();
        try {
            setStatInfoStmt = con.prepareStatement("INSERT INTO cats_stat(" +
                    "tail_length_mean, tail_length_median, tail_length_mode, " +
                    "whiskers_length_mean, whiskers_length_median, whiskers_length_mode" +
                    ") VALUES (?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            throw new DaoException("Can't init CatsStatDao", e);
        }
    }

    public void saveStatInfo(StatInfo statInfo) throws DaoException {
        try {
            setStatInfoStmt.setDouble(1, statInfo.getTailLengthMean());
            setStatInfoStmt.setDouble(2, statInfo.getTailLengthMedian());
            setStatInfoStmt.setArray(3, con.createArrayOf("integer", statInfo.getTailLengthMode().toArray()));
            setStatInfoStmt.setDouble(4, statInfo.getWhiskersLengthMean());
            setStatInfoStmt.setDouble(5, statInfo.getWhiskersLengthMedian());
            setStatInfoStmt.setArray(6, con.createArrayOf("integer", statInfo.getWhiskersLengthMode().toArray()));
            setStatInfoStmt.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't save StatInfo", e);
        }
    }
}