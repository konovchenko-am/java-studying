package final_work.services;

import final_work.models.Good;
import final_work.models.GoodCategory;
import final_work.repository.IGoodCategoryRepository;
import final_work.repository.IGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис категорий товаров
 */
@Service
public class GoodCategoryService implements IGoodCategoryService{

    @Autowired
    private IGoodCategoryRepository categoryRepository;

    @Autowired
    private IGoodRepository goodRepository;

    @Override
    public GoodCategory create(GoodCategory category) {
        return categoryRepository.save(category);
    }

    @Override
    public Iterable<GoodCategory> readAll() {
        return categoryRepository.findAll();
    }

    @Override
    public GoodCategory read(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Good addGoodToCategory(long categoryId, long goodId) {
        if(categoryRepository.existsById(categoryId) && goodRepository.existsById(goodId)){
            Good good = goodRepository.findById(goodId).orElse(null);
            GoodCategory category = categoryRepository.findById(categoryId).orElse(null);
            if(good==null || category == null) return null;
            good.setCategory(category);
            return goodRepository.save(good);
        }
        return null;
    }
}
