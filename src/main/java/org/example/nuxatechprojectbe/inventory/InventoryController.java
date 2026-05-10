package org.example.nuxatechprojectbe.inventory;

import lombok.RequiredArgsConstructor;
import org.example.nuxatechprojectbe.common.response.ResponseHandler;
import org.example.nuxatechprojectbe.common.response.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<ResponseHelper<List<Services>>> listAll() {
        return ResponseHandler.generateResponse(
                inventoryService.getAllServices(),
                "",
                HttpStatus.OK,
                true
        );
    }
}
