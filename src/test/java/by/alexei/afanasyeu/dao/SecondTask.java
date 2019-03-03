package by.alexei.afanasyeu.dao;

import by.alexei.afanasyeu.domain.StatInfo;
import org.junit.Test;

public class SecondTask {
    @Test
    public void test() {
        try (CatsDao catsDao = new CatsDao();
            CatsStatDao catsStatDao = new CatsStatDao()) {
            StatInfo statInfo = catsDao.getStatInfo();
            catsStatDao.saveStatInfo(statInfo);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
