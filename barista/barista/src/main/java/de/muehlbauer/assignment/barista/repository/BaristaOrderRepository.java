package de.muehlbauer.assignment.barista.repository;

import de.muehlbauer.assignment.barista.entity.BaristaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaristaOrderRepository extends JpaRepository<BaristaOrderEntity, Integer> {


    Optional<BaristaOrderEntity> findByOrderNumber(Integer orderNumber);

    void deleteByOrderNumber(Integer orderNumber);

    List<BaristaOrderEntity> findAllByOrderNumberIn(List<Integer> orderNumbers);

}
