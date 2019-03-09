package by.alexei.afanasyeu.dao;

import by.alexei.afanasyeu.dao.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CatColorsInfoDao extends BaseDao {
    private PreparedStatement setColorInfoStmt;

    public CatColorsInfoDao() throws DaoException {
        super();
        try {
            setColorInfoStmt = con.prepareStatement("TRUNCATE TABLE cat_colors_info; " +
                    "INSERT INTO cat_colors_info(color, count) " +
                    "SELECT color, count(*) AS count FROM cats GROUP BY color");
        } catch (SQLException e) {
            throw new DaoException("Can't init CatColorsInfoDao", e);
        }
    }

    public void setColorsInfo() {
        try {
            setColorInfoStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
