package com.example.vonline.service;

import com.example.vonline.entity.Category;
import com.example.vonline.model.dto.CategoryInfo;
import com.example.vonline.model.request.CreateCategoryReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CategoryService {
    public List<Category> getListCategory();

    public List<CategoryInfo> getListCategoryAndProductCount();

    public Category createCategory(CreateCategoryReq req);

    public void updateCategory(int id, CreateCategoryReq req);

    public void deleteCategory(int id);
}
