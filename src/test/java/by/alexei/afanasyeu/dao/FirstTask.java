package by.alexei.afanasyeu.dao;

import org.junit.Test;

public class FirstTask {
    @Test
    public void test() {
        try (CatColorsInfoDao catColorsInfoDao = new CatColorsInfoDao()) {
            catColorsInfoDao.setColorsInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}