package final_work.repository;

import final_work.models.StockGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockGoodRepository extends JpaRepository<StockGood, Long> {

    boolean existsByGoodId(long goodId);

    StockGood findOneByGoodId(long goodId);

    Iterable<StockGood> findAllByOrderByGoodCategoryTitle();

    Iterable<StockGood> findAllByGoodCategoryId(long categoryId);
}



