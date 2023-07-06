package final_work.models;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Класс представления отгрузки товаров.
 */
@Entity
@Table(name="shipments")
@Transactional
public class Shipment {
    /**
     * Идентификатор поставки (в одной поставке м.б. N записей-товаров)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * Пользователь, в пользу которого осуществляется отгрузка
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private User user;

    /**
     * Дата отгрузки в строковом виде
     */
    private String date;

    public Shipment() {
    }

    public Shipment(User user, String date) {
        this.user = user;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
