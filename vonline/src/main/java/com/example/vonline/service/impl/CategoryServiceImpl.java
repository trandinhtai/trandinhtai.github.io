package com.example.vonline.service.impl;

import com.example.vonline.entity.Category;
import com.example.vonline.exception.BadRequestException;
import com.example.vonline.exception.InternalServerException;
import com.example.vonline.exception.NotFoundException;
import com.example.vonline.model.dto.CategoryInfo;
import com.example.vonline.model.request.CreateCategoryReq;
import com.example.vonline.repository.CategoryRepository;
import com.example.vonline.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryInfo> getListCategoryAndProductCount() {
        return categoryRepository.getListCategoryAndProductCount();
    }

    @Override
    public Category createCategory(CreateCategoryReq req) {
        Category category = new Category();
        category.setName(req.getName());

        categoryRepository.save(category);

        return category;
    }

    @Override
    public void updateCategory(int id, CreateCategoryReq req) {
        // Check category exist
        Optional<Category> rs = categoryRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }

        Category category = rs.get();
        category.setName(req.getName());

        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi chỉnh sửa category");
        }
    }

    @Override
    public void deleteCategory(int id) {
        // Check category exist
        Optional<Category> rs = categoryRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }

        // Check product in category
        int count = categoryRepository.checkProductInCategory(id);
        if (count == 1) {
            throw new BadRequestException("Có sản phẩm thuộc category không thể xóa");
        }

        Category category = rs.get();

        try {
            categoryRepository.delete(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa category");
        }
    }
}
