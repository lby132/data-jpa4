package study.datajpa4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa4.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
