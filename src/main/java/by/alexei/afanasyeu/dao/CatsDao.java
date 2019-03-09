package by.alexei.afanasyeu.dao;

import by.alexei.afanasyeu.dao.exception.DaoException;
import by.alexei.afanasyeu.domain.Cat;
import by.alexei.afanasyeu.domain.MeanMedianMode;
import by.alexei.afanasyeu.domain.StatInfo;

import java.sql.*;
import java.util.*;

public class CatsDao extends BaseDao {
    private PreparedStatement getTailAndWhiskersLengthStmt;
    private PreparedStatement saveStmt;
    private PreparedStatement isNameExistStmt;
    private PreparedStatement deleteByNameStmt;

    public CatsDao() throws DaoException {
        super();
        try {
            getTailAndWhiskersLengthStmt = con.prepareStatement("SELECT tail_length, whiskers_length FROM cats");
            saveStmt = con.prepareStatement("INSERT INTO cats (name, color, tail_length, whiskers_length) VALUES (?, ?::cat_color, ?, ?)");
            isNameExistStmt = con.prepareStatement("SELECT count(*) AS count FROM cats WHERE name=?");
            deleteByNameStmt = con.prepareStatement("DELETE FROM cats WHERE name=?");
        } catch (SQLException e) {
            throw new DaoException("Can't init CatsDao", e);
        }
    }

    public StatInfo getStatInfo() throws DaoException {
        try (ResultSet rs = getTailAndWhiskersLengthStmt.executeQuery()){
            List<Integer> tailLengthList = new ArrayList<>();
            List<Integer> whiskersLengthList = new ArrayList<>();
            while (rs.next()) {
                Integer tailLength = rs.getInt("tail_length");
                if (!rs.wasNull()) {
                    tailLengthList.add(tailLength);
                }
                Integer whiskersLength = rs.getInt("whiskers_length");
                if (!rs.wasNull()) {
                    whiskersLengthList.add(whiskersLength);
                }
            }
            return getStatInfo(tailLengthList, whiskersLengthList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Cat> getAll(String sortBy, String order, int offset, Integer limit) throws DaoException {
        List<Cat> result = new ArrayList<>();
        Statement stmt = null;
        try {
            String query;
            if (limit == null) {
                query = String.format("SELECT name, color, tail_length, whiskers_length FROM cats ORDER BY %s %s OFFSET %d", sortBy, order, offset);
            } else {
                query = String.format("SELECT name, color, tail_length, whiskers_length FROM cats ORDER BY %s %s OFFSET %d LIMIT %d", sortBy, order, offset, limit);
            }
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String color = rs.getString("color");
                Integer tailLength = rs.getInt("tail_length");
                Integer whiskersLength = rs.getInt("whiskers_length");
                result.add(new Cat(name, color, tailLength, whiskersLength));
            }
            return result;
        } catch (SQLException e ) {
            throw new DaoException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveCat(Cat cat) throws DaoException {
        try {
            con.setAutoCommit(false);
            isNameExistStmt.setString(1, cat.getName());
            ResultSet rs = isNameExistStmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("count") > 0) {
                    throw new DaoException(DaoException.CAT_EXIST);
                }
            } else {
                throw new DaoException("Can't check is cat exist.");
            }
            saveStmt.setString(1, cat.getName());
            saveStmt.setString(2, cat.getColor());
            if (cat.getTailLength() != null) {
                saveStmt.setInt(3, cat.getTailLength());
            } else {
                saveStmt.setNull(3, Types.INTEGER);
            }
            if (cat.getWhiskersLength() != null) {
                saveStmt.setInt(4, cat.getWhiskersLength());
            } else {
                saveStmt.setNull(4, Types.INTEGER);
            }
            saveStmt.execute();
            con.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void deleteByName(String name) throws DaoException {
        try {
            deleteByNameStmt.setString(1, name);
            deleteByNameStmt.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private StatInfo getStatInfo(List<Integer> tailLengthList, List<Integer> whiskersLengthList) {
        if (tailLengthList.isEmpty()) {
            return null;
        }
        StatInfo statInfo = new StatInfo();
        MeanMedianMode mathStat = getMeanMedianMode(tailLengthList);
        statInfo.setTailLengthMean(mathStat.getMean());
        statInfo.setTailLengthMedian(mathStat.getMedian());
        statInfo.setTailLengthMode(mathStat.getMode());
        mathStat = getMeanMedianMode(whiskersLengthList);
        statInfo.setWhiskersLengthMean(mathStat.getMean());
        statInfo.setWhiskersLengthMedian(mathStat.getMedian());
        statInfo.setWhiskersLengthMode(mathStat.getMode());
        return statInfo;
    }

    private MeanMedianMode getMeanMedianMode(List<Integer> list) {
        Collections.sort(list);
        List<Integer> modes = new ArrayList<>();
        int maxMode = 1;
        int curMode = 1;
        int lastNumber = list.get(0);
        modes.add(lastNumber);
        long sum = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            int cur = list.get(i);
            sum += cur;
            if (cur == lastNumber){
                curMode++;
            } else {
                curMode = 1;
            }
            if (curMode > maxMode){
                modes.clear();
                modes.add(cur);
                maxMode = curMode;
            } else if (curMode == maxMode) {
                modes.add(cur);
            }
            lastNumber = cur;
        }
        //median
        double median;
        int middle = list.size()/2;
        if (list.size()%2 == 1) {
            median = list.get(middle);
        } else {
            median = (list.get(middle-1) + list.get(middle)) / 2.0;
        }
        return new MeanMedianMode(sum/list.size(), median, modes);
    }
}
