package final_work.services;

import final_work.models.Good;

/**
 * Интерфейс для работы с представлениями товаров
 */
public interface IGoodService {
    /**
     * Создание нового товара
     * @param good - добавляемый объект
     */
    Good create(Good good);

    /**
     * Получение всех товаров в виде коллекции
     * @return коллекция объектов Good
     */
    Iterable<Good> readAll();

    /**
     * Получение свойств товара по id
     * @param id товара
     * @return объект Good
     */
    Good read(long id);

    /**
     * Удаление товара по id
     * @param id товара
     * @return true в случае успеха, false в противном случае
     */
    boolean delete(long id);

    /**
     * Изменение цены товара
     * @param id товара
     * @param price новое значение цены
     * @return объект Good
     */
    Good changePrice(long id, int price);

    /**
     * Получение всех товаров по заданной категории
     * @param categoryId id категории
     * @return коллекция объектов Good
     */
    Iterable<Good> readAllByCategoryId(long categoryId);
}
