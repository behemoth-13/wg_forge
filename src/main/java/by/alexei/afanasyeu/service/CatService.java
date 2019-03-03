package by.alexei.afanasyeu.service;

import by.alexei.afanasyeu.dao.CatsDao;
import by.alexei.afanasyeu.dao.DaoException;
import by.alexei.afanasyeu.domain.Cat;

import java.util.List;

public class CatService {
    private CatsDao catsDao;
    private static CatService instance = new CatService();

    public static CatService getInstance() {
        return instance;
    }

    private CatService(){
        try {
            catsDao = new CatsDao();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public List<Cat> getCatList() throws ServiceException {
        try {
            return catsDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveCat(Cat cat) throws ServiceException {
        try {
            catsDao.saveCat(cat);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
