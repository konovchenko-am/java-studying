package final_work.controllers;

/**
 * Константы - сообщения
 */
public class Messages {
    static final String NO_RECORDS_FOUND = "Записи отсутствуют, либо ошибка при получении записей из БД.",
        INSERT_ERROR = "Ошибка при внесении записи в БД.",
        NO_ID_RECORD_FOUND="Запись с указанным ID не найдена.",
        WRONG_PRICE_VALUE = "Неверное значение цены.",
        UPDATING_PRICE_ERROR = "Ошибка при обновлении цены товара.";

    static final String USERS_NOT_FOUND = "Пользователи в БД не найдены.",
        SUCH_ID_USER_NOT_FOUND = "Пользователь с указанным ID не найден.",
        UPDATE_USER_ERROR = "Ошибка при обновлении данных пользователя в БД.",
        USER_DELETED = "Пользователь с указанным ID удален.",
        DELETE_USER_ERROR = "Ошибка при удалении пользователя из БД.",
        CREATE_USER_ERROR = "Ошибка при создании пользователя в БД";

    static final String WRONG_VALUE = "Неверные значения аргументов.",
            ADD_GOOD_TO_SHIP_ERROR = "Не найдена поставка и/или товар с указанными идентификаторами.";

    static final String CATEGORIES_NOT_FOUND = "Категории товаров в БД не найдены.",
            CREATE_CATEGORY_ERROR = "Ошибка при создании категории товаров в БД.",
            SUCH_ID_CATEGORY_NOT_FOUND = "Категория товаров с указанным ID не найдена.",
            CATEGORY_DELETED = "Категория товаров с указанным ID удалена.",
            DELETE_CATEGORY_ERROR = "Ошибка при удалении категории товаров из БД.";

    static final String STOCK_GOODS_NOT_FOUND = "Товары на складе в БД не найдены.",
            CREATE_STOCK_GOOD_ERROR = "Ошибка при создании товара на складе в БД.",
            SUCH_ID_STOCK_GOOD_NOT_FOUND = "Товар на складе с указанным ID не найден.",
            STOCK_GOOD_DELETED = "Товар на складе с указанным ID удален.",
            DELETE_STOCK_GOOD_ERROR = "Ошибка при удалении товара на складе из БД.";

}
