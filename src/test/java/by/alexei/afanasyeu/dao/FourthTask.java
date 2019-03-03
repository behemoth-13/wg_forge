package by.alexei.afanasyeu.dao;

import org.junit.Test;

public class FourthTask {
    @Test
    public void test() {
        try {
            CatsDao dao = new CatsDao();
            System.out.println(dao.getAll());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
