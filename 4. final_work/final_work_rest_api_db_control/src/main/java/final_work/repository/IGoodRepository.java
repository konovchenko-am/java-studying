package final_work.repository;

import final_work.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGoodRepository extends JpaRepository<Good, Long> {
    Iterable<Good> findAllByOrderByCategoryTitle();

    Iterable<Good> findAllByCategoryId(long categoryId);
}
