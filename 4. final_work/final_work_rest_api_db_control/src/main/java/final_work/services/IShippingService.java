package final_work.services;

import final_work.models.Shipment;
import final_work.models.ShippedGood;

/**
 * Интерфейс для работы с отгрузками
 */
public interface IShippingService {

    /**
     * Создание новой поставки
     * @param shippedGood объект "товар на складе"
     * @return объект ShippedGood, полученный после записи изменений в БД
     */
    ShippedGood create(ShippedGood shippedGood);

    /**
     * Получение всех поставок товаров в виде коллекции
     * @return коллекция Shipment
     */
    Iterable<Shipment> readAllShipments();

    /**
     * Получение всех отгруженных товаров в виде коллекции
     * @return коллекция ShippedGood
     */
    Iterable<ShippedGood> readAllShippedGood();

    /**
     * Получение свойств отгрузки по id
     * @param shipmentId id отгрузки
     * @return объект Shipment, полученный после записи изменений в БД
     */
    Shipment readShipment(long shipmentId);

    /**
     * Получение свойств отгруженного товара по id
     * @param shippedGoodId id отгруженного товара
     * @return объект ShippedGood, полученный после записи изменений в БД
     */
    ShippedGood readShippedGood(long shippedGoodId);

    /**
     * Удаление отгрузки по id
     * @param shipmentId id отгрузки
     * @return true в случае успеха, false в противном случае
     */
    boolean deleteShipmentById(long shipmentId);

    /**
     * Удаление отгруженного товара по id
     * @param shippedGoodId id отгруженного товара
     * @return true в случае успеха, false в противном случае
     */
    boolean deleteShippedGoodById(long shippedGoodId);

    /**
     * Добавление указанного количества товара в поставку по логину пользователя и id товара
     * @param login пользователя, в пользу которого осуществляется поставка
     * @param goodId id товара, который добавляется в поставку
     * @param goodAmount количество указанного товара перемещаемого в поставку со склада
     * @return объект ShippedGood, возвращенный после записи в БД
     */
    ShippedGood addGood(String login, long goodId, int goodAmount);

    /**
     * Получение всех отгруженных товаров заданной отгрузки
     * @param shipmentId id отгрузки
     * @return коллекция объектов ShippedGood
     */
    Iterable<ShippedGood> readAllShippedGoodByShipmentId(long shipmentId);
}
