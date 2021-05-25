package com.example.vonline.service;

import com.example.vonline.entity.Brand;
import com.example.vonline.model.dto.BrandInfo;
import com.example.vonline.model.request.CreateBrandReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BrandService {
    public List<Brand> getListBrand();

    public List<BrandInfo> getListBrandAndProductCount();

    public Brand createBrand(CreateBrandReq req);

    public void updateBrand(int id, CreateBrandReq req);

    public void deleteBrand(int id);
}
