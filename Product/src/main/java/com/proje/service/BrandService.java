package com.proje.service;

import com.proje.model.api.request.BrandCreateRequest;
import com.proje.model.api.response.BrandResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

    List<BrandResponse> findAll();
    BrandResponse findById(Long id);
    BrandResponse create(BrandCreateRequest request);
    void deleteById(Long id);
    BrandResponse update(BrandCreateRequest request);
}
