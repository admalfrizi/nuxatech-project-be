package org.example.nuxatechprojectbe.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Services> getAllServices() {
        return inventoryRepository.findAllByOrderByCategoryAscNameAsc();
    }

    public Services addService(Services services) {
        services.setStatus("UNKNOWN");
        return inventoryRepository.save(services);
    }

    public void deleteService(UUID id) {
        inventoryRepository.deleteById(id);
    }
}
