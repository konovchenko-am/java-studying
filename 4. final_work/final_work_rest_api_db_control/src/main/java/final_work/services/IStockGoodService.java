package final_work.services;

import final_work.models.StockGood;

/**
 * Интерфейс для работы с товарами на складе
 */
public interface IStockGoodService {

    /**
     * Создание новой записи "товар на складе"
     * @param stockGood - добавляемый объект
     * @return объект StockGood, полученный после записи изменений в БД
     */
    StockGood create(StockGood stockGood);

    /**
     * Получение всех записей "товар на складе" в виде коллекции, отсортированной по категориям
     * @return коллекция объектов StockGood
     */
    Iterable<StockGood> readAll();

    /**
     * Получение всех записей "товар на складе" заданной категории
     * @return коллекция объектов StockGood
     */
    Iterable<StockGood> readAllByCategoryId(long categoryId);


    /**
     * Получение всех свойств записи "товар на складе" по id
     * @param id товара на складе
     * @return объект StockGood, полученный после записи изменений в БД
     */
    StockGood read(long id);

    /**
     * Удаление записи "товар на складе" по id
     * @param id товара на складе
     * @return true в случае успеха, false в противном случае
     */
    boolean delete(long id);

    /**
     * Добавление количества товара на складе
     * @param goodId - id товара
     * @param goodAmountToAdd - количество добавляемого товара
     * @return объект StockGood, полученный после записи изменений в БД
     */
    StockGood addGoodAmount(long goodId, int goodAmountToAdd);
}
