package final_work.services;

import final_work.models.Good;
import final_work.repository.IGoodCategoryRepository;
import final_work.repository.IGoodRepository;
import final_work.repository.IStockGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис представлений товаров
 */
@Service
public class GoodService implements IGoodService {

    @Autowired
    private IGoodRepository goodRepository;

    @Autowired
    private IGoodCategoryRepository categoryRepository;

    @Autowired
    private IStockGoodRepository stockGoodRepository;

    @Override
    public Good create(Good good) {
        return goodRepository.save(good);
    }

    /**
     * Получение всех видов товаров, отсортированных по категориям
     * @return
     */
    @Override
    public Iterable<Good> readAll() {
        return goodRepository.findAllByOrderByCategoryTitle();
    }

    @Override
    public Iterable<Good> readAllByCategoryId(long categoryId) {
        return goodRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Good read(long id) {
        return goodRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        if(goodRepository.existsById(id)){
            goodRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Good changePrice(long id, int price) {
        Good good = goodRepository.findById(id).orElse(null);
        if(good != null){
            good.setPrice(price);
            return goodRepository.save(good);
        } else {
            return null;
        }
    }


}
