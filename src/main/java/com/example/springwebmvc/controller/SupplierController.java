package com.example.springwebmvc.controller;

import com.example.springwebmvc.model.Supplier;
import com.example.springwebmvc.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        Supplier supplier = supplierService.findSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<Void> createSupplier(@RequestBody Supplier supplier) {
        supplierService.createSupplier(supplier);
        return ResponseEntity.status(201).build(); // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSupplier(@PathVariable Integer id,
                                               @RequestBody Supplier supplier) {
        supplierService.updateSupplier(id, supplier);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    //PUT /suppliers/5/status?active=false
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateSupplierStatus(
            @PathVariable Integer id,
            @RequestParam("active") Boolean active
    ) {
        supplierService.updateStatus(id, active);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}

