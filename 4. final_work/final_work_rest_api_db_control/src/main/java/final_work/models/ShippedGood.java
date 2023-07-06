package final_work.models;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Класс представления отгруженного товара, входящего в состав отгрузки
 */
@Entity
@Table(name="shipped_goods")
@Transactional
public class ShippedGood {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;        // идентификатор записи в поставках (одна запись - один товар с количеством и суммой)


    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Good good;

    private int amount;  // количество товаров
    private int sum;    // суммарная стоимость позиции в поставке

    public ShippedGood() {
    }

    public ShippedGood(Shipment shipment, Good good, int amount) {
        this.shipment = shipment;
        this.good = good;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public int getAmount() {
        return amount;
    }

    public int getSum() {
        return sum;
    }

    public Good getGood() {
        return good;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }



    public void setGood(Good good) {
        this.good = good;
    }

    public void calculateSum() {
        sum = good.getPrice() * amount;
    }
}
