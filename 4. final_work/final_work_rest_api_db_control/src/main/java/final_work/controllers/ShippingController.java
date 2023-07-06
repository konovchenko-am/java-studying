package final_work.controllers;

import final_work.models.ShippedGood;
import final_work.services.IGoodService;
import final_work.services.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * REST контроллер для работы с отгрузками и отгруженными товарами
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    private final IGoodService goodService;

    @Autowired
    private IShippingService shippingService;

    @Autowired
    public ShippingController(IGoodService goodService, IShippingService shippingService) {
        this.goodService = goodService;
        this.shippingService = shippingService;
    }

    /**
     * Обработка запроса на получение всех отгруженных товаров
     * @return ResponseEntity с коллекцией объектов ShippedGood
     */
    @GetMapping("")
    public ResponseEntity<?> readAll(){
        List<ShippedGood> shippedGoods = new ArrayList<>();
        Iterable<ShippedGood> iterable = shippingService.readAllShippedGood();
        iterable.forEach(shippedGoods::add);
        return !shippedGoods.isEmpty()
                ? new ResponseEntity<>(shippedGoods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на получение всех отгруженных товаров по заданной отгрузке
     * @param shipmentId id отгрузки
     * @return ResponseEntity с коллекцией объектов ShippedGood внутри
     */
    @GetMapping("/{shipmentId}")
    public ResponseEntity<?> readShipmentById(@PathVariable("shipmentId") long shipmentId){
        List<ShippedGood> shippedGoods = new ArrayList<>();
        Iterable<ShippedGood> iterable = shippingService.readAllShippedGoodByShipmentId(shipmentId);
        iterable.forEach(shippedGoods::add);
        return !shippedGoods.isEmpty()
                ? new ResponseEntity<>(shippedGoods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на добавление заданного товара в отгрузку для заданного пользователя
     * @param goodId id товара
     * @param argsMap - объект Map<String, String>: "goodAmount" - количество товара, "login" - логин пользователя
     * @return
     */
    @PostMapping("/add/{id}")   // в URL - id товара
    public ResponseEntity<?> addGood(@PathVariable("id") long goodId, @RequestBody Map<String, String> argsMap){
        String login;
        int goodAmount;
        try {
            goodAmount = Integer.parseInt(argsMap.get("goodAmount")); // в теле один параметр - количество товара
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Messages.WRONG_VALUE, HttpStatus.BAD_REQUEST);
        }
        login = argsMap.get("login");       // второй параметр в теле - логин пользователя, кому отгружается товар
        if(login == null || login.isEmpty())
            return new ResponseEntity<>(Messages.WRONG_VALUE, HttpStatus.BAD_REQUEST);

        ShippedGood shippedGood = shippingService.addGood(login, goodId, goodAmount);
        return shippedGood != null
                ? new ResponseEntity<>(shippedGood, HttpStatus.OK)
                : new ResponseEntity<>(Messages.ADD_GOOD_TO_SHIP_ERROR, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на удаление заданного отгруженного товара
     * @param id отгруженного товара
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteGood(@PathVariable("id") long id){

        boolean isRemoved = shippingService.deleteShippedGoodById(id);;
        return isRemoved
                ? new ResponseEntity<>("Запись c id = " + id + " успешно удалена.", HttpStatus.OK)
                : new ResponseEntity<>(Messages.NO_ID_RECORD_FOUND, HttpStatus.NOT_FOUND);
    }
}
