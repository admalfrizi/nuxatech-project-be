package org.example.nuxatechprojectbe.inventory;

import lombok.RequiredArgsConstructor;
import org.example.nuxatechprojectbe.config.response.ResponseHandler;
import org.example.nuxatechprojectbe.config.response.ResponseHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryController {

}
