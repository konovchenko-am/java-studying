package final_work.services;

import final_work.models.*;
import final_work.repository.*;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с отгрузками
 */
@Service
public class ShippingService implements IShippingService {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");

    @Autowired
    private IShipmentRepository shipmentRepository;
    @Autowired
    private IShippedGoodRepository shippedGoodRepository;
    @Autowired
    private IGoodRepository goodRepository;

    @Autowired
    private IGoodCategoryRepository categoryRepository;

    @Autowired
    private IStockGoodRepository stockGoodRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public ShippedGood create(ShippedGood shippedGood) {
        return shippedGoodRepository.save(shippedGood);
    }

    @Override
    public Iterable<Shipment> readAllShipments() {
        return shipmentRepository.findAll();
    }

    public Iterable<ShippedGood> readAllShippedGoodByShipmentId(long shipmentId){
        return shippedGoodRepository.findAllByShipmentId(shipmentId);
    }
    @Override
    public Iterable<ShippedGood> readAllShippedGood() {
        return shippedGoodRepository.findAll();
    }

    @Override
    public Shipment readShipment(long shipmentId) {
        return shipmentRepository.findById(shipmentId).orElse(null);
    }

    @Override
    public ShippedGood readShippedGood(long id) {
        return shippedGoodRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteShipmentById(long shipmentId) {
        if(shipmentRepository.existsById(shipmentId)){
            shipmentRepository.deleteById(shipmentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteShippedGoodById(long shippedGoodId) {
        if(shippedGoodRepository.existsById(shippedGoodId)){
            shippedGoodRepository.deleteById(shippedGoodId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ShippedGood addGood(String login, long goodId, int goodAmount) {

        StockGood stockGood;

        if(stockGoodRepository.existsByGoodId(goodId)){  // ищем такой товар на складе
            stockGood = stockGoodRepository.findOneByGoodId(goodId); // если нашли
            if(stockGood.getAmount() < goodAmount) {   // проверяем необходимое количество
                return null;                    // если нехватка, возвращаем Null
            }
        } else {
            return null;
        }

        LocalDateTime currDate = new LocalDateTime();  // получаем текущую дату
        Shipment shipment;          // рабочий объект "поставка"
        ShippedGood shippedGood;    // рабочий объект "отгруженный товар"

        // ищем поставку с таким логином и текущей датой
        if(shipmentRepository.existsShipmentByUserLoginAndDate(login, currDate.toString(dateTimeFormatter))) {
            // если такая есть
            shipment = shipmentRepository.findOneByUserLoginAndDate(login, currDate.toString(dateTimeFormatter));
            // ищем запись отгруженный товар
            if(shippedGoodRepository.existsByShipmentIdAndGoodId(shipment.getId(), goodId)){
                // если такой отгружаемый товар есть

                // получаем отгружаемый товар из БД
                shippedGood = shippedGoodRepository.findOneByShipmentIdAndGoodId(shipment.getId(), goodId);
                shippedGood.setAmount(shippedGood.getAmount() + goodAmount); //увеличиваем его количество в поставке
            } else {
                // если такого отгружаемого товара нет

                // создаем отгружаемый товар
                shippedGood = new ShippedGood(shipment, stockGood.getGood(), goodAmount);
            }
            // уменьшаем количество этого товара на складе
            stockGood.setAmount(stockGood.getAmount() - goodAmount);
            stockGood.calculateSum();
            stockGoodRepository.save(stockGood);

            shippedGood.calculateSum(); // выставляем сумму для отгруженного товара
            return shippedGoodRepository.save(shippedGood); // сохраняем отгруженный товар в БД, возвращаем результат

        } else { // если поставки с таким логином и текущей датой нет

            // ищем такого пользователя
            if (userRepository.existsByLogin(login)) {
                // если такой пользователь есть
                User user = userRepository.findOneByLogin(login);

                // создаем новую поставку
                shipment = new Shipment(user, currDate.toString(dateTimeFormatter));
                shipmentRepository.save(shipment); // сохраняем поставку в БД

                // уменьшаем количество этого товара на складе
                stockGood.setAmount(stockGood.getAmount() - goodAmount);
                stockGood.calculateSum();
                stockGoodRepository.save(stockGood); // сохраняем запись "товар на складе" с уменьшенным количеством

                // создаем новую запись "отгруженный товар"
                shippedGood = new ShippedGood(shipment, stockGood.getGood(), goodAmount);
                shippedGood.calculateSum();
                return shippedGoodRepository.save(shippedGood);
            } else { // если такого пользователя нет
                return null;
            }
        }
    }
}
