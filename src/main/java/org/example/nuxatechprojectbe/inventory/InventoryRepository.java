package org.example.nuxatechprojectbe.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, UUID> {
    List<InventoryModel> findAllByOrderByCategoryAscNameAsc();
}
