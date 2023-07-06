package final_work.models;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Класс представления товара на складе с указанием количества
 */
@Entity
@Table(name="stock")
@Transactional
public class StockGood {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @Nullable
    private Good good;

    private int amount;         // количество товара на складе

    private int sum;


    public StockGood() {
    }

    public StockGood(Good good, int amount) {
        this.good = good;
        this.amount = amount;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Good getGood() {
        return good;
    }

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getSum() {
        return sum;
    }

    public void calculateSum(){
        setSum(good.getPrice() * getAmount());
    }
}
