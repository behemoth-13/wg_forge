package by.alexei.afanasyeu.dao;

import by.alexei.afanasyeu.domain.Cat;
import by.alexei.afanasyeu.service.CatService;
import org.junit.Test;

public class FifthTask {
    @Test
    public void test() {
        try {
            Cat cat = new Cat("tom", "red & white", 22,6);
            CatService service = CatService.getInstance();
            service.saveCat(cat);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
