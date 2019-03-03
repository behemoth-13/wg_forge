package by.alexei.afanasyeu.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class CatColorsInfoDao extends BaseDao {
    private PreparedStatement truncateStmt;
    private PreparedStatement saveCountColouredCatsStmt;

    public CatColorsInfoDao() throws DaoException {
        super();
        try {
            truncateStmt = con.prepareStatement("TRUNCATE TABLE cat_colors_info;");
            saveCountColouredCatsStmt = con.prepareStatement(" INSERT INTO cat_colors_info(color, count) VALUES (?::cat_color, ?)");
        } catch (SQLException e) {
            throw new DaoException("Can't init CatColorsInfoDao", e);
        }
    }

    public void saveColorsInfo(Map<String, Integer> catColorsInfo) {
        try {
            con.setAutoCommit(false);
            for (Map.Entry<String, Integer> entry : catColorsInfo.entrySet()) {
                saveCountColouredCatsStmt.setString(1, entry.getKey());
                saveCountColouredCatsStmt.setInt(2, entry.getValue());
                saveCountColouredCatsStmt.addBatch();
            }
            truncateStmt.execute();
            saveCountColouredCatsStmt.executeBatch();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
