package final_work.controllers;

import final_work.models.Good;
import final_work.services.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * REST контроллер для работы с видами товаров (для работы с товарами на складе предназначен StockController)
 */
@RestController
@RequestMapping("/goods")
public class GoodController {

    private final IGoodService goodService; // объект для вызова методов интерфейса

    @Autowired
    public GoodController(IGoodService goodService) {
        this.goodService = goodService;
    }

    /**
     * Обработка запроса на вывод всех видов товаров с сортировкой по категориям
     * @return ResponseEntity c коллекцией объектов Good
     */
    @GetMapping("")
    public ResponseEntity<?> readAll(){
        List<Good> goods = new ArrayList<>();
        Iterable<Good> goodsIterable = goodService.readAll();
        goodsIterable.forEach(goods::add);
        return !goods.isEmpty()
                ? new ResponseEntity<>(goods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на вывод всех видов товаров заданной категории
     * @return ResponseEntity c коллекцией объектов Good
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> readAllByCategoryId(@PathVariable(name="categoryId") long categoryId){
        List<Good> goods = new ArrayList<>();
        Iterable<Good> goodsIterable = goodService.readAllByCategoryId(categoryId);
        goodsIterable.forEach(goods::add);
        return !goods.isEmpty()
                ? new ResponseEntity<>(goods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на создание товара
     * @param ticket объект PledgeTicket из запроса POST
     * @return ResponseEntity c объектом PledgeTicket ticket
     */
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody Good ticket){//user - это объект, который клиент отправил в виде json
        try{
            goodService.create(ticket);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(Messages.INSERT_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обработка запроса ын получение информации о заданном товаре
     * @param id товара
     * @return ResponseEntity c объектом Good
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable(name="id") long id){
        Good good = goodService.read(id); //по id получили юзера
        return good != null
                ? new ResponseEntity<>(good, HttpStatus.OK)
                : new ResponseEntity<>(Messages.NO_ID_RECORD_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на изменение цены товара
     * @param id товара, передается в URL
     * @param bodyParams тело запроса, должно содержать параметр "price" - новая цена товара
     * @return в случае успеха - ResponseEntity с объектом Good
     */
    @PutMapping(value = "/price/{id}")
    public ResponseEntity<?> changePrice(@PathVariable(name="id") long id, @RequestBody Map<String, String> bodyParams) {
        int price;
        try {
            price = Integer.parseInt(bodyParams.get("price"));
        } catch (NumberFormatException e){
            return new ResponseEntity<>(Messages.WRONG_PRICE_VALUE, HttpStatus.NOT_MODIFIED);
        }

        Good good = goodService.changePrice(id, price);
        return good != null
                ? new ResponseEntity<>(good, HttpStatus.OK)
                : new ResponseEntity<>(Messages.UPDATING_PRICE_ERROR, HttpStatus.NOT_MODIFIED);
    }

    /**
     * Обработка запроса на удаление товара
     * @param id товара
     * @return true в случае успешного удаления, false - в противном случае
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") long id){
        boolean isRemoved = goodService.delete(id);
        return isRemoved
                ? new ResponseEntity<>("Запись c id = " + id + " успешно удалена.", HttpStatus.OK)
                : new ResponseEntity<>(Messages.NO_ID_RECORD_FOUND, HttpStatus.NOT_FOUND);
    }
}
