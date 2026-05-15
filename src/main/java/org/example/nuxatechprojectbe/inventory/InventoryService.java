package org.example.nuxatechprojectbe.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void updateServiceStatus(UUID serviceId, String newStatus) {
        inventoryRepository.findById(serviceId).ifPresent(service -> {
            service.setStatus(newStatus);
            inventoryRepository.save(service);
        });
    }

    @Transactional(readOnly = true)
    public Services getServiceById(UUID id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + id));
    }

    public void deleteService(UUID id) {
        inventoryRepository.deleteById(id);
    }
}
