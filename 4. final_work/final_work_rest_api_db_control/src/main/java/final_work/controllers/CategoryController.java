package final_work.controllers;

import final_work.models.Good;
import final_work.models.GoodCategory;
import final_work.services.IGoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * REST контроллер для работы с категориями товаров
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final IGoodCategoryService categoryService; // объект для вызова методов интерфейса

    /**
     * Конструктор
     * @param categoryService объект IGoodCategoryService
     */
    @Autowired
    public CategoryController(IGoodCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Обработка запроса чтения всех категорий
     * @return ResponseEntity с коллекцией объектов GoodCategory
     */
    @GetMapping(value = "")
    public ResponseEntity<?> readAll(){
        List<GoodCategory> categories = new ArrayList<>();
        Iterable<GoodCategory> source = categoryService.readAll();
        source.forEach(categories::add);
        return !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.CATEGORIES_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Создание новой категории товаров
     * @param category объект категории
     * @return в случае успеха - ResponseEntity с объектом GoodCategory
     */
    @PostMapping(value = "/add_category")
    public ResponseEntity<?> create(@RequestBody GoodCategory category){ //user - это объект, который клиент отправил в виде json
        GoodCategory dbCategory;
        try{
            dbCategory = categoryService.create(category);
            return new ResponseEntity<>(dbCategory, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(Messages.CREATE_CATEGORY_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Чтение информации о заданной категории товара
     * @param id категории товара
     * @return ResponseEntity с объектом GoodCategory
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable(name="id") long id){
        GoodCategory category = categoryService.read(id); //по id получили юзера
        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(Messages.SUCH_ID_CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Удаление заданной категории товара
     * @param id категории
     * @return true в случае успеха, false в противном случае
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") long id){
        boolean isRemoved = categoryService.delete(id);
        return isRemoved
                ? new ResponseEntity<>(Messages.CATEGORY_DELETED, HttpStatus.OK)
                    : new ResponseEntity<>(Messages.DELETE_CATEGORY_ERROR, HttpStatus.NOT_FOUND);
    }

    /**
     * Добавление товара в категорию
     * @param bodyParams объект категории
     * @return в случае успеха - ResponseEntity с объектом Good
     */
    @PostMapping(value = "/add_good/{goodId}")
    public ResponseEntity<?> addGoodToCategory(@PathVariable(name="goodId") long goodId,  // id товара
                                               @RequestBody Map<String, String> bodyParams) {
        String strCategoryId = bodyParams.get("categoryId");
        long categoryId;
        try {
            categoryId = Long.parseLong(strCategoryId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Messages.WRONG_VALUE, HttpStatus.BAD_REQUEST);
        }
        Good dbGood;
        try{
            dbGood = categoryService.addGoodToCategory(categoryId, goodId);
            return new ResponseEntity<>(dbGood, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(Messages.CREATE_CATEGORY_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
