package com.example.springwebmvc.service.impl;

import com.example.springwebmvc.model.Supplier;
import com.example.springwebmvc.repository.SupplierRepository;
import com.example.springwebmvc.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public void createSupplier(Supplier supplier) {
        supplierRepository.insert(supplier);
        log.info("Created supplier: {}", supplier.getCompany());
    }

    @Override
    @Transactional
    public void updateSupplier(Integer id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id);
        if (existingSupplier == null) {
            throw new RuntimeException("Supplier with ID " + id + " not found.");
        }

        supplier.setId(id);
        supplierRepository.update(supplier);
        log.info("Updated supplier: ID={}, Company={}", id, supplier.getCompany());
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Boolean status) {
        Supplier supplier = supplierRepository.findById(id);
        if (supplier == null) {
            throw new RuntimeException("Supplier with ID " + id + " not found.");
        }

        supplier.setStatus(status);
        supplierRepository.update(supplier);
        log.info("Updated status of supplier ID={} to {}", id, status);
    }

    @Override
    @Transactional
    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
        log.info("Deleted supplier with ID: {}", id);
    }

    @Override
    public Supplier findSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id);
        if (supplier == null) {
            throw new RuntimeException("Supplier with ID " + id + " not found.");
        }
        return supplier;
    }

    @Override
    public List<Supplier> findAllSuppliers() {
        return supplierRepository.findAll();
    }
}
