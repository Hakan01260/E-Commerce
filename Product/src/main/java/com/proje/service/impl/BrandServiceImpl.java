package com.proje.service.impl;

import com.proje.mapper.BrandResponseMapper;
import com.proje.model.api.request.BrandCreateRequest;
import com.proje.model.api.response.BrandResponse;
import com.proje.model.entity.Brand;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.BrandRepository;
import com.proje.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandResponseMapper brandResponseMapper;

    @Transactional(readOnly = true)
    public List<BrandResponse> findAll(){
        return brandRepository
                .findAll().stream().map(brandResponseMapper::map).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public BrandResponse findById(Long id){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand not found by id: " + id);
        }
        Brand brand = optionalBrand.get();
        return brandResponseMapper.map(brand);
    }
    @Transactional
    public BrandResponse create(BrandCreateRequest request){
        Brand brand = new Brand();
        brand.setName(request.getName());

        Brand createdBrand = brandRepository.save(brand);
        return brandResponseMapper.map(createdBrand);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()) {
            throw new NotFoundException("Brand not found by id: " + id);
        }
        brandRepository.deleteById(optionalBrand.get().getId());
    }
    @Transactional
    public BrandResponse update(BrandCreateRequest request) {
        Optional<Brand> optionalBrand = brandRepository.findById(request.getId());
        if (optionalBrand.isEmpty()) {
            throw new NotFoundException("Brand not found by id: " + request.getId());
        }
        Brand brand = optionalBrand.get();
        brand.setName(request.getName());

        Brand createdBrand = brandRepository.save(brand);
        return brandResponseMapper.map(createdBrand);
    }
}
