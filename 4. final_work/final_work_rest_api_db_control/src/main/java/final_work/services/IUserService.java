package final_work.services;

import final_work.models.User;

/**
 * Интерфейс для работы с пользователями
 */
public interface IUserService {
    /**
     * Создание нового пользователя
     * @param user - объект, который будем добавлять
     * @return объект User, полученный в результате сохранения изменений в БД
     */
    User create(User user);

    /**
     * Получение всех пользователей в виде коллекции
     * @return коллекция объектов User
     */
    Iterable<User> readAll();

    /**
     * Получение свойств пользователя по id
     * @param id пользователя
     * @return объект User, полученный в результате сохранения изменений в БД
     */
    User read(long id);

    /**
     * Изменение свойств заданного пользователя
     * @param user - тело запроса, включающее свойства, которые заменят старые свойства
     * @param id - id пользователя
     * @return объект User, полученный в результате сохранения изменений в БД
     */
    User update(User user,long id);

    /**
     * Удаление пользователя по id
     * @param id пользователя
     * @return true в случае успеха, false в противном случае
     */
    boolean delete(long id);
}
