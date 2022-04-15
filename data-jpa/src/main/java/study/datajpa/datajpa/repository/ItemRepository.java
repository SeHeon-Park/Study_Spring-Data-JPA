package study.datajpa.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
}
