package org.example.nuxatechprojectbe.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Services> getAllServices() {
        return inventoryRepository.findAllByOrderByCategoryAscNameAsc();
    }
}
