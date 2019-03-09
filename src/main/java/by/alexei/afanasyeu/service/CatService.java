package by.alexei.afanasyeu.service;

import by.alexei.afanasyeu.dao.CatsDao;
import by.alexei.afanasyeu.dao.exception.DaoException;
import by.alexei.afanasyeu.domain.Cat;
import by.alexei.afanasyeu.service.exception.ServiceException;

import java.util.List;

public class CatService {
    private CatsDao catsDao;

    public CatService(){
        try {
            catsDao = new CatsDao();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public List<Cat> getCatList(String sortBy, String order, int offset, Integer limit) throws ServiceException {
        try {
            return catsDao.getAll(sortBy, order, offset, limit);
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
