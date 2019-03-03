package by.alexei.afanasyeu.dao;

import org.junit.Test;

import java.util.Map;

public class FirstTask {
    @Test
    public void test() {
        try (CatsDao catsDao = new CatsDao();
        CatColorsInfoDao catColorsInfoDao = new CatColorsInfoDao()) {
            Map<String, Integer> catColorsInfo = catsDao.getColorsInfo();
            catColorsInfoDao.saveColorsInfo(catColorsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}