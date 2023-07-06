package final_work.controllers;

import final_work.models.Good;
import final_work.models.StockGood;
import final_work.services.IStockGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * REST контроллер для работы с товарами на складе
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    private final IStockGoodService stockGoodService; // объект для вызова методов интерфейса

    @Autowired
    public StockController(IStockGoodService stockGoodService) {
        this.stockGoodService = stockGoodService;
    }

    /**
     * Обработка запроса на вывод всех товаров на складе с сортировкой по категориям
     * @return ResponseEntity c коллекцией объектов StockGood
     */
    @GetMapping(value = "")
    public ResponseEntity<?> readAll(){
        List<StockGood> stockGoods = new ArrayList<>();
        Iterable<StockGood> stockGoodIterable = stockGoodService.readAll();
        stockGoodIterable.forEach(stockGoods::add);
        return !stockGoods.isEmpty()
                ? new ResponseEntity<>(stockGoods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.STOCK_GOODS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на вывод всех видов товаров заданной категории
     * @return ResponseEntity c коллекцией объектов Good
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> readAllByCategoryId(@PathVariable(name="categoryId") long categoryId){
        List<StockGood> goods = new ArrayList<>();
        Iterable<StockGood> goodsIterable = stockGoodService.readAllByCategoryId(categoryId);
        goodsIterable.forEach(goods::add);
        return !goods.isEmpty()
                ? new ResponseEntity<>(goods, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
    }
    /**
     * Обработка запроса на создание новой записи "товар на складе"
     * @param stockGood объект "товар на складе"
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> create(@RequestBody StockGood stockGood){
        StockGood dbStockGood;

        dbStockGood = stockGoodService.create(stockGood);
        return dbStockGood != null
                ? new ResponseEntity<>(dbStockGood, HttpStatus.CREATED)
                : new ResponseEntity<>(Messages.CREATE_STOCK_GOOD_ERROR, HttpStatus.BAD_REQUEST);
    }
    /**
     * Обработка запроса на чтение записи "товар на складе" по заданному id
     * @param id объекта "товар на складе"
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable(name="id") long id){
        StockGood dbStockGood = stockGoodService.read(id);
        return dbStockGood != null
                ? new ResponseEntity<>(dbStockGood, HttpStatus.OK)
                : new ResponseEntity<>(Messages.SUCH_ID_STOCK_GOOD_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    /**
     * Обработка запроса на удаление записи "товар на складе" по заданному id
     * @param id объекта "товар на складе"
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") long id){
        boolean isRemoved = stockGoodService.delete(id);
        return isRemoved
                ? new ResponseEntity<>(Messages.STOCK_GOOD_DELETED, HttpStatus.OK)
                : new ResponseEntity<>(Messages.DELETE_STOCK_GOOD_ERROR, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на добавление заданного количества товара на склад
     * @param bodyParams объект категории
     * @return
     */
    @PostMapping(value = "/add/{goodId}")
    public ResponseEntity<?> addGoodAmount(@PathVariable(name="goodId") long goodId,  // id товара
                                               @RequestBody Map<String, String> bodyParams) {
        String strGoodAmount = bodyParams.get("goodAmount");
        int goodAmount;
        try {
            goodAmount = Integer.parseInt(strGoodAmount);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Messages.WRONG_VALUE, HttpStatus.BAD_REQUEST);
        }
        StockGood dbStockGood;
            dbStockGood = stockGoodService.addGoodAmount(goodId, goodAmount);
            return dbStockGood!=null
                ? new ResponseEntity<>(dbStockGood, HttpStatus.OK)
                : new ResponseEntity<>(Messages.CREATE_STOCK_GOOD_ERROR, HttpStatus.BAD_REQUEST);
    }
}
