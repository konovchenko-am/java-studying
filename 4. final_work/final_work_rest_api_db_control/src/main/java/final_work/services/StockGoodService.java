package final_work.services;

import final_work.models.Good;
import final_work.models.StockGood;
import final_work.repository.IGoodRepository;
import final_work.repository.IStockGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с товарами на складе
 */
@Service
public class StockGoodService implements IStockGoodService {

    @Autowired
    private IStockGoodRepository stockGoodRepository;

    @Autowired
    private IGoodRepository goodRepository;

    @Override
    public StockGood create(StockGood stockGood) {
        return stockGoodRepository.save(stockGood);
    }

    @Override
    public Iterable<StockGood> readAll() {
        return stockGoodRepository.findAllByOrderByGoodCategoryTitle();
    }

    @Override
    public Iterable<StockGood> readAllByCategoryId(long categoryId) {
        return stockGoodRepository.findAllByGoodCategoryId(categoryId);
    }

    @Override
    public StockGood read(long id) {
        return stockGoodRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        if(stockGoodRepository.existsById(id)){
            stockGoodRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Добавление заданного количества товара на склад
     * @param goodId - id товара
     * @param goodAmountToAdd - количество добавляемого товара
     * @return
     */
    @Override
    public StockGood addGoodAmount(long goodId, int goodAmountToAdd) {

        StockGood stockGood;
        // если запись "товар на складе" с заданным id товара существует
        if(stockGoodRepository.existsByGoodId(goodId)) {
            stockGood = stockGoodRepository.findOneByGoodId(goodId);
            // добавляем количество
            stockGood.setAmount(stockGood.getAmount() + goodAmountToAdd);
            stockGood.calculateSum();
            // сохраняем запись
            return stockGoodRepository.save(stockGood);
        } else {  // если запись "товар на складе" с заданным id товара в БД отсутствует
            if (goodRepository.existsById(goodId)) { // если товар с заданным id в БД существует
                Good good = goodRepository.findById(goodId).orElse(null);
                stockGood = new StockGood(good, goodAmountToAdd); // создали новую запись "товар на складе"
                stockGood.calculateSum();
                return stockGoodRepository.save(stockGood);    // сохранили созданную запись в БД
            } else { // если товар с заданным id в БД отсутствует
                return null;
            }
        }
    }
}
