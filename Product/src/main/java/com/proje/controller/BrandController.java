package com.proje.controller;

import com.proje.model.api.request.BrandCreateRequest;
import com.proje.model.api.response.BrandResponse;
import com.proje.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public List<BrandResponse> getAll() {
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public BrandResponse getBrandById(@PathVariable Long id) {
        return brandService.findById(id);
    }

    @PostMapping
    public ResponseEntity<BrandResponse> posts(@RequestBody BrandCreateRequest request) {
        return new ResponseEntity<>(brandService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBrandById(@PathVariable Long id) {
        brandService.deleteById(id);
    }

    @PutMapping
    public BrandResponse update(@RequestBody BrandCreateRequest request) {
        return brandService.update(request);
    }
}
