package final_work.services;

import final_work.models.Good;
import final_work.models.GoodCategory;

/**
 * Интерфейс для работы с категориями товаров
 */
public interface IGoodCategoryService {
    /**
     * Создание новой категории товаров
     * @param category - добавляемый объект
     */
    GoodCategory create(GoodCategory category);

    /**
     * Получение всех категорий товаров в виде коллекции
     * @return коллекция объектов GoodCategory из БД
     */
    Iterable<GoodCategory> readAll();

    /**
     * Получение всех свойств категории товаров по id
     * @param id категории товаров
     * @return объект GoodCategory
     */
    GoodCategory read(long id);

    /**
     * Удаление категорию товаров по id
     * @param id категории товаров
     * @return true в случае успеха
     */
    boolean delete(long id);

    /**
     * Добавление заданного товара к заданной категории
     * @param categoryId id категории товаров
     * @param goodId id товара
     * @return объект Good
     */
    Good addGoodToCategory(long categoryId, long goodId);

}
