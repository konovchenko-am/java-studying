package final_work.controllers;

import final_work.models.User;
import final_work.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * REST контроллер для работы с пользователями
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService; // объект для вызова методов интерфейса

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Обработка запроса на получение информации обо всех пользователях
     * @return ResponseEntity с коллекцией объектов User
     */
    @GetMapping(value = "")
    public ResponseEntity<?> readAll(){
        List<User> users = new ArrayList<>();
        Iterable<User> source = userService.readAll();
        source.forEach(users::add);
        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK) // 1ый параметр это тело ответа, 2ой - статус ответа
                : new ResponseEntity<>(Messages.USERS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на регистрацию - создание пользователя
     * @param user объект пользователя
     * @return в случае успеха - ResponseEntity с объектом User
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> create(@RequestBody User user){ //user - это объект, который клиент отправил в виде json
        User dbUser;
        try{
            dbUser = userService.create(user);
            return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(Messages.CREATE_USER_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обработка запроса на чтение заданного пользователя
     * @param id пользователя
     * @return ResponseEntity с объектом User
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable(name="id") long id){
        User user = userService.read(id);//по id получили юзера
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(Messages.SUCH_ID_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка запроса на обновление пользователя
     * @param id пользователя
     * @param user объект User
     * @return ResponseEntity с объектом User, полученным в результате внесения информации в БД
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name="id") long id,@RequestBody User user){
        user.setRole("USER"); // принудительно выставляем роль обычного пользователя
        User dbUser = userService.update(user, id);
        return dbUser != null
                ? new ResponseEntity<>(dbUser, HttpStatus.OK)
                : new ResponseEntity<>(Messages.UPDATE_USER_ERROR, HttpStatus.NOT_MODIFIED);
    }

    /**
     * Обработка запроса на удаление записи пользователя
     * @param id пользователя
     * @return ResponseEntity c сообщением о результате операции
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") long id){
        boolean isRemoved = userService.delete(id);
        return isRemoved
                ? new ResponseEntity<>(Messages.USER_DELETED, HttpStatus.OK)
                : new ResponseEntity<>(Messages.DELETE_USER_ERROR, HttpStatus.NOT_FOUND);
    }
}
