package br.com.estudo.orderservice.repository;

import br.com.estudo.orderservice.entity.PurchaseOrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Integer> {

        List<PurchaseOrderEntity> findByUserId(Integer userId);

}
